package com.example.backend.exception;

public class TokenExpiredException  extends RuntimeException{
    public TokenExpiredException() {}
    public TokenExpiredException(String message) {
        super(message);
    }
}
