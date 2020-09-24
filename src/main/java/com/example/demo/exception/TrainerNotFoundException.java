package com.example.demo.exception;

public class TrainerNotFoundException extends RuntimeException {
    public TrainerNotFoundException(Long id) {
        super("Trainer ID " + id + " Not Found");
    }
}
