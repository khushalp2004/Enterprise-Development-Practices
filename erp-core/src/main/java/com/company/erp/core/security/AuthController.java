package com.company.erp.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // By default assign ROLE_USER, but for this MVP, let's allow assigning admin via a special username
        if(request.getUsername().equalsIgnoreCase("admin")) {
            user.setRoles(Set.of("ROLE_ADMIN"));
        } else {
            user.setRoles(Set.of("ROLE_USER"));
        }
        
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        AppUser user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername(), new ArrayList<>(user.getRoles()));
            return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), user.getRoles()));
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
