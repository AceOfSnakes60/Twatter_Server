package com.group3.twat.group.service;

import com.group3.twat.group.model.Group;
import com.group3.twat.group.Repository.GroupRepository;
import com.group3.twat.user.model.User;
import com.group3.twat.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository=groupRepository;
        this.userRepository=userRepository;

    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }



    public void addGroup(Group newGroup) {
        groupRepository.save(newGroup);
    }
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }


    public boolean deleteGroupById(Long groupId) {
        if (groupRepository.existsById(groupId)) {
            groupRepository.deleteById(groupId);
            return true;
        }
        return false;
    }


    public boolean addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (group != null && user != null) {
            group.getUsers().add(user);
            groupRepository.save(group);
        }
        return true;
    }
    public boolean removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            return false;
        }

            User userToRemove = null;
            for (User user : group.getUsers()) {
                if (user.getId()==(userId)) {
                    userToRemove = user;
                    break;
                }
            }
            if (userToRemove != null) {
                group.getUsers().remove(userToRemove);
                groupRepository.save(group);
                return true;
            }

        return false;
    }

}
