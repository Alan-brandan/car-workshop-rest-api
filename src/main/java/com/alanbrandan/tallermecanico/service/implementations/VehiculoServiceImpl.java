package com.alanbrandan.tallermecanico.service.implementations;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.repository.VehiculoRepository;
import com.alanbrandan.tallermecanico.service.interfaces.VehiculoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository repository;

    @Override
    public Vehiculo BuscarVehiculoPorPatente(String patente) {
        Vehiculo result = repository.findByPatente(patente);
        if(result==null){
            throw new NotFoundException("No se encontro un Vehiculo con la patente '" + patente + "'");
        }
        return result;
    }

}
