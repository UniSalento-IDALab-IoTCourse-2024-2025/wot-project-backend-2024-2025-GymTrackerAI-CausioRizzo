package com.gymtracker.service;

import com.gymtracker.dto.LoginRequest;
import com.gymtracker.dto.RegisterRequest;
import com.gymtracker.dto.ResetPasswordRequest;
import com.gymtracker.entity.User;
import com.gymtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        // Verifica che tutti i campi siano pieni (validazione semplice lato backend)
        if (request.getFirstName().isBlank() || request.getLastName().isBlank() ||
                request.getEmail().isBlank() || request.getPassword().isBlank() || request.getConfirmPassword().isBlank()) {
            throw new IllegalArgumentException("All fields are required");
        }

        // Verifica email già registrata
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Verifica password e conferma
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setTimeLogin(LocalDateTime.now(ZoneId.of("Europe/Rome")));
        user.setTimeLogout(null);
        return userRepository.save(user);
    }

    public User authenticate(LoginRequest request) {
        if (request.getEmail().isBlank() || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Email and password are required");
        }

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        // Aggiorna timeLogin al login
        user.setTimeLogin(ZonedDateTime.now(ZoneId.of("Europe/Rome")).toLocalDateTime());
        userRepository.save(user);

        return user;
    }

    public void logout(User user) {
        user.setTimeLogout(ZonedDateTime.now(ZoneId.of("Europe/Rome")).toLocalDateTime());
        userRepository.save(user);
    }

    public void resetPassword(ResetPasswordRequest request) {
        if (request.getEmail().isBlank() || request.getNewPassword().isBlank() || request.getConfirmPassword().isBlank()) {
            throw new IllegalArgumentException("All fields are required");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
