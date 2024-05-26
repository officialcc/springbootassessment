package com.example.demo.exceptions;

public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException(String id) {
        super("Could not find Task: " + id);
    }
}
