package com.group3.twat.twatt.service.DAO;

import com.group3.twat.twatt.Twatt;

import java.util.List;

public interface TwattDao {
    List<Twatt> getTwatt();

    void addTwatt(Twatt newTwatt);

    boolean deleteTwattById(Long twattId);

    Twatt getTwattById(Long twattId);

    List<Twatt> getResponses(Long twattId);
}