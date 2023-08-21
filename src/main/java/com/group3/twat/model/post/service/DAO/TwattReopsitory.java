package com.group3.twat.model.post.service.DAO;

import com.group3.twat.model.post.Twatt;
import com.group3.twat.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;



    public interface TwattReopsitory extends JpaRepository<Twatt,Long> {
    }

