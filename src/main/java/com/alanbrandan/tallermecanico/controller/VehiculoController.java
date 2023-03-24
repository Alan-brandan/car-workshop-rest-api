package com.alanbrandan.tallermecanico.controller;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import com.alanbrandan.tallermecanico.service.implementations.VehiculoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculo")
@AllArgsConstructor
public class VehiculoController {

    private final VehiculoServiceImpl service;

    @GetMapping("/{patente}")
    public Vehiculo BuscarVehiculoPorPatente(@PathVariable String patente){
        return service.BuscarVehiculoPorPatente(patente);
    }
}
