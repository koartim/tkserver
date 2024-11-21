package com.timkoar.tkserver.mapper;

import com.timkoar.tkserver.dto.CommentRequest;
import com.timkoar.tkserver.model.blog.BlogPost;
import com.timkoar.tkserver.model.blog.comment.Comment;
import com.timkoar.tkserver.model.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // convert comment entity to comment dto

    public static CommentRequest toDTO(Comment comment) {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setId(comment.getId());
        commentRequest.setContent(comment.getContent());
        commentRequest.setCreatedDate(comment.getCreatedDate().format(formatter));
        commentRequest.setUserId(comment.getUser().getId());
        commentRequest.setUsername(comment.getUser().getUsername());
        return commentRequest;
    }

    public static Comment toEntity(CommentRequest commentRequest, BlogPost blogPost, User user) {

        if (blogPost == null) {
            throw new IllegalArgumentException("BlogPost cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        Comment comment = new Comment();
        comment.setId(commentRequest.getId());
        comment.setContent(commentRequest.getContent());
        comment.setCreatedDate(LocalDateTime.now());
        comment.setBlogPost(blogPost);
        comment.setUser(user);
        return comment;
    }
}
