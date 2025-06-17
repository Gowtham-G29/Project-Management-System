package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Repository.MessageRepo;
import com.g_29.projectManagementSystem.Repository.UserRepo;
import com.g_29.projectManagementSystem.model.Chat;
import com.g_29.projectManagementSystem.model.Message;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProjectServiceImpl projectServiceImpl;


    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {

        User sender= userRepo.findById(senderId).orElseThrow(()->new Exception("User Nit found in this id"));

        Chat chat =projectServiceImpl.getProjectById(projectId).getChat();

        Message message=new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);

        Message savedMessage=messageRepo.save(message);
        chat.getMessage().add(message);

        return savedMessage;


    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat =projectServiceImpl.getChatByProjectId(projectId);
        List<Message>messages=messageRepo.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return messages;
    }
}
