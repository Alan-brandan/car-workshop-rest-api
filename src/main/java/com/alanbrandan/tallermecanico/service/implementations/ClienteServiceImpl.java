package com.alanbrandan.tallermecanico.service.implementations;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import com.alanbrandan.tallermecanico.exception.AlreadyExistsException;
import com.alanbrandan.tallermecanico.exception.InputInvalidoException;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.repository.VehiculoRepository;
import com.alanbrandan.tallermecanico.service.interfaces.ClienteService;
import com.alanbrandan.tallermecanico.utilities.EmailValidator;
import com.alanbrandan.tallermecanico.domain.Cliente;
import com.alanbrandan.tallermecanico.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

private final ClienteRepository repository;
private final VehiculoRepository repositoryVehiculos;

    @Override
    public Cliente BuscarClientePorEmail(String email) {
        if(!EmailValidator.CorreoValido(email)){
            throw new InputInvalidoException("Ingresaste un correo electronico no valido");
        }
        Cliente found = repository.findByCorreoElectronico(email);
        if(found==null){
            throw new NotFoundException("No se encontro Clientes registrador con el correo electronico '"+email+"'");
        }
        return found;
    }

    @Override
    public Cliente VincularVehiculoNuevo(Vehiculo nuevoVehiculo, String email ) {

        if(!EmailValidator.CorreoValido(email)){
            throw new InputInvalidoException("correo electronico ingresado no valido");
        }
        Cliente clientefound = repository.findByCorreoElectronico(email);
        if(clientefound==null){
            throw new NotFoundException("No se encontro un cliente registrado con el correo electronico '"+email+"'");
        }
        Vehiculo vehiculofound = repositoryVehiculos.findByPatente(nuevoVehiculo.getPatente());
        if(vehiculofound!=null){
            throw new AlreadyExistsException("ya existe un vehiculo con la patente '"+nuevoVehiculo.getPatente()+"'");
        }

        List<Cliente> nuevalistac = new ArrayList<>();
        nuevalistac.add(clientefound);
        nuevoVehiculo.setClientes(nuevalistac);
        repositoryVehiculos.save(nuevoVehiculo);

        List<Vehiculo> nuevalistav = new ArrayList<>();
        if(clientefound.getVehiculos()!=null){
            nuevalistav.addAll(clientefound.getVehiculos());
        }
        nuevalistav.add(nuevoVehiculo);
        clientefound.setVehiculos(nuevalistav);
        repository.save(clientefound);
        return clientefound;
    }

    @Override
    public Cliente RegistrarNuevoCliente(Cliente nuevoCliente) {
        if(!EmailValidator.CorreoValido(nuevoCliente.getCorreoElectronico())){
            throw new InputInvalidoException("Ingresaste un correo electronico no valido");
        }
        Cliente found = repository.findByCorreoElectronico(nuevoCliente.getCorreoElectronico());
        if(found!=null){
            throw new AlreadyExistsException("Ya hay un usuario registrado con el correo electronico '"+nuevoCliente.getCorreoElectronico()+"'");
        }
        if(nuevoCliente.getVehiculos()!=null){
            String LastAddedPatente = nuevoCliente.getVehiculos().get(nuevoCliente.getVehiculos().size()-1).getPatente();
            Vehiculo vehiculonuevoExistente = repositoryVehiculos.findByPatente(LastAddedPatente);
            if(vehiculonuevoExistente!=null){
                throw new AlreadyExistsException("Ya existe un vehiculo registrado con la patente '"+ LastAddedPatente+"'");
            }
            Vehiculo vehiculonuevo = nuevoCliente.getVehiculos().get(nuevoCliente.getVehiculos().size()-1);

            //vincula el nuevo vehiculo al cliente
            List<Cliente> nuevalistac = new ArrayList<>();
            if(vehiculonuevo.getClientes()!=null){
                nuevalistac.addAll(vehiculonuevo.getClientes());
            }
            nuevalistac.add(nuevoCliente);
            vehiculonuevo.setClientes(nuevalistac);
            repositoryVehiculos.save(vehiculonuevo);

            List<Vehiculo> nuevalistav = new ArrayList<>();
            if(vehiculonuevo.getClientes()!=null){
                nuevalistav.addAll(nuevoCliente.getVehiculos());
            }
            nuevalistav.add(vehiculonuevo);
            nuevoCliente.setVehiculos(nuevalistav);
        }
        return repository.save(nuevoCliente);
    }
}
