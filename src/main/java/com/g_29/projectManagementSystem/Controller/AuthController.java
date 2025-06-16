package com.g_29.projectManagementSystem.Controller;

import com.g_29.projectManagementSystem.Config.JwtProvider;
import com.g_29.projectManagementSystem.DTO.LoginRequest;
import com.g_29.projectManagementSystem.Response.AuthResponse;
import com.g_29.projectManagementSystem.Service.CustomUserDetailsImpl;
import com.g_29.projectManagementSystem.Service.UserServiceImpl;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse>createUser(@RequestBody User user) throws Exception {

        User isUserExists=userService.findUserByEmail(user.getEmail());

        if(isUserExists!=null){
            throw new Exception("Email already exist please provide another email");
        }
        User newUser=new User();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());

        User createNewUser=userService.createUser(newUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(createNewUser.getEmail(),createNewUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= JwtProvider.generateToken(authentication);

        //created a new response object for our purpose.
        AuthResponse response=new AuthResponse();
        response.setMessage("Signed Up Successfully");
        response.setJwt(jwt);
        response.setUsername(createNewUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>signIn(@RequestBody LoginRequest loginRequest){

        String userName=loginRequest.email();
        String password=loginRequest.password();

        //check if the user is valid or not using custom authenticate Method.

        Authentication authentication=authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= JwtProvider.generateToken(authentication);

        //created a new response object for our purpose.
        AuthResponse response=new AuthResponse();
        response.setMessage("Login Successfully");
        response.setJwt(jwt);
        response.setUsername(userName);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

    }

    //check the valid user from the db.
    private Authentication authenticate(String userName, String password) {

        UserDetails userDetails=customUserDetails.loadUserByUsername(userName);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
