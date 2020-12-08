package com.sovback.sovback.controllers;

import com.sovback.sovback.model.User;
import com.sovback.sovback.payload.request.LoginRequest;
import com.sovback.sovback.payload.request.SignupRequest;
import com.sovback.sovback.payload.response.JwtResponse;
import com.sovback.sovback.payload.response.MessageResponse;
import com.sovback.sovback.repositories.UserRepository;
import com.sovback.sovback.security.jwt.JwtUtils;
import com.sovback.sovback.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody final LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles,
                userDetails.getName(),
                userDetails.getEmail()));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Ошибка: это имя уже занято."));
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setRole(signUpRequest.getRole());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Пользователь успешно зарегистрирован!"));
    }
}

