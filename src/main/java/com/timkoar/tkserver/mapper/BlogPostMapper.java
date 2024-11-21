package com.timkoar.tkserver.mapper;

import com.timkoar.tkserver.dto.BlogPostRequest;
import com.timkoar.tkserver.model.blog.BlogPost;
import com.timkoar.tkserver.model.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class BlogPostMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static BlogPostRequest toDTO(BlogPost blogPost) {
        BlogPostRequest blogPostRequest = new BlogPostRequest();
        blogPostRequest.setId(blogPost.getId());
        blogPostRequest.setTitle(blogPost.getTitle());
        blogPostRequest.setContent(blogPost.getContent());
        blogPostRequest.setAuthorId(blogPost.getAuthor().getId());
        blogPostRequest.setAuthorName(blogPost.getAuthor().getUsername());
        blogPostRequest.setComments(blogPost.getComments().stream()
                .map(CommentMapper::toDTO).collect(Collectors.toList()));
        return blogPostRequest;
    }

    public static BlogPost toEntity(BlogPostRequest blogPostRequest, User author) {
        BlogPost blogPost = new BlogPost();
        blogPost.setId(blogPostRequest.getId());
        blogPost.setTitle(blogPostRequest.getTitle());
        blogPost.setContent(blogPostRequest.getContent());
        blogPost.setAuthor(author);
        blogPost.setCreated(LocalDateTime.now());
        return blogPost;
    }
}
