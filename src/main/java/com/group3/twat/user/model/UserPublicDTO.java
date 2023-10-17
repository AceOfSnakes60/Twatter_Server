package com.group3.twat.user.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserPublicDTO {
    private String username;
    private String fullname;
    private String description;
    private int followers_count;
    private int following_count;
}
