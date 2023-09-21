package com.group3.twat.group;

import com.group3.twat.group.model.Group;
import com.group3.twat.group.service.GroupService;
import com.group3.twat.requests.GroupCreationRequest;
import com.group3.twat.user.model.User;
import com.group3.twat.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/groups")
    public List <Group> getGroup() {
        System.out.println("Get groups");
        return groupService.getAllGroups();
    }


    @PostMapping("/groups")
    public ResponseEntity addGroup(@RequestBody GroupCreationRequest newGroupRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByName(username);

        Group newGroup = new Group(
                null,
                newGroupRequest.name(),
                newGroupRequest.description(),
                LocalDate.now(),
                user,
                null
        );
        groupService.addGroup(newGroup);
        return ResponseEntity.ok().build();
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
