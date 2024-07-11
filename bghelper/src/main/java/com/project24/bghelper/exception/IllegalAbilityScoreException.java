package com.project24.bghelper.exception;

public class IllegalAbilityScoreException extends RuntimeException {
    public IllegalAbilityScoreException(String message) {
        super("Ability Score of range [1..25]: " + message);
    }
}
