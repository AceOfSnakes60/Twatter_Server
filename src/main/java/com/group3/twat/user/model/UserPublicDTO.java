package com.group3.twat.user.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserPublicDTO {
    private String username;
    private String description;
    private int followers_count;
    private int following_count;

    public UserPublicDTO(User user){
        username = user.getUsername();
        description = user.getDescription();
    }
}
