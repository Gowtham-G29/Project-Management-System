package com.g_29.projectManagementSystem.Repository;

import com.g_29.projectManagementSystem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findByIssueId(Long issueId);
}
