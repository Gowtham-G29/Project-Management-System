package com.g_29.projectManagementSystem.Repository;

import com.g_29.projectManagementSystem.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {

    List<Message>findByChatIdOrderByCreatedAtAsc(Long chatId);


}
