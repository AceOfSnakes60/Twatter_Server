package com.group3.twat.auth;

import com.group3.twat.auth.templates.AuthenticationRequest;
import com.group3.twat.auth.templates.AuthenticationResponse;
import com.group3.twat.auth.templates.RegisterRequest;
import com.group3.twat.user.User;
import com.group3.twat.user.service.DAO.UserRepository;
import com.group3.twat.user.service.Validations;
import lombok.RequiredArgsConstructor;
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
    public AuthenticationResponse register(RegisterRequest request) {
        System.out.println(request.getUsername());
        String validationMessage = validations.validateUsername(request.getUsername(), userRepository);
        String validationMessage2 = validations.validateUserPassword(request.getPassword());
        String validationMessage3 = validations.validateEmail(request.getEmail(),userRepository);
        if (validationMessage == null && validationMessage2 == null&& validationMessage3==null) {
            var user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        return AuthenticationResponse.builder()
                .error(validationMessage != null ? validationMessage :
                        validationMessage2 != null ? validationMessage2 : validationMessage3)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElse(repository.findByUsername(request.getEmail()).orElseThrow());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
