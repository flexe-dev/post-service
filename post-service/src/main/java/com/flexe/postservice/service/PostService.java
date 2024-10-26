package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.PostInteraction;
import com.flexe.postservice.entity.posts.PostNode;
import com.flexe.postservice.entity.posts.UserPosts;
import com.flexe.postservice.entity.posts.core.MediaPost;
import com.flexe.postservice.entity.posts.core.Post;
import com.flexe.postservice.entity.posts.core.TextPost;
import com.flexe.postservice.entity.posts.feed.FeedDisplay;
import com.flexe.postservice.entity.posts.feed.FeedPost;
import com.flexe.postservice.entity.posts.media.MediaDocument;
import com.flexe.postservice.entity.posts.text.TextContent;
import com.flexe.postservice.entity.user.UserDetails;
import com.flexe.postservice.enums.PostEnums.*;
import com.flexe.postservice.enums.PostInteractionEnums.*;
import com.flexe.postservice.repository.MediaDocumentRepository;
import com.flexe.postservice.repository.PostRepository;
import com.flexe.postservice.repository.TextContentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.flexe.postservice.enums.PostEnums.getPostType;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TextContentRepository textContentRepository;

    @Autowired
    private MediaDocumentRepository mediaDocumentRepository;

    @Autowired
    private PostInteractionService postInteractionService;

    @Autowired
    private HTTPService  httpService;

    private WebClient client;
    private String serverPrefix;

    @PostConstruct
    public void init(){
        this.client = httpService.generateWebClient(HTTPService.TargetServer.USER);
        this.serverPrefix = httpService.getUriPrefix(HTTPService.TargetController.USER);
    }

    public Post savePost(Post post){
        Post savedPost = postRepository.save(post);
        postInteractionService.SendPostNodeMessage(new PostNode(savedPost), PostNodeModificationEnum.SAVE);
        return savedPost;
    }

    public Post getPostById(String id){
        return postRepository.findById(id).orElse(null);
    }

    public Post getPostOrThrow(String id){
        Post post = getPostById(id);
        if(post == null) throw new IllegalArgumentException("Post not found");
        return post;
    }

    public UserPosts getAllPostsByUser(String userId){
        Post[] posts = postRepository.findByUserId(userId).toArray(Post[]::new);

        if(posts == null || posts.length == 0) return new UserPosts();

        MediaDocument[] mediaPosts = mediaDocumentRepository.findByUserId(userId);
        TextContent[] textPosts = textContentRepository.findByUserId(userId);

        return UserPosts.fromCollection(posts, mediaPosts, textPosts);
    }

    public void DeleteUserPosts(String userId){
        postRepository.deleteByUserId(userId);
        textContentRepository.deleteByUserId(userId);
        mediaDocumentRepository.deleteByUserId(userId);
    }

    public void deletePost(String postId){
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null) return;

        postInteractionService.SendPostNodeMessage(new PostNode(post), PostNodeModificationEnum.DELETE);
        postRepository.delete(post);
    }

    public Post retrievePostAndSendInteraction(String postId, String userId, PostInteractionEnum action){
        Post post = getPostOrThrow(postId);
        PostNode node = new PostNode(post);
        postInteractionService.SendPostInteractionMessage(new PostInteraction(node, userId), action);
        return post;
    }

    public void likePost(String postId, String userId){
        Post post = retrievePostAndSendInteraction(postId, userId, PostInteractionEnum.LIKE);
        post.getMetrics().likePost();
        postRepository.save(post);
    }

    public void unlikePost(String postId, String userId){
        Post post = retrievePostAndSendInteraction(postId, userId, PostInteractionEnum.UNLIKE);
        post.getMetrics().removeLike();
        postRepository.save(post);
    }

    public void favouritePost(String postId, String userId){
        Post post = retrievePostAndSendInteraction(postId, userId, PostInteractionEnum.SAVE);
        post.getMetrics().savePost();
        postRepository.save(post);
    }

    public void removeFavouritePost(String postId, String userId){
        Post post = retrievePostAndSendInteraction(postId, userId, PostInteractionEnum.UNSAVE);
        post.getMetrics().removeSave();
        postRepository.save(post);
    }

    public void repostPost(String postId, String userId){
        Post post = retrievePostAndSendInteraction(postId, userId, PostInteractionEnum.REPOST);
        post.getMetrics().repostPost();
        postRepository.save(post);
    }

    public void removeRepost(String postId, String userId){
        Post post = retrievePostAndSendInteraction(postId, userId, PostInteractionEnum.UNREPOST);
        post.getMetrics().removeRepost();
        postRepository.save(post);
    }

    public void incrementCommentCount(String postId){
        Post post = getPostOrThrow(postId);
        post.getMetrics().commentPost();
        postRepository.save(post);
    }

    public void decrementCommentCount(String postId, Integer count){
        Post post = getPostOrThrow(postId);
        post.getMetrics().removeComment(count);
        postRepository.save(post);
    }
    // Fetches all Posts from a list of references
    public List<FeedPost<?>> GetPostsFromFeedReference(List<FeedDisplay> postReferences) {

        // Create a HashSet of all users that need to be fetched (creatorId plus reference array)
        Set<String> userIdSet = postReferences.stream()
                .flatMap(reference ->
                        Stream.concat(
                                reference.getRecipientReferences().stream().map(userReference -> userReference.getKey().getUserId()),
                                Stream.of(reference.getUserFeed().getCreatorId())))
                .collect(Collectors.toSet());

        // Fetch users from User Service in parallel with post fetches
        CompletableFuture<List<UserDetails>> usersFuture = CompletableFuture.supplyAsync(() -> {
            ResponseEntity<UserDetails[]> userResponse = httpService.post(client, serverPrefix + "/find/users",
                    userIdSet.stream().toList(),
                    UserDetails[].class);
            return userResponse != null && userResponse.getBody() != null ? Arrays.asList(userResponse.getBody()) : Collections.emptyList();
        });

        // Retrieve all post metadata from both collections (consolidating filtering logic)
        HashMap<PostType, List<String>> postIdsByType = postReferences.stream()
                .collect(Collectors.groupingBy(
                        reference -> getPostType(reference.getUserFeed().getPostType()),
                        HashMap::new, // Explicitly using HashMap here
                        Collectors.mapping(reference -> reference.getUserFeed().getKey().getPostId(), Collectors.toList())
                ));

        // Fetch text and media documents in parallel
        CompletableFuture<List<TextContent>> textPostsFuture = CompletableFuture.supplyAsync(() ->
                fetchPostsByType(postIdsByType, PostType.TEXT, textContentRepository::findAllInPostIdList));

        CompletableFuture<List<MediaDocument>> mediaPostsFuture = CompletableFuture.supplyAsync(() ->
                fetchPostsByType(postIdsByType, PostType.MEDIA, mediaDocumentRepository::findAllInPostIdList));

        // Fetch the post metadata itself
        CompletableFuture<List<Post>> postsFuture = CompletableFuture.supplyAsync(() ->
                postRepository.findAllInIdList(postReferences.stream()
                        .map(reference -> reference.getUserFeed().getKey().getPostId())
                        .toList()));

        // Combine the futures to process after all fetches are complete
        CompletableFuture.allOf(usersFuture, textPostsFuture, mediaPostsFuture, postsFuture).join();

        // Create lookup maps for fast access
        Map<String, Post> postMap = postsFuture.join().stream().collect(Collectors.toMap(Post::getId, Function.identity()));
        Map<String, MediaDocument> mediaDocumentMap = mediaPostsFuture.join().stream().collect(Collectors.toMap(MediaDocument::getPostId, Function.identity()));
        Map<String, TextContent> textContentMap = textPostsFuture.join().stream().collect(Collectors.toMap(TextContent::getPostId, Function.identity()));
        Map<String, UserDetails> userDetailsMap = usersFuture.join().stream().collect(Collectors.toMap(UserDetails::getUserId, Function.identity()));

        // Build the FeedPost list
        return postReferences.stream().map(reference -> {
            // Find Associated Post Object
            Post post = postMap.get(reference.getUserFeed().getKey().getPostId());
            if (post == null) return null;

            List<UserDetails> postUsers = findUsersForFeedPost(reference, userDetailsMap);
            UserDetails creator = userDetailsMap.get(reference.getUserFeed().getCreatorId());

            if (post.getPostType() == PostType.MEDIA) {
                MediaDocument document = mediaDocumentMap.get(post.getId());
                return new FeedPost<>(new MediaPost(post, document), postUsers, creator, reference);
            } else {
                TextContent content = textContentMap.get(post.getId());
                return new FeedPost<>(new TextPost(post, content), postUsers, creator, reference);
            }
        }).filter(Objects::nonNull).toList();
    }

    // Helper methods to fetch from relevant data repository
    private <T> List<T> fetchPostsByType(HashMap<PostType, List<String>> postIdsByType, PostType type, Function<List<String>, List<T>> repositoryFetch) {
        List<String> postIds = postIdsByType.get(type);
        if (postIds == null || postIds.isEmpty()) return Collections.emptyList();
        return repositoryFetch.apply(postIds);
    }

    private List<UserDetails> findUsersForFeedPost(FeedDisplay reference, Map<String, UserDetails> userDetailsMap) {
        return reference.getRecipientReferences().stream()
                .map(userReference -> userDetailsMap.get(userReference.getKey().getUserId()))
                .filter(Objects::nonNull)
                .toList();
    }


}
