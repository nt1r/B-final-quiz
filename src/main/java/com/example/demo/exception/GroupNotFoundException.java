package com.example.demo.exception;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long id) {
        super("Group ID " + id + " Not Found");
    }
}
