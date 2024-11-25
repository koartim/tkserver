package com.timkoar.tkserver.controller;

import com.timkoar.tkserver.dto.CommentDTO;
import com.timkoar.tkserver.mapper.CommentMapper;
import com.timkoar.tkserver.model.blog.comment.Comment;
import com.timkoar.tkserver.service.BlogPostService;
import com.timkoar.tkserver.service.CommentService;
import com.timkoar.tkserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId)
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
        commentDTO.setPostId(postId);
        if (commentDTO.getUsername() == null || commentDTO.getUsername().isEmpty()) {
            String username = userService.findById(commentDTO.getUserId())
                    .map(user -> user.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("user not found"));
            commentDTO.setUsername(username);
        }
        Comment comment = CommentMapper.toEntity(commentDTO);
        Comment savedComment = commentService.saveComment(comment);
        CommentDTO response = CommentMapper.toDTO(savedComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
