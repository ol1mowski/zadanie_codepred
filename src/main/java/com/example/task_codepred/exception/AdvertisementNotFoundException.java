package com.example.task_codepred.exception;

public class AdvertisementNotFoundException extends RuntimeException {
    
    public AdvertisementNotFoundException(String message) {
        super(message);
    }
    
    public AdvertisementNotFoundException(Long id) {
        super("Advertisement with id " + id + " not found");
    }
}
