package com.g_29.projectManagementSystem.Service;

public interface EmailService {

     void sendEmailWithToken(String userEmail,String token)throws Exception;

}
