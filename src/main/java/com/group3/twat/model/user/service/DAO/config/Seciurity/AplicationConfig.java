package com.group3.twat.model.user.service.DAO.config.Seciurity;

import com.group3.twat.model.user.service.DAO.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.plaf.PanelUI;
import java.security.PublicKey;

@Configuration
@RequiredArgsConstructor
public class AplicationConfig {
    private final UserRepository repository;
    @Bean
    public UserDetailsService userDetailsService(){
 return username -> repository.findByEmail(username)
         .orElseThrow(()->new UsernameNotFoundException("user not found"));
//TODO
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider =new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationMenager(AuthenticationConfiguration config) throws Exception {
        return  config.getAuthenticationManager();
    }
@Bean
    public  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
