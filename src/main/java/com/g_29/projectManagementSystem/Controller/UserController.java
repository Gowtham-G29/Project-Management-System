package com.g_29.projectManagementSystem.Controller;

import com.g_29.projectManagementSystem.Service.UserServiceImpl;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/profile")
    public ResponseEntity<User>getUserProfile(@RequestHeader("AUTHORIZATION") String jwt) throws Exception {
        User user=userServiceImpl.findUserProfileByJwt(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
