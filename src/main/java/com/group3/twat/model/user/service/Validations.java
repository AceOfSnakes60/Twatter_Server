package com.group3.twat.model.user.service;

import com.group3.twat.model.user.User;
import com.group3.twat.model.user.service.DAO.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Validations {


    public String validateUsername(String username) {
        if (username.length() < 5 || username.length() > 20) {
            return "Username length must be between 5 and 20 characters";
        }

        if (!username.matches("^[a-zA-Z0-9_-]*$")) {
            return "Username can only contain letters, numbers, hyphens, and underscores";
        }

        return null;
    }

    public String validateUserPassword(String password) {
        if (password.length() < 6 || password.length() > 20) {
            return "Password length must be between 6 and 20 characters";
        }

        if (!password.matches("^[a-zA-Z0-9_-]*$")) {
            return "Password can only contain letters, numbers, hyphens, and underscores";
        }

        return null;
    }


    public String validateEmail(String email, UserRepository userRepository) {
        if (email.length() < 5 || email.length() > 50) {
            return "Email length must be between 5 and 50 characters";
        }

        if (!isValidEmailFormat(email)) {
            return "Invalid email format";
        }
        if (emailExistsInDatabase(email, userRepository)) {
            return "Email already exists";
        }

        return null;
    }


    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean emailExistsInDatabase(String email, UserRepository userRepository) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }
}