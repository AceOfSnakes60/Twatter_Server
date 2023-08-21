package com.group3.twat.model.post.service.DAO;

import com.group3.twat.model.post.Twatt;
import com.group3.twat.model.user.service.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
@Repository
public class TwattMemory implements TwattDao {
    private final TwattReopsitory twattReopsitory;

    @Autowired
    public TwattMemory(TwattReopsitory twattReopsitory) {
this.twattReopsitory=twattReopsitory;
    }

    @Override
    public List<Twatt> getTwatt() {
        return twattReopsitory.findAll();
    }


    @Override
    public void addTwatt(Twatt newTwatt) {
        twattReopsitory.save(newTwatt);
    }
    @Override
    public boolean deleteTwattById(Long twattId) {
        if (twattReopsitory.existsById(twattId)) {
            twattReopsitory.deleteById(twattId);
            return true;
        }
        return false;
    }

}
