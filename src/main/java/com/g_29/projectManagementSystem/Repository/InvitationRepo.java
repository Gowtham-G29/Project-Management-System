package com.g_29.projectManagementSystem.Repository;

import com.g_29.projectManagementSystem.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepo extends JpaRepository<Invitation,Long> {

    Invitation findByToken(String token);
    Invitation findByEmail(String userEmail);
}
