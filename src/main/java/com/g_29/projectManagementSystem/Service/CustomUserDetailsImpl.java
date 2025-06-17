package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Repository.UserRepo;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//this enables the custom login details from the db.
// and disable spring security default generated password.
@Service
public class CustomUserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user=userRepo.findByEmail(username);

        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found in this email");
        }
        //here we are not using any authorities so just pass a empty authorities
        List<GrantedAuthority>authorities=new ArrayList<>();

        //this will load the Custom user from the database instead of Hardcoded user.
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),user.get().getPassword(),authorities);

    }
}
