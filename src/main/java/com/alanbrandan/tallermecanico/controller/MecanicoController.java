package com.alanbrandan.tallermecanico.controller;

import com.alanbrandan.tallermecanico.domain.Mecanico;
import com.alanbrandan.tallermecanico.service.interfaces.MecanicoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mecanico")
@AllArgsConstructor
public class MecanicoController {
    private final MecanicoService service;

    @PostMapping("/")
    public Mecanico RegistrarNuevoMecanico(@RequestBody Mecanico mecanico){
        return service.RegistrarNuevoMecanico(mecanico);
    }
}
