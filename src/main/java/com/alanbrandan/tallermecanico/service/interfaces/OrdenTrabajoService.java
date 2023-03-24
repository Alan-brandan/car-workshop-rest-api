package com.alanbrandan.tallermecanico.service.interfaces;
import com.alanbrandan.tallermecanico.domain.Empleado;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajo;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajoDetalle;
import com.alanbrandan.tallermecanico.domain.Repuesto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrdenTrabajoService {
    OrdenTrabajo EstadoReparacion(Long id);
    OrdenTrabajoDetalle EstadoFacturar(Long id, int cantidad, Repuesto repuesto);
    OrdenTrabajo EstadoFacturado(Long id);
    OrdenTrabajo EstadoCerrado(Long id,Long adminid,String formapago,String tarjeta,int cuotas);
    OrdenTrabajo RegistrarNuevaOrdenDeTrabajo(String patente, OrdenTrabajo nuevoTrabajo,Long id);

}
