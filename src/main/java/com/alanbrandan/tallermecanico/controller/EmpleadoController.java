package com.alanbrandan.tallermecanico.controller;
import com.alanbrandan.tallermecanico.domain.Empleado;
import com.alanbrandan.tallermecanico.service.interfaces.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleado")
@AllArgsConstructor
public class EmpleadoController {
    private final EmpleadoService service;
    @PostMapping("/")
    public Empleado RegistrarNuevoEmpleado(@RequestBody Empleado empleado){
        return service.RegistrarNuevoEmpleado(empleado);
    }
}
