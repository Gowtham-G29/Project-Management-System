package com.g_29.projectManagementSystem.Controller;


import com.g_29.projectManagementSystem.Request.CreateMessageRequest;
import com.g_29.projectManagementSystem.Service.MessageServiceImpl;
import com.g_29.projectManagementSystem.Service.ProjectServiceImpl;
import com.g_29.projectManagementSystem.Service.UserServiceImpl;
import com.g_29.projectManagementSystem.model.Chat;
import com.g_29.projectManagementSystem.model.Message;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageServiceImpl messageServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @PostMapping("/send")
    public ResponseEntity<Message>sendMessage(@RequestBody CreateMessageRequest request,
                                              @RequestHeader("AUTHORIZATION") String jwt)throws Exception{
        User user=userServiceImpl.findUserById(request.getSenderId());

        Chat chat=projectServiceImpl.getChatByProjectId(request.getProjectId());

        if(chat==null){
            throw new Exception("Chat Not found");
        }

        Message sentMessage=messageServiceImpl.sendMessage(request.getSenderId(),
                request.getProjectId(),
                request.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(sentMessage);

    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>>getMessagesByProjectId(@PathVariable Long projectId)throws Exception{
        List<Message>messages=messageServiceImpl.getMessagesByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
