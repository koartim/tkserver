package com.timkoar.tkserver.mapper;

import com.timkoar.tkserver.dto.BlogPostDTO;
import com.timkoar.tkserver.model.blog.BlogPost;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BlogPostMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static BlogPostDTO toDTO(BlogPost blogPost) {
        BlogPostDTO blogPostDTO = new BlogPostDTO();
        blogPostDTO.setId(blogPost.getId());
        blogPostDTO.setTitle(blogPost.getTitle());
        blogPostDTO.setContent(blogPost.getContent());
        blogPostDTO.setAuthorId(blogPost.getAuthorId());
        blogPostDTO.setAuthorName(blogPost.getAuthorName());
        blogPostDTO.setCreatedDate(blogPost.getCreatedDate().format(formatter));
        return blogPostDTO;
    }

    public static BlogPost toEntity(BlogPostDTO blogPostDTO) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogPostDTO.getTitle());
        blogPost.setContent(blogPostDTO.getContent());
        blogPost.setAuthorId(blogPostDTO.getAuthorId());
        blogPost.setAuthorName(blogPostDTO.getAuthorName());
        blogPost.setCreatedDate(LocalDateTime.now());
        return blogPost;
    }
}
