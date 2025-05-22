package com.ssafy.travelquest.global.handler.exception;

public class QuestGenerationException extends RuntimeException {
    public QuestGenerationException(String message) {
        super(message);
    }
    public QuestGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}