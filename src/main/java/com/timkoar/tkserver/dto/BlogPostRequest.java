package com.timkoar.tkserver.dto;

import java.util.List;

public class BlogPostRequest {

    private Long id;
    private String title;
    private String content;
    private String createdDate;
    private Long authorId;
    private String authorName;
    private List<CommentRequest> comments;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<CommentRequest> getComments() {
        return comments;
    }

    public void setComments(List<CommentRequest> comments) {
        this.comments = comments;
    }
}
