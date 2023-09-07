package com.group3.twat.model.user.service.DAO.config.Seciurity;

import com.group3.twat.model.user.Role;
import com.group3.twat.model.user.User;
import com.group3.twat.model.user.service.DAO.UserRepository;
import com.group3.twat.model.user.service.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
private final Validations validations;
private final UserRepository userRepository;
    public AuthenticationResponse register(RegisterRequset requset) {
        String validationMessage = validations.validateUsername(requset.getUsername());
        String validationMessage2 = validations.validateUserPassword(requset.getPassword());
        String validationMessage3 = validations.validateEmail(requset.getEmail(),userRepository);
        if (validationMessage == null && validationMessage2 == null&& validationMessage3==null) {
            var user = User.builder()
                    .username(requset.getUsername())
                    .email(requset.getEmail())
                    .password(passwordEncoder.encode(requset.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);
            var jwtToken = jwtService.genrateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            return AuthenticationResponse.builder()
                    .error(validationMessage != null ? validationMessage :
                            validationMessage2 != null ? validationMessage2 : validationMessage3)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequset requset) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requset.getEmail(),
                        requset.getPassword()
                )
        );
        var user = repository.findByEmail(requset.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.genrateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
