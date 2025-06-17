package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Request.IssueRequest;
import com.g_29.projectManagementSystem.model.Issue;
import com.g_29.projectManagementSystem.model.User;

import java.util.List;

public interface IssueService {

    Issue getIssueById(Long issueId)throws Exception;

    List<Issue>getIssueByProjectId(Long projectId)throws Exception;

    Issue createIssue(IssueRequest issueRequest, User user)throws Exception;

    void deleteIssue(Long issueId,Long userId) throws Exception;

    Issue addUserToIssue(Long issueId,Long userId)throws Exception;

    Issue updatedStatus(Long issueId,String status)throws Exception;


}
