package com.alanbrandan.tallermecanico.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDto {
    private int estado;
    private String mensaje;
}
