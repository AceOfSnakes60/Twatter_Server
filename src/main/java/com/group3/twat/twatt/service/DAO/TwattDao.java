package com.group3.twat.twatt.service.DAO;

import com.group3.twat.twatt.Twatt;
import com.group3.twat.twatt.service.TwattWithUserUsernameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//TODO Get rid of twatt DAO
public interface TwattDao {
    List<Twatt> getTwatt();
    public Page<Twatt> getTwattByPage(Pageable pageable);

    void addTwatt(Twatt newTwatt);

    boolean deleteTwattById(Long twattId);

    Twatt getTwattById(Long twattId);

    List<Twatt> getResponses(Long twattId);
}
