package com.g_29.projectManagementSystem.Repository;

import com.g_29.projectManagementSystem.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends JpaRepository<Chat,Long> {
}
