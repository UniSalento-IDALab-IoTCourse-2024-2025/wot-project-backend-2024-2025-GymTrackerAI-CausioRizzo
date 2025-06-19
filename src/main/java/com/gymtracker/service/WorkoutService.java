package com.gymtracker.service;

import com.gymtracker.dto.WorkoutSessionRequest;
import com.gymtracker.entity.User;
import com.gymtracker.entity.WorkoutSession;
import com.gymtracker.repository.WorkoutSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutSessionRepository workoutSessionRepository;

    public WorkoutSession saveSession(User user, WorkoutSessionRequest request) {
        String exercise = request.getExerciseType();
        LocalDate date = LocalDate.parse(request.getWorkoutDate());

        Optional<WorkoutSession> existingSessionOpt =
                workoutSessionRepository.findByUserAndExerciseTypeAndWorkoutDate(user, exercise, date);

        WorkoutSession session;
        if (existingSessionOpt.isPresent()) {
            session = existingSessionOpt.get();
            session.setRepetitions(session.getRepetitions() + request.getRepetitions());
            session.setTimeInSeconds(session.getTimeInSeconds() + request.getTimeInSeconds());
        } else {
            session = new WorkoutSession();
            session.setUser(user);
            session.setExerciseType(exercise);
            session.setRepetitions(request.getRepetitions());
            session.setTimeInSeconds(request.getTimeInSeconds());
            session.setWorkoutDate(date);
        }

        return workoutSessionRepository.save(session);
    }

    public List<WorkoutSession> getUserSessions(User user) {
        return workoutSessionRepository.findByUser(user);
    }

    public void deleteSessionByDate(User user, LocalDate date) {
        workoutSessionRepository.deleteByUserAndWorkoutDate(user, date);
    }
}
