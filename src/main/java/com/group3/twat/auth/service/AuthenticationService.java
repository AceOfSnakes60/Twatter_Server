package com.group3.twat.auth.service;

import com.group3.twat.enums.Role;
import com.group3.twat.auth.templates.AuthenticationRequest;
import com.group3.twat.auth.templates.AuthenticationResponse;
import com.group3.twat.auth.templates.RefreshRequest;
import com.group3.twat.auth.templates.RegisterRequest;
import com.group3.twat.user.model.User;
import com.group3.twat.user.repository.UserRepository;
import com.group3.twat.user.service.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            String secretKey = jwtService.generateSecretKey();
            String refreshToken = jwtService.generateRefreshToken(user, secretKey);
            return AuthenticationResponse.builder()
                    .AccessToken(jwtToken)
                    .RefreshToken(refreshToken)
                    .build();
        }
        return AuthenticationResponse.builder()
                .error(validationMessage != null ? validationMessage :
                        validationMessage2 != null ? validationMessage2 : validationMessage3)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var user = repository.findByEmail(request.getEmail());
        if(user.isEmpty()) {
                user = repository.findByUsername(request.getEmail());
                if(user.isEmpty()){
                    return AuthenticationResponse.builder()
                            .error("User not found")
                            .build();
                }
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.get().getEmail(),
                        request.getPassword()
                )
        );
        String jwtToken = jwtService.generateToken(user.get());
        String secretKey = jwtService.generateSecretKey();
        String refreshToken = jwtService.generateRefreshToken(user.get(), secretKey);
        System.out.println(refreshToken);
        System.out.println(jwtToken);
        return AuthenticationResponse.builder()
                .AccessToken(jwtToken)
                .RefreshToken(refreshToken)
                .build();
    }
    public AuthenticationResponse refresh(RefreshRequest refreshRequest){
        String refreshToken = refreshRequest.refreshToken();
        String username = jwtService.extractUsername(refreshToken);

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            return null;
        }
        System.out.println("Refresh");
        String jwtToken = jwtService.generateToken(user.get());
        return AuthenticationResponse.builder()
                .AccessToken(jwtToken)
                .RefreshToken(refreshToken)
                .build();
    }

}
