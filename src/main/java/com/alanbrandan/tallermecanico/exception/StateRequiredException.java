package com.alanbrandan.tallermecanico.exception;

public class StateRequiredException extends RuntimeException{
    public StateRequiredException(String message){super(message);}
    public StateRequiredException(String message,Throwable cause){super(message,cause);}
}
