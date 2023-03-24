package com.alanbrandan.tallermecanico.controller;
import com.alanbrandan.tallermecanico.domain.*;
import com.alanbrandan.tallermecanico.service.implementations.OrdenTrabajoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trabajo")
@AllArgsConstructor
public class OrdenTrabajoController {

    private final OrdenTrabajoServiceImpl service;

    @PutMapping("/{id}/enreparacion")
    public OrdenTrabajo EstadoReparacion(@PathVariable Long id){
        return service.EstadoReparacion(id);
    }
    @PutMapping("/{id}/parafacturar/{cantidad}")
    public OrdenTrabajoDetalle EstadoFacturar(@PathVariable Long id,
                                              @PathVariable int cantidad,
                                              @RequestBody Repuesto repuesto){
        return service.EstadoFacturar(id,cantidad,repuesto);
    }
    @PutMapping("/{id}/facturado")
    public OrdenTrabajo EstadoFacturado(@PathVariable Long id){
        return service.EstadoFacturado(id);
    }
    @PutMapping("/{id}/cerrar/")
    public OrdenTrabajo EstadoCerrado(@PathVariable Long id,
                                      @RequestParam(name = "admin_id")Long adminid,
                                      @RequestParam(name = "forma_pago")String formapago,
                                      @RequestParam(name = "tarjeta")String tarjeta,
                                      @RequestParam(name = "cuotas")int cuotas){
        return service.EstadoCerrado(id,adminid,formapago,tarjeta,cuotas);
    }
    @PostMapping("/{patente}/{id}")
    public OrdenTrabajo RegistrarNuevaOrdenDeTrabajo(@PathVariable String patente,
                                                     @RequestBody OrdenTrabajo nuevoTrabajo,
                                                     @PathVariable Long id){
        return service.RegistrarNuevaOrdenDeTrabajo(patente,nuevoTrabajo,id);
    }

}
