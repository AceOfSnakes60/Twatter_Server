package com.group3.twat.model.post.service;

import com.group3.twat.model.post.service.DAO.TwattDao;
import com.group3.twat.model.post.Twatt;
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
        System.out.println(twattList.get(0));
        return twattList;
    }


    public void addTwatt(Twatt newTwat) {
        twattDao.addTwatt(newTwat);
    }

    public boolean deleteTwattById(Long twattId) {
        {
            return twattDao.deleteTwattById(twattId);
        }
    }
}