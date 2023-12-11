package com.example.androidphotos59.utils;

/**
 * Provides a modularized way to store application error codes.
 * 
 * @author Rohan Deshpande
 * @version 1.0
 */
public enum ErrorCode 
{
    AUTHERROR("Authentication Error"),
    APPERROR("Application Error"),
    ADMINERROR("Admin Subsystem Error");
    

    /**
     * The associated error code (string) to the enum key.
     */
    private String errorCode;

    /**
     * 
     * @param errorCode Takes the error code string and makes it a enum key.
     */
    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * This method will return the associated error code to the enum key.
     * 
     * @return The error code message
     */
    public String getErrorCode() {
        return errorCode;
    }
}