package com.alanbrandan.tallermecanico.exception;

public class InputInvalidoException extends  RuntimeException{
    public InputInvalidoException(String message){super(message);}
    public InputInvalidoException(String message, Throwable cause){super(message,cause);}

}
