package com.group3.twat.controller;

import com.group3.twat.controller.requests.UserRegistrationRequest;
import com.group3.twat.controller.requests.ValidationRequest;
import com.group3.twat.controller.requests.ValidationResponse;
import com.group3.twat.model.user.User;
import com.group3.twat.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControler {

    private final UserService userService;

    @Autowired
    public UserControler(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUser() {
        return userService.getUser();
    }

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable Long id) {
        System.out.println(id);
        List<User> userList = userService.getUser();
        return userList.stream().filter(e->e.getId()==id).findFirst().get();
    }


    @PostMapping()
    public String addUser(@RequestBody UserRegistrationRequest newUser) {

        User user = new User();
        user.setUsername(newUser.username());
        user.setEmail(newUser.email());
        user.setPassword(newUser.password());

        userService.addUser(user);
        return "redirect:/user";
    }

    @PostMapping("/validate")
    public ValidationResponse validate(@RequestBody ValidationRequest request) {
        System.out.println("Validate");
        System.out.println(request.email() + " " + request.password());
        return new ValidationResponse(true, true);
    }

    @PostMapping("/{userId}/addFriend/{friendId}")
    public ResponseEntity<String> addFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {

        try {
            userService.addUserToFriend(userId, friendId);
            return ResponseEntity.ok("Friend added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add friend");
        }
    }

    @DeleteMapping("/{userId}/removeFriend/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {

        boolean success = userService.removeUserFromFriends(userId, friendId);
        if (success) {
            return ResponseEntity.ok("Friend removed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to remove friend");
        }
    }

}
