package com.group3.twat.model.group.service.DAO;

import com.group3.twat.model.group.Group;
import com.group3.twat.model.post.service.DAO.TwattReopsitory;
import com.group3.twat.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupMemory implements GroupDao {
    private final GroupRepository groupRepository;
    @Autowired
    public GroupMemory(GroupRepository groupRepository) {
        this.groupRepository=groupRepository;
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
    public void addUserToGroup(Long groupId, User user) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group != null) {
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
