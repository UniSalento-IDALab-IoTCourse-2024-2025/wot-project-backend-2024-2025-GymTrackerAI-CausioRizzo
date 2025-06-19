package com.gymtracker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "workout_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String exerciseType;

    @Column(nullable = false)
    private int repetitions;

    @Column(nullable = false)
    private Long timeInSeconds;

    @Column(nullable = false)
    private LocalDate workoutDate;

    @Transient
    @JsonProperty("formattedTime")
    public String getFormattedTime() {
        long minutes = timeInSeconds / 60;
        long seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}