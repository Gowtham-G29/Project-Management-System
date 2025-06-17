package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.model.Invitation;

public interface InvitationService {

    void sendInvitation(String email,Long projectId) throws Exception;

    Invitation acceptInvitation(String token,Long userId)throws Exception;

    String getTokenByUserEmail(String userEmail)throws Exception;

    void deleteToken(String token)throws Exception;

}
