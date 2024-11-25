package com.timkoar.tkserver.service;

import com.timkoar.tkserver.model.blog.comment.Comment;
import com.timkoar.tkserver.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(long postId) {
        return commentRepository.findByPostId(postId);
    }

    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
