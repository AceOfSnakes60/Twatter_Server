package com.group3.twat.model.user.service.DAO;

import com.group3.twat.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Repository
public class UserMemory implements UserDao {
    private final UserRepository userRepository;

    @Autowired
    public UserMemory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User newUser) {
        String plainPassword = newUser.getPassword();
        String hashedPassword = hashPassword(plainPassword);
        newUser.setPassword(hashedPassword);
        userRepository.save(newUser);
    }



    private String hashPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainPassword);
    }
}
