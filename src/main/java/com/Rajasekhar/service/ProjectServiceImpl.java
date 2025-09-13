package com.Rajasekhar.service;

import com.Rajasekhar.model.Chat;
import com.Rajasekhar.model.Project;
import com.Rajasekhar.model.User;
import com.Rajasekhar.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    private ChatService chatService;

    @Autowired
    private UserService userService;

    private
    @Override
    public Project createProject(Project project, Long userId) throws Exception {

        Projecct createdProject=new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project savedProject=projectRepository.save(createdProject);
        Chat chat=new Chat();
        chat.setProject(savedProject);
        Chat projectChat=chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return null;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String Category, String tag) throws Exception {
        List<Project> projects=projectRepository.findByTeamContainingOrOwner(user,user);
        if (Category != null) {
            projects=projects.stream().filter(project -> Category.equals(project.getTags())).collect(Collectors.toList());
        }
        if(tag!=null){
            projects=projects.stream().filter(project -> tag.equals(project.getTags())).contains(tag));
        }


        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {

        Optional<Project> optionalProject=projectRepository.findById(projectId);
        if(optionalProject.isEmpty()){
            throw new Exception("Project not found");
        }

        return optionalProject.get();
    }

    @Override
    public void degradeProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId);
        projectRepository.deleteById(projectId);

    }

    @Override
    public Project updateProject(Long projectId, Project updatedProject, Long id) throws Exception {

        Project project=getProjectById(projectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setCategory(updatedProject.getCategory());
        project.setTags(updatedProject.getTags());
        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {

        Project project = getProjectById(projectId);
        User user=userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getTeam().add(user);
            project.getChat().getUsers().add(user);
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user=userService.findUserById(userId);
        if(project.getTeam().contains(user)){
            project.getTeam().remove(user);
            project.getChat().getUsers().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {

        Project project = getProjectById(projectId);

        return project.getChat();
    }
}
