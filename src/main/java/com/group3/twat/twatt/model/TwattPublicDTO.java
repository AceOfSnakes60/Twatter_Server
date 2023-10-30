package com.group3.twat.twatt.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.group3.twat.user.model.User;
import com.group3.twat.user.model.UserPublicDTO;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@JsonSerialize
@AllArgsConstructor
public class TwattPublicDTO {
    private String text;
    private LocalDate createdAt;
    private UserPublicDTO user;

    public TwattPublicDTO(Twatt twatt){
        text = twatt.getText();
        createdAt = twatt.getDate();
        this.user = new UserPublicDTO(twatt.getUser());
    }

}
