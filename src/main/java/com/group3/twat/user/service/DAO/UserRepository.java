package com.group3.twat.user.service.DAO;

import com.group3.twat.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
Optional<User> findByEmail(String username);
}