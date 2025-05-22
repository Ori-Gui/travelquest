package com.ssafy.travelquest.global.handler.exception;

public class DungeonCreationException extends RuntimeException {
    public DungeonCreationException(String message) {
        super(message);
    }
    public DungeonCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}