package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Repository.InvitationRepo;
import com.g_29.projectManagementSystem.model.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    private InvitationRepo invitationRepo;

    @Autowired
    private EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws Exception {

        String invitationToken= UUID.randomUUID().toString();

        Invitation invitation=new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepo.save(invitation);

        String invitationLink="http://localhost:8080/accept_invitation?token"+invitationToken;
        emailService.sendEmailWithToken(email,invitationLink);

    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation=invitationRepo.findByToken(token);

        if(invitation==null){
            throw new Exception("Invalid invitation token");
        }

        return invitation;
    }

    @Override
    public String getTokenByUserEmail(String userEmail) throws Exception {
        Invitation invitation=invitationRepo.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) throws Exception {
        Invitation invitation=invitationRepo.findByToken(token);
        invitationRepo.delete(invitation);

    }
}
