package com.gang.growup.service;

import com.gang.growup.dto.LoginRequest;
import com.gang.growup.dto.RegisterRequest;
import com.gang.growup.entity.User;
import com.gang.growup.repository.UserRepository;
import com.gang.growup.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UserRepository repo, JwtUtil jwtUtil, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    public Map<String,Object> register(RegisterRequest request) {

        if(repo.existsByUsername(request.username))
            throw new RuntimeException("Username already exists");

        User user = new User(
                request.username,
                request.email,
                request.fullName,
                encoder.encode(request.password)
        );

        repo.save(user);

        String token = jwtUtil.generateToken(user.getUsername());

        return buildResponse(user, token);
    }

    public Map<String,Object> login(LoginRequest request) {

        User user = repo.findByUsername(request.username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!encoder.matches(request.password, user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        String token = jwtUtil.generateToken(user.getUsername());

        return buildResponse(user, token);
    }

    private Map<String,Object> buildResponse(User user, String token) {

        Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("fullName", user.getFullName());

        String initials = user.getFullName()
                .chars()
                .mapToObj(c -> (char)c)
                .filter(Character::isUpperCase)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        map.put("initials", initials.isEmpty() ? "U" : initials);

        return map;
    }
}