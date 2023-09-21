package com.group3.twat.twatt.service.DAO;

import com.group3.twat.twatt.Twatt;
import com.group3.twat.twatt.service.TwattWithUserUsernameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TwattRepository extends JpaRepository<Twatt,Long> {

    @Query("select t from Twatt t where t.parentId = :id")
    List<Twatt> findByParentId(@Param("id") Long id);

    Page<Twatt> findAll(Pageable pageable);

    //@Query("SELECT NEW com.group3.twat.twatt.service.TwattWithUserUsernameDTO(b) FROM Twatt b")
    //Page<TwattWithUserUsernameDTO> findAllWithUserUsername(Pageable pageable);



}

