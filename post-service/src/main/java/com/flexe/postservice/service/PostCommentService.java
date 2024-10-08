package com.flexe.postservice.service;

import com.flexe.postservice.entity.posts.metrics.CommentNode;
import com.flexe.postservice.entity.posts.metrics.Comment;
import com.flexe.postservice.entity.posts.metrics.CommentReact;
import com.flexe.postservice.entity.posts.metrics.CommentReact.ReactType;
import com.flexe.postservice.repository.CommentReactionRepository;
import com.flexe.postservice.repository.PostCommentRepository;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.*;

@Service
public class PostCommentService {

    @Autowired
    private PostCommentRepository repository;
    @Autowired
    private CommentReactionRepository reactionRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private HTTPService httpService;

    private WebClient client;
    private String serverPrefix;

    @PostConstruct
    public void init(){
        this.client = httpService.generateWebClient(HTTPService.TargetServer.INTERACTION);
        this.serverPrefix = httpService.getUriPrefix(HTTPService.TargetController.NODE);
    }

    public CommentNode[] getPostComments(String postId) {
        Comment[] comments = repository.findAllByPostId(postId);
        return generateCommentTree(comments);
    }

    private CommentNode[] generateCommentTree(Comment[] comments){
        //TODO: Move Comment Storage and Generation into Graph Database
        return new ArrayList<CommentNode>().toArray(new CommentNode[0]);
//        Map<String, List<Comment>> commentMap = new HashMap<>();
//        for(Comment comment: comments){
//            commentMap.computeIfAbsent(comment.getParentId(), k -> new ArrayList<>()).add(comment);
//        }
//        Map<String, CommentNode> commentNodeMap = new HashMap<>();
//        Set<String> userSet = new HashSet<>();
//        Arrays.stream(comments).map(Comment::getUserId).forEach(userSet::add);
//
//        //Fetch User Details
//
//        for(Comment comment: comments){
//            CommentNode node = new CommentNode(comment);
//            userSet.add(comment.getUserId());
//            node.setUser(userCommentMap.get(comment.getUserId()));
//            commentNodeMap.put(comment.getId(), node);
//        }
//
//        Map<String, UserDisplay> userCommentMap = new HashMap<>();
//        List<CommentNode> commentTree = new ArrayList<>();
//        for(Comment comment: comments){
//            if(comment.getParentId() == null || !commentMap.containsKey(comment.getParentId())){
//                commentTree.add(commentNodeMap.get(comment.getId()));
//            }
//            else{
//            CommentNode parent = commentNodeMap.get(comment.getParentId());
//            parent.addChild(commentNodeMap.get(comment.getId()));
//            }
//
//        }
//
//        return commentTree.toArray(new CommentNode[0]);
    }

    public Comment saveComment(Comment comment){
        if(comment.getId() == null || comment.getId().isEmpty()){
            comment.setId(new ObjectId().toHexString());
        }

        postService.incrementCommentCount(comment.getPostId());
        return repository.save(comment);
    }

    public void deletePostComments(String postId){
        reactionRepository.deleteAllByPostId(postId);
        repository.deleteAllByPostId(postId);
    }

    public Integer deleteComment(CommentNode comment){
        List<String> commentIds = new ArrayList<>();
        traverseCommentNode(comment, commentIds);
        repository.deleteAllById(commentIds);
        postService.decrementCommentCount(comment.getComment().getPostId(), commentIds.size());
        return commentIds.size();
    }

    public Map<String, CommentReact.ReactType> getUserCommentReactions(String userId, String postId){
        CommentReact[] reactions =  reactionRepository.findByPostIdAndUserId(postId, userId);
        Map<String, CommentReact.ReactType> reactionMap = new HashMap<>();
        for(CommentReact reaction: reactions){
            reactionMap.put(reaction.getCommentId(), reaction.getReactType());
        }
        return reactionMap;
    }

    public void traverseCommentNode(CommentNode current, List<String> children){
        children.add(current.getComment().getId());
        for(CommentNode child: current.getChildren()){
            traverseCommentNode(child, children);
        }
    }

    public Optional<CommentReact> retrieveReaction(String commentId, String userId){
        return reactionRepository.findByCommentIdAndUserId(commentId, userId);
    }

    public void likeComment(String commentId, String userId, String postId, boolean opposite){
        Optional<Comment> comment = repository.findById(commentId);
        if(comment.isEmpty()) return;
        boolean reaction = handleCommentReaction(commentId, userId, postId, ReactType.LIKE);
        if(!reaction) return;

        comment.get().setLikes(comment.get().getLikes() + 1);
        if(opposite){
            comment.get().setDislikes(comment.get().getDislikes() - 1);
        }
        repository.save(comment.get());

    }

    public void removeReaction(String commentId, String userId){
        Optional<CommentReact> reaction = retrieveReaction(commentId, userId);
        Optional<Comment> comment = repository.findById(commentId);
        if(reaction.isEmpty() || comment.isEmpty()) return;
        if(reaction.get().getReactType() == ReactType.LIKE){
            comment.get().setLikes(comment.get().getLikes() - 1);
        }
        else{
            comment.get().setDislikes(comment.get().getDislikes() - 1);
        }

        repository.save(comment.get());
        reactionRepository.delete(reaction.get());
    }

    public boolean handleCommentReaction(String commentId, String userId, String postId, ReactType reactionType){
        Optional<CommentReact> reaction = retrieveReaction(commentId, userId);
        if(reaction.isPresent()){
            //Edit Current Reaction
            CommentReact react = reaction.get();
            if(react.getReactType() == reactionType){
                return false;
            }
            react.setReactType(reactionType);
            reactionRepository.save(react);

        }
        else{
            //Add New Reaction
            CommentReact react = new CommentReact(reactionType, userId, commentId, postId);
            reactionRepository.save(react);
        }
        return true;
    }

    public void dislikeComment(String commentId, String userId, String postId, boolean opposite) {
        Optional<Comment> comment = repository.findById(commentId);
        if(comment.isEmpty()) return;
        boolean reaction = handleCommentReaction(commentId, userId, postId, ReactType.DISLIKE);
        if(!reaction) return;

        comment.get().setDislikes(comment.get().getDislikes() + 1);
        if(opposite){
            comment.get().setLikes(comment.get().getLikes() - 1);
        }
        repository.save(comment.get());
    }

    public void reportComment(String commentId) {
        //Add To Seperate Table for Reported Comments
    }

}
