package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Repository.UserRepo;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User findUser(User user){
        return userRepo.findByEmail(user.getEmail());
    }

    public User createUser(User user){
        return userRepo.save(user);
    }

}
