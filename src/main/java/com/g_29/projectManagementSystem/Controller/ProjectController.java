package com.g_29.projectManagementSystem.Controller;

import com.g_29.projectManagementSystem.Response.MessageResponse;
import com.g_29.projectManagementSystem.Service.ProjectServiceImpl;
import com.g_29.projectManagementSystem.Service.UserServiceImpl;
import com.g_29.projectManagementSystem.model.Chat;
import com.g_29.projectManagementSystem.model.Project;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/getProjects")
    public ResponseEntity<List<Project>>getProject(@RequestParam(required = false)String category,
                                                   @RequestParam(required = false)String tag,
                                                   @RequestHeader("Authorization")String jwt) throws Exception {

        User user=userServiceImpl.findUserProfileByJwt(jwt);
        List<Project>projects=projectServiceImpl.getProjectByTeam(user,category,tag);
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project>getProjectById(@PathVariable Long projectId,
                                                   @RequestHeader("Authorization")String jwt) throws Exception {

        User user=userServiceImpl.findUserProfileByJwt(jwt);
        Project project= projectServiceImpl.getProjectById(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }


    @PostMapping("/create")
    public ResponseEntity<Project>createProject(@RequestBody Project project,
                                                   @RequestHeader("Authorization")String jwt) throws Exception {

        User user=userServiceImpl.findUserProfileByJwt(jwt);
        Project createdproject= projectServiceImpl.createProject(project,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdproject);
    }

    @PatchMapping("/update/{projectId}")
    public ResponseEntity<Project>updateProject(@PathVariable Long projectId,
                                                @RequestHeader("Authorization")String jwt ,
                                                @RequestBody Project project) throws Exception {

        User user=userServiceImpl.findUserProfileByJwt(jwt);

        Project updatedproject= projectServiceImpl.updateProject(project,projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedproject);
    }


    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<MessageResponse>deleteProject(@PathVariable Long projectId,
                                                @RequestHeader("Authorization")String jwt ,
                                                @RequestBody Project project) throws Exception {

        User user=userServiceImpl.findUserProfileByJwt(jwt);
        projectServiceImpl.deleteProject(projectId);

        MessageResponse response=new MessageResponse();
        response.setMessage("Project is deleted Successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>>searchProject(@RequestParam(required = false)String keyword,
                                                   @RequestHeader("Authorization")String jwt) throws Exception {

        User user=userServiceImpl.findUserProfileByJwt(jwt);
        List<Project>projects=projectServiceImpl.searchProjects(keyword,user);
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<Chat>getChatByProjectId(@PathVariable Long projectId,
                                                 @RequestHeader("Authorization")String jwt) throws Exception {

        User user=userServiceImpl.findUserProfileByJwt(jwt);
        Chat chat= projectServiceImpl.getChatByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(chat);
    }









}
