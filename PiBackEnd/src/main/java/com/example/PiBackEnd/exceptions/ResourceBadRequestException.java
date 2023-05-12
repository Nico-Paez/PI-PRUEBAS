package com.example.PiBackEnd.exceptions;

public class ResourceBadRequestException extends Exception{
    public ResourceBadRequestException(String message) {
        super(message);
    }
}
