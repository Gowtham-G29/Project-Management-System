package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.model.User;

public interface UserService {

    User findUserProfileByJwt(String jwt)throws Exception;

    User findUserByEmail(String email)throws Exception;

    User findUserById(Long userId)throws Exception;

    User updateUserByProjectSize(User user,int number)throws Exception;

}
