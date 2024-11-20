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

    @Autowired
    private BlogPostRepository blogPostRepository;

    private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    public List<BlogPost> getAllPosts() {
        logger.info("Fetching all blog posts...");
        List<BlogPost> posts = blogPostRepository.findAll();
        logger.info("Number of posts retrieved: " + posts.size());
        return posts;
    }

    public Optional<BlogPost> getPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    public BlogPost createPost(BlogPost post) {
        return blogPostRepository.save(post);
    }

    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }
}
