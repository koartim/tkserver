package com.timkoar.tkserver;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="blogpost")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime created;
    private String author;

    public BlogPost() {
    }

    public BlogPost(Long id, String title, LocalDateTime created, String author) {
        this.id = id;
        this.title = title;
        this.created = created;
        this.author = author;
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
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
