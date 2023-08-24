package com.group3.twat.model.group.service.DAO;

import com.group3.twat.model.group.Group;
import com.group3.twat.model.post.service.DAO.TwattReopsitory;
import com.group3.twat.model.user.User;
import com.group3.twat.model.user.service.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupMemory implements GroupDao {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    @Autowired
    public GroupMemory(GroupRepository groupRepository,UserRepository userRepository) {
        this.groupRepository=groupRepository;
        this.userRepository=userRepository;
    }





    @Override
    public List<Group> getGroup() {
        return groupRepository.findAll();
    }


    @Override
    public void addGroup(Group newGroup) {
        groupRepository.save(newGroup);
    }

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    @Override
    public boolean deleteGroupById(Long groupId) {
        if (groupRepository.existsById(groupId)) {
            groupRepository.deleteById(groupId);
            return true;
        }
        return false;
    }


    @Override
    public void addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (group != null && user != null) {
            group.getUsers().add(user);
            groupRepository.save(group);
        }
    }



    @Override
    public boolean removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group != null) {
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
        }
        return false;
    }
}
