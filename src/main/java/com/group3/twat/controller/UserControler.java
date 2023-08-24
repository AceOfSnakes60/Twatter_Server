package com.group3.twat.controller;

import com.group3.twat.controller.requests.UserRegistrationRequest;
import com.group3.twat.controller.requests.ValidationRequest;
import com.group3.twat.controller.requests.ValidationResponse;
import com.group3.twat.model.user.User;
import com.group3.twat.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        userService.addUser(new User(0, newUser.username(), newUser.email(), newUser.password()));
        return "redirect:/user";
    }

    @PostMapping("/validate")
    public ValidationResponse validate(@RequestBody ValidationRequest request){
        System.out.println("Validate");
        System.out.println(request.email() + " " + request.password());
        return new ValidationResponse(true, true);
    }
}
