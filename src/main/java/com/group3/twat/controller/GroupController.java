package com.group3.twat.controller;

import com.group3.twat.model.group.Group;
import com.group3.twat.model.group.service.GroupService;
import com.group3.twat.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public List <Group> getGroup() {
        return groupService.getAllGroups();
    }


    @PostMapping("/groups")
    public String addGroup(@RequestBody Group newGroup) {
        groupService.addGroup(newGroup);
        return "redirect:/groups";
    }


    @GetMapping("/groups/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long groupId) {
        Group group = groupService.getGroupById(groupId);
        if (group != null) {
            return ResponseEntity.ok(group);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<String> deleteGroupById(@PathVariable Long groupId) {
        boolean deleted = groupService.deleteGroupById(groupId);
        if (deleted) {
            return ResponseEntity.ok("Group deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/{groupId}/addUser/{userId}")
    public ResponseEntity<String> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {

        boolean success = groupService.addUserToGroup(groupId, userId);
        if (success) {
            return ResponseEntity.ok("User added to group successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add user to group");
        }
    }
    @DeleteMapping("/groups/{groupId}/{userId}")
    public ResponseEntity<String> removeUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        Group group = groupService.getGroupById(groupId);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }

        boolean removed = groupService.removeUserFromGroup(groupId, userId);
        if (removed) {
            return ResponseEntity.ok("User removed from the group successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
