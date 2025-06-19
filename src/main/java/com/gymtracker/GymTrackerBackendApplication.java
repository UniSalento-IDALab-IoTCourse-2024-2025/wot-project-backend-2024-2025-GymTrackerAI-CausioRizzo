package com.gymtracker;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class GymTrackerBackendApplication {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
    }

    public static void main(String[] args) {
        SpringApplication.run(GymTrackerBackendApplication.class, args);
    }

}
