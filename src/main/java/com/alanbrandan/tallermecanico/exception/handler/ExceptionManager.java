package com.alanbrandan.tallermecanico.exception.handler;

import com.alanbrandan.tallermecanico.exception.AlreadyExistsException;
import com.alanbrandan.tallermecanico.exception.InputInvalidoException;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.exception.StateRequiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionManager {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto InputInvalido (InputInvalidoException ex){
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto AlreadyExist (AlreadyExistsException ex){
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto DoesntExist (NotFoundException ex){
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto StateRequired (StateRequiredException ex){
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
