package com.Rajasekhar.service;

import com.Rajasekhar.model.Invitation;
import com.Rajasekhar.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class InvitationServiceImpl extends InvitationService{



    @Autowired
    private InvitationRepository invitationRepository;

    private EmailService emailService;
    @Override
    public void sendInvitation(String email, Long projectId) {


        String invitationToken = UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink="http://localhost:5173/accept_invitation?token="+invitationToken;
        emailService.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) {

        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation==null){
            throw new Exception("Invalid inviation token");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();


    }

    @Override
    public void deleteToken(String token) {


        Invitation invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);
    }
}
