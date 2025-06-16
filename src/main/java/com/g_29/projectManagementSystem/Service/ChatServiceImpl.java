package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Repository.ChatRepo;
import com.g_29.projectManagementSystem.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepo chatRepo;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepo.save(chat);
    }
}
