package com.group3.twat.model.user.service.DAO.config.Seciurity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequset {
    private String username;
    private String email;
    private String password;
}
