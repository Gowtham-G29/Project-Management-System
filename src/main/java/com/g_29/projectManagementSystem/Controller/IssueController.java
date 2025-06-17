package com.g_29.projectManagementSystem.Controller;

import com.g_29.projectManagementSystem.DTO.IssueDTO;
import com.g_29.projectManagementSystem.Request.IssueRequest;
import com.g_29.projectManagementSystem.Response.MessageResponse;
import com.g_29.projectManagementSystem.Service.IssueServiceImpl;
import com.g_29.projectManagementSystem.Service.UserServiceImpl;
import com.g_29.projectManagementSystem.model.Issue;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueServiceImpl issueServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue>getIssueById(@PathVariable Long issueId) throws Exception{

        Issue issue=issueServiceImpl.getIssueById(issueId);
        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>>getIssueByProjectId(@PathVariable Long projectId)throws Exception{

        List<Issue>issues=issueServiceImpl.getIssueByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }

    @PostMapping("/create")
    public ResponseEntity<IssueDTO>createIssue(@RequestBody IssueRequest issueRequest,
                                               @RequestHeader("AUTHORIZATION") String token) throws Exception{

        User tokenUser=userServiceImpl.findUserProfileByJwt(token);
        User user=userServiceImpl.findUserById(tokenUser.getId());

        Issue createdIssue=issueServiceImpl.createIssue(issueRequest,tokenUser);
        IssueDTO issueDTO=new IssueDTO();
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setId(createdIssue.getId());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setProjectId(createdIssue.getProjectId());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setAssignee(createdIssue.getAssignee());

        return ResponseEntity.status(HttpStatus.CREATED).body(issueDTO);

    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse>deleteIssue(@PathVariable Long issueId,
                                                      @RequestHeader("AUTHORIZATION") String token) throws Exception{
        User tokenUser=userServiceImpl.findUserProfileByJwt(token);
        issueServiceImpl.deleteIssue(issueId,tokenUser.getId());

        MessageResponse response=new MessageResponse();
        response.setMessage("Issue deleted");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);

    }
    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue>addUserToIssue(@PathVariable Long issueId,
                                               @PathVariable Long userId)throws Exception{
        Issue issue=issueServiceImpl.addUserToIssue(issueId,userId);
        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue>updateIssueStatus(@PathVariable String status,
                                                  @PathVariable Long issueId)throws Exception{

        Issue issue=issueServiceImpl.updatedStatus(issueId,status);
        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }


}

