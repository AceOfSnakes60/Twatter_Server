package com.group3.twat.twatt.model;

import com.group3.twat.twatt.model.Twatt;

public class TwattWithUserUsernameDTO {
    String username;

    public TwattWithUserUsernameDTO(Twatt twatt){
        this.username = twatt.getUser().getUsername();
    }
    public String getUsername() {
        return username;
    }
}
