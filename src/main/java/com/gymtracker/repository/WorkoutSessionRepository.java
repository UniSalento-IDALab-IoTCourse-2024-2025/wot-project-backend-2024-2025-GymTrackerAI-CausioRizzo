package com.gymtracker.repository;

import com.gymtracker.entity.User;
import com.gymtracker.entity.WorkoutSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    List<WorkoutSession> findByUser(User user);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM WorkoutSession ws WHERE ws.user = :user AND ws.workoutDate = :workoutDate")
    void deleteByUserAndWorkoutDate(@Param("user") User user, @Param("workoutDate") LocalDate workoutDate);
    Optional<WorkoutSession> findByUserAndExerciseTypeAndWorkoutDate(User user, String exerciseType, LocalDate workoutDate);
}