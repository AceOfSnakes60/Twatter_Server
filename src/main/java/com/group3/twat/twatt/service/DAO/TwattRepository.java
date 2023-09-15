package com.group3.twat.twatt.service.DAO;

import com.group3.twat.twatt.Twatt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;


public interface TwattRepository extends JpaRepository<Twatt,Long> {

    @Query("select t from Twatt t where t.parentId = :id")
    List<Twatt> findByParentId(@Param("id") Long id);

}

