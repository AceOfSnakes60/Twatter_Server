package com.group3.twat.model.post.service.DAO;

import com.group3.twat.model.post.Twatt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TwattMemory implements TwattDao {
    private final TwattRepository twattRepository;

    @Autowired
    public TwattMemory(TwattRepository twattRepository) {
this.twattRepository= twattRepository;
    }

    @Override
    public List<Twatt> getTwatt() {
        return twattRepository.findAll();
    }


    @Override
    public void addTwatt(Twatt newTwatt) {
        twattRepository.save(newTwatt);
    }
    @Override
    public boolean deleteTwattById(Long twattId) {
        if (twattRepository.existsById(twattId)) {
            twattRepository.deleteById(twattId);
            return true;
        }
        return false;
    }


    @Override
    public Twatt getTwattById(Long twattId){
        return twattRepository.findById(twattId).get();
    }
    @Override
    public List<Twatt> getResponses(Long twattId){
        return twattRepository.findByParentId(twattId);
    }

}
