package com.gymtracker.controller;

import com.gymtracker.dto.WorkoutSessionRequest;
import com.gymtracker.entity.User;
import com.gymtracker.entity.WorkoutSession;
import com.gymtracker.security.JwtUtils;
import com.gymtracker.service.AuthService;
import com.gymtracker.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/save")
    public ResponseEntity<?> saveSession(@RequestHeader("Authorization") String token,
                                         @RequestBody WorkoutSessionRequest request) {
        String email = jwtUtils.getEmailFromJwtToken(token.substring(7));
        User user = authService.getUserByEmail(email);
        WorkoutSession session = workoutService.saveSession(user, request);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getSessions(@RequestHeader("Authorization") String token) {
        String email = jwtUtils.getEmailFromJwtToken(token.substring(7));
        User user = authService.getUserByEmail(email);
        List<WorkoutSession> sessions = workoutService.getUserSessions(user);
        return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/deleteByDate")
    public ResponseEntity<?> deleteSessionsByDate(@RequestHeader("Authorization") String token,
                                                  @RequestParam("date") String dateStr) {
        String email = jwtUtils.getEmailFromJwtToken(token.substring(7));
        User user = authService.getUserByEmail(email);
        LocalDate date = LocalDate.parse(dateStr);
        workoutService.deleteSessionByDate(user, date);
        return ResponseEntity.ok("Sessions deleted");
    }
}
