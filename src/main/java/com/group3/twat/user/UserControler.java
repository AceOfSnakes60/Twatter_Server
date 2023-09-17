package com.group3.twat.user;

import com.group3.twat.requests.UserRegistrationRequest;
import com.group3.twat.requests.ValidationRequest;
import com.group3.twat.requests.ValidationResponse;
import com.group3.twat.user.service.UserService;
import com.group3.twat.user.service.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControler {


    private final UserService userService;

    @Autowired
    public UserControler(UserService userService) {
        this.userService = userService;
    }
    //@PreAuthorize("hasRole('USER')")
    @GetMapping()
    public List<User> getUser() {
        return userService.getUser();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //TODO It could be user or email
        User user = userService.getUserByMail(username);
        System.out.println("Works");
        System.out.println(user.getUsername());
        return user;
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

    @DeleteMapping("/user/{userId}/removeFriend/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {

        boolean success = userService.removeUserFromFriends(userId, friendId);
        if (success) {
            return ResponseEntity.ok("Friend removed successfully");
        }
        return ResponseEntity.badRequest().body("Failed to remove friend");

    }

}
