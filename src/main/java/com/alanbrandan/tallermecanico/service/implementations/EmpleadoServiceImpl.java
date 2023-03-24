package com.alanbrandan.tallermecanico.service.implementations;
import com.alanbrandan.tallermecanico.domain.Empleado;
import com.alanbrandan.tallermecanico.exception.InputInvalidoException;
import com.alanbrandan.tallermecanico.repository.EmpleadoRepository;
import com.alanbrandan.tallermecanico.service.interfaces.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoRepository repository;

    @Override
    public Empleado RegistrarNuevoEmpleado(Empleado empleado) {
        if(!empleado.getTipo_empleado().equalsIgnoreCase("administrativo")){
            if(!empleado.getTipo_empleado().equalsIgnoreCase("recepcionista")){
                throw new InputInvalidoException("tipo de empleado '"+empleado.getTipo_empleado().toLowerCase()+ "' invalido");
            }
        }
        return repository.save(empleado);
    }
}
