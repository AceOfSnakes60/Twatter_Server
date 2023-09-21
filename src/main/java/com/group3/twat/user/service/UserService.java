package com.group3.twat.user.service;

import com.group3.twat.user.User;
import com.group3.twat.user.service.DAO.UserDao;
import com.group3.twat.user.service.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class UserService {
    private final Validations validations;
    private final UserRepository userRepository;

    @Autowired
    public UserService(Validations validations, UserRepository userRepository) {
        this.validations = validations;
        this.userRepository = userRepository;
    }

    public List<User> getUser() {
        return userRepository.findAll();
    }

    public User getUserByMail(String email){return userRepository.findByEmail(email).get();}
    public User getUserByName(String name){return userRepository.findByUsername(name).get();}

    public ResponseEntity<String> addUser(User newUser) {
        String validationMessage = validations.validateUsername(newUser.getUsername(), userRepository);
        String validationMessage2 = validations.validateUserPassword(newUser.getPassword());
        String validationMessage3 = validations.validateEmail(newUser.getEmail(),userRepository);
        if (validationMessage == null && validationMessage2 == null&& validationMessage3==null) {
            System.out.println(newUser.getUsername());
            String plainPassword = newUser.getPassword();
            String hashedPassword = hashPassword(plainPassword);
            newUser.setPassword(hashedPassword);
            userRepository.save(newUser);

            return ResponseEntity.ok("User added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMessage);
        }
    }

    private String hashPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainPassword);
    }

    public void addUserToFriend(Long userId, Long friendId) {
        User user1 = userRepository.findById(userId).orElse(null);
        User friendToAdd = userRepository.findById(friendId).orElse(null);

        if (user1 != null && friendToAdd != null) {
            user1.getFriends().add(friendToAdd);
            userRepository.save(user1);
        }
    }

    public boolean removeUserFromFriends(Long userId, Long friendId) {
        User user1 = userRepository.findById(userId).orElse(null);
        if (user1 != null) {
            User userToRemove = null;
            for (User user : user1.getFriends()) {
                if (user.getId() == friendId) {
                    userToRemove = user;
                    break;
                }
            }
            if (userToRemove != null) {
                user1.getFriends().remove(userToRemove);
                userRepository.save(user1);
                return true;
            }
        }
        return false;
    }
}
