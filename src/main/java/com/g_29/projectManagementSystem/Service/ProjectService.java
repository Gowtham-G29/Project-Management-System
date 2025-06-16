package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.model.Chat;
import com.g_29.projectManagementSystem.model.Project;
import com.g_29.projectManagementSystem.model.User;

import java.util.List;


public interface ProjectService {

    Project createProject(Project project,User user)throws Exception;

    List<Project>getProjectByTeam(User user,String category,String tag)throws Exception;

    Project getProjectById(Long projectId)throws Exception;

    Project deleteProject(Long projectId, Long userId)throws Exception;

    void deleteProject(Long projectId) throws Exception;

    Project updateProject(Project updatedProject, Long id)throws Exception;

    void addUserToProject(Long projectId,Long userId)throws Exception;

    void removeUserFromProject(Long projectId,Long userId)throws Exception;

    Chat getChatByProjectId(Long projectId)throws Exception;

    List<Project>searchProjects(String keyword,User user) throws Exception;


}
