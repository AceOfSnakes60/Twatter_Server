package com.group3.twat.group.service.DAO;

import com.group3.twat.group.Group;

import java.util.List;

public interface GroupDao {
    List<Group> getGroup();

    void addGroup(Group newGroup);
    Group getGroupById(Long groupId);

    boolean deleteGroupById(Long groupId);
    void addUserToGroup(Long groupId, Long userId);
   boolean  removeUserFromGroup(Long groupId,Long userId );
}
