package com.alanbrandan.tallermecanico.controller;
import com.alanbrandan.tallermecanico.domain.ManoObra;
import com.alanbrandan.tallermecanico.service.implementations.ManoObraServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manodeobra")
@AllArgsConstructor
public class ManoObraController {
    private final ManoObraServiceImpl service;

    @PostMapping("/")
    public ManoObra RegistrarNuevaManodeObra(@RequestParam(name = "mecanico_id") Long idmecan,
                                             @RequestParam(name = "trabajo_id") Long idorden,
                                             @RequestBody ManoObra manodeobra){
        return service.RegistrarNuevaManodeObra(idmecan,idorden,manodeobra);
    }
    @PutMapping("/{id}")
    public ManoObra CompletarManodeObra(@PathVariable Long id,@RequestBody ManoObra manodeobra){
        return service.CompletarManodeObra(id,manodeobra);
    }
}
