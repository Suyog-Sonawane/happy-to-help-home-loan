package com.happytohelp.homeloan.exception;


public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) { super(message); }
}