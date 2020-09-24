package com.example.demo.exception;

public class TraineeNotFoundException extends RuntimeException {
    public TraineeNotFoundException(Long id) {
        super("Trainee ID " + id + " Not Found");
    }
}
