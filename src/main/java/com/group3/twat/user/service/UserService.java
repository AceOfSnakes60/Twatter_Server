package com.group3.twat.user.service;

import com.group3.twat.user.User;
import com.group3.twat.user.service.DAO.UserDao;
import com.group3.twat.user.service.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final Validations validations;
    private final UserDao userDao;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserDao userDao, Validations validations, UserRepository userRepository) {
        this.userDao = userDao;
        this.validations = validations;
        this.userRepository = userRepository;
    }

    public List<User> getUser() {
        return userDao.getUser();
    }

    public ResponseEntity<String> addUser(User newUser) {
        String validationMessage = validations.validateUsername(newUser.getUsername(), userRepository);
        String validationMessage2 = validations.validateUserPassword(newUser.getPassword());
        String validationMessage3 = validations.validateEmail(newUser.getEmail(),userRepository);
        if (validationMessage == null && validationMessage2 == null&& validationMessage3==null) {
            System.out.println(newUser.getUsername());
            userDao.addUser(newUser);

            return ResponseEntity.ok("User added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMessage);
        }
    }

    public void addUserToFriend(Long userId, Long friendId) {
        userDao.addUserToFriend(userId, friendId);
    }

    public boolean removeUserFromFriends(Long userId, Long friendId) {
        return userDao.removeUserFromFriends(userId, friendId);
    }
}
