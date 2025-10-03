package com.Rajasekhar.controller;


import com.Rajasekhar.model.Chat;
import com.Rajasekhar.model.Invitation;
import com.Rajasekhar.model.Project;
import com.Rajasekhar.model.User;
import com.Rajasekhar.repository.InviteRequest;
import com.Rajasekhar.response.MessageResponse;
import com.Rajasekhar.service.InvitationService;
import com.Rajasekhar.service.ProjectService;
import com.Rajasekhar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private InviteRequest inviteRequest;

    @Autowired
    private InvitationService invitationService;


    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        List<Project> projects =projectService.getProjectByTeam(user,category,tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(

           @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
        Project projects =projectService.getProjectById(projectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(

            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception{

        User user=userService.findUserProfileByJwt(jwt);
        Project projects =projectService.createProject(project,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(

            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Project projects =projectService.updateProject(project,projectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(

            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Project projects =projectService.deleteProject(projectId,user.getId());

        MessageResponse res=new MessageResponse("Project deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Project>> serachProjects(

            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);

        Project projects =projectService.searchProjects(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }


    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(

            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);

        Chat chat =projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(

            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(inviteRequest.getEmail(), inviteRequest.getProjectId());
        MessageResponse res=new MessageResponse("User Invitation sent successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/accept_invitaion")
    public ResponseEntity<Invitation> acceptInvitation(

            @RequestParam String token,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Invitation invitation=invitationService.acceptInvitation(token,user.getId());
        projectService.addUserToProject(invitation.getProjectId(),project.getId());




        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }


}
