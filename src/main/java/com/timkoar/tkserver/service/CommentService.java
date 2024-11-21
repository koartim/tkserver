package com.timkoar.tkserver.service;

import com.timkoar.tkserver.model.blog.comment.Comment;
import com.timkoar.tkserver.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsForPost(Long postId) {
        return commentRepository.findByBlogPost_IdOrderByCreatedDateDesc(postId);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
}
