package com.timkoar.tkserver.controller;

import com.timkoar.tkserver.dto.BlogPostDTO;
import com.timkoar.tkserver.mapper.BlogPostMapper;
import com.timkoar.tkserver.model.blog.BlogPost;
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
    public ResponseEntity<List<BlogPostDTO>> getBlogPosts() {
        List<BlogPostDTO> posts = blogPostService.getAllPosts()
                .stream()
                .map(BlogPostMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDTO> getBlogPostById(@PathVariable long id) {
        BlogPost post = blogPostService.getPostById(id);
        BlogPostDTO response = BlogPostMapper.toDTO(post);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BlogPostDTO> createPost(@RequestBody BlogPostDTO blogPostDTO) {
        BlogPost blogPost = BlogPostMapper.toEntity(blogPostDTO);
        BlogPost savedPost = blogPostService.savePost(blogPost);
        BlogPostDTO response = BlogPostMapper.toDTO(savedPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        blogPostService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}