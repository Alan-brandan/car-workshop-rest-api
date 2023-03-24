package com.alanbrandan.tallermecanico.controller;
import com.alanbrandan.tallermecanico.domain.Cliente;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import com.alanbrandan.tallermecanico.service.implementations.ClienteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

    private final ClienteServiceImpl service;

    @GetMapping("/{email}")
    public Cliente BuscarClientePorEmail(@PathVariable String email){
        return service.BuscarClientePorEmail(email);
    }
    @PostMapping("/{email}/vehiculonuevo")
    public Cliente VincularVehiculoNuevo(@RequestBody Vehiculo nuevoVehiculo,@PathVariable String email){
        return service.VincularVehiculoNuevo(nuevoVehiculo,email);
    }
    @PostMapping("/")
    public Cliente RegistrarNuevoCliente(@RequestBody Cliente nuevoCliente){
        return service.RegistrarNuevoCliente(nuevoCliente);
    }

}
