package com.timkoar.tkserver.service;

import com.timkoar.tkserver.model.blog.BlogPost;
import com.timkoar.tkserver.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public BlogPost savePost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    public BlogPost getPostById(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + id + " not found"));
    }

    public void deletePost(Long postId) {
        blogPostRepository.deleteById(postId);
    }
}
