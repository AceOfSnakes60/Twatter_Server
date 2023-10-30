package com.group3.twat.group.Repository;

import com.group3.twat.group.model.Group;
import com.group3.twat.twatt.model.TwattPublicDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,Long> {
    public Group getGroupById(long id);
}
