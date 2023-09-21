package com.group3.twat.twatt.service;

import com.group3.twat.twatt.Twatt;

public class TwattWithUserUsernameDTO {
    String username;

    public TwattWithUserUsernameDTO(Twatt twatt){
        this.username = twatt.getUser().getUsername();
    }
    public String getUsername() {
        return username;
    }
}
