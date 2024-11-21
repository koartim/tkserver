package com.timkoar.tkserver.controller;

import com.timkoar.tkserver.dto.CommentRequest;
import com.timkoar.tkserver.mapper.CommentMapper;
import com.timkoar.tkserver.model.blog.BlogPost;
import com.timkoar.tkserver.model.blog.comment.Comment;
import com.timkoar.tkserver.model.user.User;
import com.timkoar.tkserver.service.BlogPostService;
import com.timkoar.tkserver.service.CommentService;
import com.timkoar.tkserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final BlogPostService blogPostService;
    private final UserService userService;

    public CommentController(CommentService commentService, BlogPostService blogPostService, UserService userService) {
        this.commentService = commentService;
        this.blogPostService = blogPostService;
        this.userService = userService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentRequest>> getCommentsForPost(@PathVariable long postId) {
        List<CommentRequest> comments = commentService.getCommentsForPost(postId)
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<CommentRequest> addComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        BlogPost blogPost = blogPostService.getPostById(postId);
        User user = userService.findById(commentRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Map CommentRequest to Comment entity
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setCreatedDate(LocalDateTime.now());
        comment.setUser(user); // Associate the managed user

        // add comment to the blogPost list of comments
        blogPost.addComment(comment);
        // save the blogpost (this in turn saves the comment)
        blogPostService.savePost(blogPost);

        CommentRequest response = CommentMapper.toDTO(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);

        BlogPost blogPost = comment.getBlogPost();
        blogPost.removeComment(comment);
        blogPostService.savePost(blogPost);

        return ResponseEntity.noContent().build();
    }
}
