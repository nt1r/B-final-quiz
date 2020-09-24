package com.example.demo.exception;

public class GroupNameRepeatedException extends RuntimeException {
    public GroupNameRepeatedException() {
        super("Group Name Repeated");
    }
}
