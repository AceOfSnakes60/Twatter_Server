package com.group3.twat.twatt.service;

import com.group3.twat.twatt.service.DAO.TwattDao;
import com.group3.twat.twatt.Twatt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TwattService {


    private final TwattDao twattDao;


    @Autowired
    public TwattService(TwattDao twattDao) {
        this.twattDao = twattDao;
    }

    public List<Twatt> getAllTwats(boolean isNewer){
        List<Twatt> twattList = new ArrayList<>(twattDao.getTwatt());
        Collections.sort(twattList, Comparator.comparing(Twatt::getDate));
        if(isNewer) {
            Collections.reverse(twattList);

        }
        return twattList;
    }

    public List<Twatt> getAllTwatts(boolean isNewer, int page, int postPerPage){
        List<Twatt> twattList = new ArrayList<>(twattDao.getTwatt());
        Collections.sort(twattList, Comparator.comparing(Twatt::getDate));
        if(isNewer) {
            Collections.reverse(twattList);
        }
        return twattList;
    }


    public void addTwatt(Twatt newTwat) {
        twattDao.addTwatt(newTwat);
    }

    public boolean deleteTwattById(Long twattId) {
        return twattDao.deleteTwattById(twattId);
    }

    public Twatt getTwattById(Long twattId){
        return twattDao.getTwattById(twattId);
    }
    public List<Twatt> getReplies(Long parentId){
        return twattDao.getResponses(parentId);
    }
}