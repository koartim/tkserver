package com.timkoar.tkserver.model.blog.comment;

import com.timkoar.tkserver.model.blog.BlogPost;
import com.timkoar.tkserver.model.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private String username;

    private LocalDateTime createdDate = LocalDateTime.now();

    public Comment() {}

    public Comment(Long id, Long postId, Long userId, String content, String username, LocalDateTime createdDate) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.username = username;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
