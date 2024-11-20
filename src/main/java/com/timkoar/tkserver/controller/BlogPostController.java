package com.timkoar.tkserver.controller;

import com.timkoar.tkserver.DTO.BlogPostRequest;
import com.timkoar.tkserver.model.blog.BlogPost;
import com.timkoar.tkserver.model.user.User;
import com.timkoar.tkserver.service.BlogPostService;
import com.timkoar.tkserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogPostController {

    private final BlogPostService blogPostService;
    private final UserService userService;

    public BlogPostController(BlogPostService blogPostService, UserService userService) {
        this.blogPostService = blogPostService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<BlogPost>> getBlogPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable long id) {
        try {
            return ResponseEntity.ok(blogPostService.getPostById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<BlogPost> createPost(@RequestBody BlogPostRequest blogPostRequest) {
        // restricted to users with admin role
        try {
            User author = userService.findById(blogPostRequest.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Author not found"));

            BlogPost blogPost = new BlogPost();
            blogPost.setTitle(blogPostRequest.getTitle());
            blogPost.setContent(blogPostRequest.getContent());
            blogPost.setAuthor(author);
            BlogPost newPost = blogPostService.savePost(blogPost);

            return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}