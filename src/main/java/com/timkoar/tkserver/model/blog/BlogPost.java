package com.timkoar.tkserver.model.blog;

import com.timkoar.tkserver.model.blog.comment.Comment;
import com.timkoar.tkserver.model.user.User;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="blogpost")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String authorName;

    private LocalDateTime createdDate = LocalDateTime.now();

    public BlogPost() {
    }

    public BlogPost(Long id, String title, String content, LocalDateTime createdDate, Long authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.authorName = authorName;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
