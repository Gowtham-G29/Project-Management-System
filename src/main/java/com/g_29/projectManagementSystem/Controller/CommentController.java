package com.g_29.projectManagementSystem.Controller;

import com.g_29.projectManagementSystem.Request.CreateCommentRequest;
import com.g_29.projectManagementSystem.Response.MessageResponse;
import com.g_29.projectManagementSystem.Service.CommentServiceImpl;
import com.g_29.projectManagementSystem.Service.UserServiceImpl;
import com.g_29.projectManagementSystem.model.Comment;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @PostMapping("/create")
    public ResponseEntity<Comment>createComment(@RequestBody CreateCommentRequest request,
                                                @RequestHeader("AUTHORIZATION") String jwt)throws Exception{

        User user=userServiceImpl.findUserProfileByJwt(jwt);
        Comment createdComment=commentServiceImpl.createComment(
                request.getIssueId(),
                user.getId(),
                request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse>deleteComment(@PathVariable Long commentId,
                                                        @RequestHeader("AUTHORIZATION") String jwt)throws Exception{

        User user =userServiceImpl.findUserProfileByJwt(jwt);
        commentServiceImpl.deleteComment(commentId,user.getId());

        MessageResponse response=new MessageResponse();
        response.setMessage("Comment deleted Successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);

    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>>getCommentsByIssueId(@PathVariable Long issueId) throws Exception {
        List<Comment>comments=commentServiceImpl.findCommentByIssueId(issueId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);

    }


}
