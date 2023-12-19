package com.group3.twat.twatt.service;

import com.group3.twat.twatt.model.Twatt;
import com.group3.twat.twatt.model.TwattPublicDTO;
import com.group3.twat.twatt.repository.TwattRepository;
import com.group3.twat.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
public class TwattService {



    private final TwattRepository twattRepository;


    @Autowired
    public TwattService(TwattRepository twattRepository) {
        this.twattRepository = twattRepository;
    }

    public List<Twatt> getAllTwats(boolean isNewer){
        List<Twatt> twattList = new ArrayList<>(twattRepository.findAll());
        Collections.sort(twattList, Comparator.comparing(Twatt::getDate));
        System.out.println("Show first user: " + twattList.get(0).getUser().getUsername());
        if(isNewer) {
            Collections.reverse(twattList);

        }
        return twattList;
    }

    public List<Twatt> getAllTwatts(boolean isNewer, int page, int postPerPage){
        List<Twatt> twattList = new ArrayList<>(twattRepository.findAll());
        Collections.sort(twattList, Comparator.comparing(Twatt::getDate));
        if(isNewer) {
            Collections.reverse(twattList);
        }
        return twattList;
    }

    public Page<Twatt> getTwattByPage(Pageable page){
        return twattRepository.findAllWithUser(page);
    }

    public List<TwattPublicDTO> getByUsername(String username, int page){

        return twattRepository.getTwattByUser(username);
    }
    public void addTwatt(Twatt newTwatt) {
        twattRepository.save(newTwatt);
    }

    public boolean deleteTwattById(Long twattId) {
        if (twattRepository.existsById(twattId)) {
            twattRepository.deleteById(twattId);
            return true;
        }
        return false;
    }

    public Twatt getTwattById(Long twattId){
        return twattRepository.findById(twattId).get();
    }
    public List<TwattPublicDTO> getReplies(Long parentId){
        return twattRepository.findByParentId(parentId);
    }

    public void addLike(Long twattId, User user){
        Optional<Twatt> found = twattRepository.findById(twattId);
        if(found.isEmpty()){
            return;
        }
        found.get().addLike(user);

    }
}