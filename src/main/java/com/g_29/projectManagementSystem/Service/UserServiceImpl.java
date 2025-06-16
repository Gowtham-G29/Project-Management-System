package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Config.JwtProvider;
import com.g_29.projectManagementSystem.Repository.UserRepo;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email= JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    public User findUserByEmail(String email) throws Exception{
        User user=userRepo.findByEmail(email);
        if(user==null){
            throw new Exception("user Not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User>optionalUser=userRepo.findById(userId);

        if(optionalUser.isEmpty()){
            throw new Exception("user not found");
        }
        return optionalUser.get();
    }

    @Override
    public User updateUserByProjectSize(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize()+number);
        return userRepo.save(user);
    }

    public User createUser(User user){
        return userRepo.save(user);
    }

}
