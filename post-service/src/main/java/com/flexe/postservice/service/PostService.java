package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.PostInteraction;
import com.flexe.postservice.entity.posts.PostNode;
import com.flexe.postservice.entity.posts.UserPosts;
import com.flexe.postservice.entity.posts.core.Post;
import com.flexe.postservice.entity.posts.media.MediaContent;
import com.flexe.postservice.entity.posts.media.MediaDocument;
import com.flexe.postservice.entity.posts.text.TextContent;
import com.flexe.postservice.enums.PostInteractionEnums.*;
import com.flexe.postservice.repository.MediaDocumentRepository;
import com.flexe.postservice.repository.PostRepository;
import com.flexe.postservice.repository.TextContentRepository;
import com.mongodb.internal.CheckedSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        Post[] posts = postRepository.findByUserId(userId);
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

}
