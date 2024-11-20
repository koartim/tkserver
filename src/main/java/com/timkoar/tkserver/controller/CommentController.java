package com.timkoar.tkserver.controller;

import com.timkoar.tkserver.model.blog.BlogPost;
import com.timkoar.tkserver.model.blog.comment.Comment;
import com.timkoar.tkserver.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable long postId) {
        return ResponseEntity.ok(commentService.getCommentsForPost(postId));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable long postId, @RequestBody Comment comment) {
        // create reference to 'blogPost' before saving the comment
        BlogPost blogPost = new BlogPost();
        blogPost.setId(postId);
        comment.setBlogPost(blogPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(comment));
    }
}
