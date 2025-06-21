package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Repository.ProjectRepo;
import com.g_29.projectManagementSystem.model.Chat;
import com.g_29.projectManagementSystem.model.Project;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;


    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ChatServiceImpl chatServiceImpl;


    @Override
    public Project createProject(Project project, User user) throws Exception {

        Project createProject=new Project();
        createProject.setOwner(user);
        createProject.setTags(project.getTags());
        createProject.setName(project.getName());
        createProject.setCategory(project.getCategory());
        createProject.setDescription(project.getDescription());
        createProject.setStatus(project.getStatus());
        createProject.getTeam().add(user);

        Project savedProject=projectRepo.save(createProject);

        Chat chat=new Chat();
        chat.setProject(savedProject);
        Chat projectChat=chatServiceImpl.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {

        List<Project>projects=projectRepo.findByTeamContainingOrOwner(user,user);

        //filter projects by category
        if(category!=null){
            projects=projects.stream().filter(project ->
                    project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }

        //filter projects by Tags
        if(tag!=null){
            projects=projects.stream().filter(project ->
                            project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project>optionalProject=projectRepo.findById(projectId);

        if(optionalProject.isEmpty()){
            throw new Exception("project not found");
        }
        return optionalProject.get();

    }

    @Override
    public Project deleteProject(Long projectId, Long userId) throws Exception {
        return null;
    }


    @Override
    public void deleteProject(Long projectId) throws Exception {

         getProjectById(projectId);
//        userServiceImpl.findUserById(userId);

         projectRepo.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project=getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepo.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {

        Project project=getProjectById(projectId);
        User user=userServiceImpl.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        projectRepo.save(project);

    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project=getProjectById(projectId);
        User user=userServiceImpl.findUserById(userId);
        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepo.save(project);

    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project=getProjectById(projectId);

        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        List<Project>projects=projectRepo.findByNameContainingAndTeamContains(keyword,user);
        return projects;
    }


}
