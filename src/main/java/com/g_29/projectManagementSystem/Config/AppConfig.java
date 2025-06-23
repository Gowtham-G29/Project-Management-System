package com.g_29.projectManagementSystem.Config;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{

         httpSecurity.sessionManagement(Management->Management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
         httpSecurity.authorizeHttpRequests(Authorize->Authorize.
                 requestMatchers("/register", "/login").permitAll()
                 .anyRequest().authenticated());

         httpSecurity.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class);
         httpSecurity.csrf(csrf->csrf.disable());
         httpSecurity.cors(cors->cors.configurationSource(corsConfigurationSource()));
         httpSecurity.httpBasic(Customizer.withDefaults());

         return httpSecurity.build();
     }



     //global declaration of cors
     private CorsConfigurationSource corsConfigurationSource(){
         return new CorsConfigurationSource() {
             @Override
             public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                 CorsConfiguration configuration=new CorsConfiguration();
                 configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "https://plannestor.netlify.app"));
                 configuration.setAllowedMethods(Collections.singletonList("*"));
                 configuration.setAllowCredentials(true);
                 configuration.setAllowedHeaders(Collections.singletonList("*"));
                 configuration.setExposedHeaders(Arrays.asList("Authorization"));
                 configuration.setMaxAge(3600L);
                 return configuration;
             }
         };
   }

   //Enable password encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
    }

}
