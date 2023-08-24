package com.group3.twat.model.group.service;

import com.group3.twat.model.group.Group;
import com.group3.twat.model.group.service.DAO.GroupDao;
import com.group3.twat.model.user.User;
import com.group3.twat.model.user.service.DAO.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GroupService {

    private final GroupDao groupDao;
private  final UserDao userDao;
    @Autowired
    public GroupService(UserDao userDao, GroupDao groupDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
    }

    public List<Group> getAllGroups() {
        return groupDao.getGroup();
    }



    public void addGroup(Group newGroup) {
        groupDao.addGroup(newGroup);
    }
    public Group getGroupById(Long groupId) {
        return groupDao.getGroupById(groupId);
    }


    public boolean deleteGroupById(Long groupId) {
        return groupDao.deleteGroupById(groupId);
    }


    public boolean addUserToGroup(Long groupId, Long userId) {
        groupDao.addUserToGroup(groupId, userId);
        return true;
    }
    public boolean removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupDao.getGroupById(groupId);
        if (group == null) {
            return false;
        }
        return groupDao.removeUserFromGroup(groupId, userId);
    }

}
