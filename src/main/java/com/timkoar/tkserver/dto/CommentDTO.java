package com.timkoar.tkserver.dto;

public class CommentDTO {

    private Long id;
    private String content;
    private String createdDate;
    private String username;
    private Long userId;
    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
