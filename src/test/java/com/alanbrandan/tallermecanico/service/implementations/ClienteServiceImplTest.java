package com.alanbrandan.tallermecanico.service.implementations;

import com.alanbrandan.tallermecanico.data.DummyData;
import com.alanbrandan.tallermecanico.domain.Cliente;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import com.alanbrandan.tallermecanico.exception.AlreadyExistsException;
import com.alanbrandan.tallermecanico.exception.InputInvalidoException;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.repository.ClienteRepository;
import com.alanbrandan.tallermecanico.repository.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteServiceImplTest {

    @Autowired
    private ClienteServiceImpl service;
    @MockBean
    private ClienteRepository repository;
    @MockBean
    private VehiculoRepository repositoryVehiculo;

    @Test
    void buscarClientePorEmail() {
        Long idtocompare = 1L;
        when(repository.findByCorreoElectronico(anyString())).thenReturn(DummyData.listaClientes.get(0));
        Cliente result = service.BuscarClientePorEmail("jDoe@domain.com");
        assertEquals(idtocompare,result.getId());
        verify(repository,times(1)).findByCorreoElectronico("jDoe@domain.com");
    }
    @Test
    void buscarClientePorEmail_invalidEmail() {
        Throwable exc = assertThrows(InputInvalidoException.class,()->
                service.BuscarClientePorEmail("jDoe")
        );
        assertEquals("Ingresaste un correo electronico no valido", exc.getMessage());
    }
    @Test
    void buscarClientePorEmail_noClientsFound() {
        Throwable exc = assertThrows(NotFoundException.class,()->
                service.BuscarClientePorEmail("jDoe@domain.com")
                );
        assertEquals("No se encontro Clientes registrador con el correo electronico 'jDoe@domain.com'", exc.getMessage());
    }

    @Test
    void vincularVehiculoNuevo() {

        Vehiculo vehiculonuevo = new Vehiculo(2L,2005,"azul",null,null,"321",null);
        List<Vehiculo> listavehiculos = new ArrayList<>(Arrays.asList(DummyData.listaVehiculos.get(0)));
        Cliente nuevocliente = new Cliente(4L,"woo",null,null,null,null,null,null,null,"John",null,"jDeee@domain.com",listavehiculos);

        when(repository.findByCorreoElectronico(anyString())).thenReturn(nuevocliente);
        when(repository.save(any(Cliente.class))).thenReturn(nuevocliente);
        when(repositoryVehiculo.findByPatente(anyString())).thenReturn(null);
        when(repositoryVehiculo.save(any(Vehiculo.class))).thenReturn(vehiculonuevo);

        Cliente result = service.VincularVehiculoNuevo(vehiculonuevo,"janeDoe@domain.com");
        verify(repositoryVehiculo,times(1)).save(vehiculonuevo);
        assertEquals(vehiculonuevo,result.getVehiculos().get(1));
    }
    @Test
    void vincularVehiculoNuevo_invalidEmail() {
        Vehiculo vehiculonuevo = new Vehiculo(2L,2005,"azul",null,null,"321",null);
        Throwable exc = assertThrows(InputInvalidoException.class,()->
                service.VincularVehiculoNuevo(vehiculonuevo,"jDoe")
        );
        assertEquals("correo electronico ingresado no valido", exc.getMessage());
    }
    @Test
    void vincularVehiculoNuevo_noClientsFound() {
        Vehiculo vehiculonuevo = new Vehiculo(2L,2005,"azul",null,null,"321",null);
        Throwable exc = assertThrows(NotFoundException.class,()->
                service.VincularVehiculoNuevo(vehiculonuevo,"janeDoee@domain.com")
        );
        assertEquals("No se encontro un cliente registrado con el correo electronico 'janeDoee@domain.com'", exc.getMessage());
    }
    @Test
    void vincularVehiculoNuevo_VehiculoAlreadyExists() {
        Vehiculo vehiculonuevo = new Vehiculo(2L,2005,"azul",null,null,"321",null);
        Cliente nuevocliente = new Cliente(4L,"woo",null,null,null,null,null,null,null,"John",null,"jDeee@domain.com",null);

        when(repository.findByCorreoElectronico(anyString())).thenReturn(nuevocliente);
        when(repository.save(any(Cliente.class))).thenReturn(nuevocliente);
        when(repositoryVehiculo.findByPatente(anyString())).thenReturn(DummyData.listaVehiculos.get(0));

        Throwable exc = assertThrows(AlreadyExistsException.class,()->
                service.VincularVehiculoNuevo(vehiculonuevo,"janeDoe@domain.com")
        );
        assertEquals("ya existe un vehiculo con la patente '321'", exc.getMessage());
    }

    @Test
    void registrarNuevoCliente() {
        List<Cliente> clientesdevehiculo = new ArrayList<> (Arrays.asList(DummyData.listaClientes.get(0)));
        Vehiculo vehiculonuevo = new Vehiculo(2L,2005,"azul",null,null,"321",clientesdevehiculo);
        List<Vehiculo> vehiculosDeCliente = new ArrayList<> (Arrays.asList(vehiculonuevo));
        Cliente nuevocliente = new Cliente(2L,"Doe",null,null,null,null,null,null,null,"Jane",null,"janeDoe@domain.com",vehiculosDeCliente);
        when(repository.findByCorreoElectronico(anyString())).thenReturn(null);
        when(repository.save(any(Cliente.class))).thenReturn(nuevocliente);
        when(repositoryVehiculo.findByPatente(anyString())).thenReturn(null);
        when(repositoryVehiculo.save(any(Vehiculo.class))).thenReturn(vehiculonuevo);

        Cliente result = service.RegistrarNuevoCliente(nuevocliente);

        assertEquals(nuevocliente,result);
        verify(repository,times(1)).save(nuevocliente);
        assertEquals(vehiculonuevo,result.getVehiculos().get(0));
    }
    @Test
    void registrarNuevoCliente_invalidEmail() {

        Cliente nuevocliente = new Cliente(2L,"Doe",null,null,null,null,null,null,null,"Jane",null,"janeDoedomain.com",null);

        Throwable exc = assertThrows(InputInvalidoException.class,()->
                service.RegistrarNuevoCliente(nuevocliente)
        );
        assertEquals("Ingresaste un correo electronico no valido", exc.getMessage());
    }
    @Test
    void registrarNuevoCliente_clienteAlreadyExists() {

        Cliente nuevocliente = new Cliente(2L,"Doe",null,null,null,null,null,null,null,"Jane",null,"janeDoe@domain.com",null);
        when(repository.findByCorreoElectronico(anyString())).thenReturn(DummyData.listaClientes.get(0));
        Throwable exc = assertThrows(AlreadyExistsException.class,()->
                service.RegistrarNuevoCliente(nuevocliente)
        );
        assertEquals("Ya hay un usuario registrado con el correo electronico 'janeDoe@domain.com'", exc.getMessage());
    }
    @Test
    void registrarNuevoCliente_vehiculoAlreadyExists() {

        Vehiculo vehiculonuevo = new Vehiculo(2L,2005,"azul",null,null,"321",null);
        List<Vehiculo> vehiculosDeCliente = new ArrayList<> (Arrays.asList(vehiculonuevo));
        Cliente nuevocliente = new Cliente(2L,"Doe",null,null,null,null,null,null,null,"Jane",null,"janeDoe@domain.com",vehiculosDeCliente);
        when(repository.findByCorreoElectronico(anyString())).thenReturn(null);
        when(repositoryVehiculo.findByPatente(anyString())).thenReturn(DummyData.listaVehiculos.get(0));
        Throwable exc = assertThrows(AlreadyExistsException.class,()->
                service.RegistrarNuevoCliente(nuevocliente)
        );
        assertEquals("Ya existe un vehiculo registrado con la patente '321'", exc.getMessage());
    }
}