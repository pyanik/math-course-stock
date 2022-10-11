package com.pyanik.mathcoursestock.exception;

public class EntityNotSavedException extends RuntimeException {

    public EntityNotSavedException(String message) {
        super(message);
    }
}