package com.group3.twat.model.user.service.DAO;

import com.group3.twat.model.group.Group;
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


    @Override
    public void addUserToFriend(Long userId, Long friendId) {
        User user1 = userRepository.findById(userId).orElse(null);
        User friendToAdd = userRepository.findById(friendId).orElse(null);

        if (user1 != null && friendToAdd != null) {
            user1.getFriends().add(friendToAdd);
            userRepository.save(user1);
        }
    }

    @Override
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
