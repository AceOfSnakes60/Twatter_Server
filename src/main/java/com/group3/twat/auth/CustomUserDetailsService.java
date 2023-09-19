package com.group3.twat.auth;

import com.group3.twat.user.User;
import com.group3.twat.user.service.DAO.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public CustomUserDetailsService(UserRepository repository){
        userRepository = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> find = userRepository.findByUsername(username);
        if(!find.isPresent()){
            find = userRepository.findByEmail(username);

            if(!find.isPresent()){
                throw new UsernameNotFoundException("User Not found with username: " + username);
            }
        }
        return find.get();
    }
}
