package com.alanbrandan.tallermecanico.service.interfaces;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface VehiculoService {
    Vehiculo BuscarVehiculoPorPatente( String patente);
}
