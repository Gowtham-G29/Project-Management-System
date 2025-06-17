package com.g_29.projectManagementSystem.Repository;

import com.g_29.projectManagementSystem.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue,Long> {

    List<Issue>findByProject_Id(Long projectId);
}
