package com.group3.twat.model.post.service.DAO;

import com.group3.twat.model.post.Twatt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TwattRepository extends JpaRepository<Twatt,Long> {

    @Query("select t from Twatt t where t.parentId = :id")
    List<Twatt> findByParentId(@Param("id") Long id);
}

