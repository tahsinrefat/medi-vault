package com.tahsin.medi_vault.config;

import com.tahsin.medi_vault.dto.authentication.AuthenticationRequest;
import com.tahsin.medi_vault.dto.authentication.AuthenticationResponse;
import com.tahsin.medi_vault.dto.register.RegisterRequest;
import com.tahsin.medi_vault.dto.register.RegisterResponse;
import com.tahsin.medi_vault.entity.User;
import com.tahsin.medi_vault.enums.Role;
import com.tahsin.medi_vault.enums.Status;
import com.tahsin.medi_vault.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        com.tahsin.medi_vault.entity.User user = com.tahsin.medi_vault.entity.User
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return new RegisterResponse(Status.SUCCESSFUL, "Successfully registered user information");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new UsernameNotFoundException(STR."Username not found with email: \{request.email()}")
        );

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(Status.SUCCESSFUL, jwtToken);
    }
}
