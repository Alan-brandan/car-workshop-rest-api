package com.alanbrandan.tallermecanico.service.interfaces;
import com.alanbrandan.tallermecanico.domain.Cliente;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

public interface ClienteService {
    Cliente BuscarClientePorEmail(String email);
    Cliente VincularVehiculoNuevo(Vehiculo nuevoVehiculo, String email);
    Cliente RegistrarNuevoCliente(Cliente nuevoCliente);

}
