package com.timkoar.tkserver.repository;

import com.timkoar.tkserver.model.blog.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogPost_IdOrderByCreatedDateDesc(Long blogPostId);
}
