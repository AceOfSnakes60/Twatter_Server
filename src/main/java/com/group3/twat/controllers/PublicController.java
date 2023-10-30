package com.group3.twat.controllers;

import com.group3.twat.group.service.GroupService;
import com.group3.twat.user.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final GroupService groupService;
    private final UserService userService;

    public PublicController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    //TODO delete this controller, for dev use only
    @DeleteMapping("/nuke")
    public void nuke(){

    }


}
