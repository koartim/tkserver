package com.timkoar.tkserver.controller;

import com.timkoar.tkserver.dto.BlogPostRequest;
import com.timkoar.tkserver.mapper.BlogPostMapper;
import com.timkoar.tkserver.model.blog.BlogPost;
import com.timkoar.tkserver.model.user.User;
import com.timkoar.tkserver.service.BlogPostService;
import com.timkoar.tkserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<BlogPostRequest>> getBlogPosts() {
        List<BlogPostRequest> posts = blogPostService.getAllPosts()
                .stream()
                .map(BlogPostMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostRequest> getBlogPostById(@PathVariable long id) {
        BlogPost post = blogPostService.getPostById(id);
        BlogPostRequest response = BlogPostMapper.toDTO(post);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BlogPostRequest> createPost(@RequestBody BlogPostRequest blogPostRequest) {
        User author = userService.findById(blogPostRequest.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        BlogPost blogPost = BlogPostMapper.toEntity(blogPostRequest, author);
        BlogPost savedPost = blogPostService.savePost(blogPost);
        BlogPostRequest response = BlogPostMapper.toDTO(savedPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}