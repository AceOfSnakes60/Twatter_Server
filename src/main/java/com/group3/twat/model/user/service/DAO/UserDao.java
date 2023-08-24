package com.group3.twat.model.user.service.DAO;

import com.group3.twat.model.user.User;

import java.util.List;

public interface UserDao {
    List<User> getUser();

    void addUser(User newUser);
   void addUserToFriend(Long userId, Long friendId);
    boolean  removeUserFromFriends(Long userId, Long friendId );

}
