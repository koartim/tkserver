package com.timkoar.tkserver.mapper;

import com.timkoar.tkserver.dto.CommentDTO;
import com.timkoar.tkserver.model.blog.comment.Comment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setPostId(comment.getPostId());
        commentDTO.setUserId(comment.getUserId());
        commentDTO.setUsername(comment.getUsername());
        commentDTO.setCreatedDate(comment.getCreatedDate().format(formatter));
        return commentDTO;
    }

    public static Comment toEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPostId(commentDTO.getPostId());
        comment.setUserId(commentDTO.getUserId());
        comment.setUsername(commentDTO.getUsername());
        comment.setCreatedDate(LocalDateTime.now());
        return comment;
    }
}
