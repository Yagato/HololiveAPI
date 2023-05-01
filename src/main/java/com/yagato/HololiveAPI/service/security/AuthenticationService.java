package com.yagato.HololiveAPI.service.security;

import com.yagato.HololiveAPI.model.Authentication;
import com.yagato.HololiveAPI.model.Register;
import com.yagato.HololiveAPI.model.User;
import com.yagato.HololiveAPI.repository.UserRepository;
import com.yagato.HololiveAPI.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderService jwtTokenProviderService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(Register register) {
        User user = User.builder()
                .firstname(register.getFirstname())
                .lastname(register.getLastname())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        String jwtToken = jwtTokenProviderService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(Authentication authentication) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authentication.getEmail(),
                        authentication.getPassword()
                )
        );

        User user = userRepository.findByEmail(authentication.getEmail())
                .orElseThrow();

        String jwtToken = jwtTokenProviderService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
