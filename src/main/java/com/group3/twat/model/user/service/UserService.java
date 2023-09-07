package com.group3.twat.model.user.service;

import com.group3.twat.model.group.Group;
import com.group3.twat.model.user.User;
import com.group3.twat.model.user.service.DAO.UserDao;
import com.group3.twat.model.user.service.DAO.UserRepository;
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



    public void addUserToFriend(Long userId, Long friendId) {
        userDao.addUserToFriend(userId, friendId);
    }

    public boolean removeUserFromFriends(Long userId, Long friendId) {
        return userDao.removeUserFromFriends(userId, friendId);
    }
}
