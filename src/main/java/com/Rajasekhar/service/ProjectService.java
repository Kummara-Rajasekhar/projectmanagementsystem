package com.Rajasekhar.service;

import com.Rajasekhar.model.Project;
import com.Rajasekhar.model.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, Long userId) throws Exception;
    List<Project>  getProjectByTeam(User user,String Category, String tag) throws Exception;

    List<Project> getProjectByTeam(User user, String Category, String tag) throws Exception;

    Project getProjectById(Long projectId) throws Exception;

    void degradeProject(Long projectId, Long userId) throws Exception;
    Project updateProject(Long projectId, Project updatedProject, Long id) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;
    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws Exception;

    List<Project> searchProjects(String keyword,User user) throws Exception;
}
