package com.alanbrandan.tallermecanico.service.implementations;

import com.alanbrandan.tallermecanico.data.DummyData;
import com.alanbrandan.tallermecanico.domain.Empleado;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajo;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajoDetalle;
import com.alanbrandan.tallermecanico.domain.Repuesto;
import com.alanbrandan.tallermecanico.exception.InputInvalidoException;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.exception.StateRequiredException;
import com.alanbrandan.tallermecanico.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrdenTrabajoServiceImplTest {

    @Autowired
    private OrdenTrabajoServiceImpl service;
    @MockBean
    private OrdenTrabajoRepository repository;
    @MockBean
    private DetalleOrdenTrabajoRepository repositoryDetalles;
    @MockBean
    private VehiculoRepository repositoryVehiculos;
    @MockBean
    private EmpleadoRepository repositoryEmpleados;
    @MockBean
    private RepuestoRepository repositoryRepuestos;

    @Test
    void estadoReparacion() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("creada");
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(ordenUpdate));
        when(repository.save(any(OrdenTrabajo.class))).thenReturn(ordenUpdate);

        OrdenTrabajo result = service.EstadoReparacion(DummyData.listaOrdenTrabajo.get(0).getId());
        assertEquals("En reparacion",result.getEstado());
        verify(repository,times(1)).save(ordenUpdate);
    }
    @Test
    void estadoReparacion_ordenDeTrabajoNotFound() {
        when(repository.existsById(anyLong())).thenReturn(false);
        Throwable exc = assertThrows(NotFoundException.class,()->
                service.EstadoReparacion(DummyData.listaOrdenTrabajo.get(0).getId())
        );
        assertEquals("no existe una orden de trabajo con el id '1'", exc.getMessage());
    }
    @Test
    void estadoReparacion_estadoPrevioInvalido() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("cerrada");
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(ordenUpdate));

        Throwable exc = assertThrows(StateRequiredException.class,()->
                service.EstadoReparacion(DummyData.listaOrdenTrabajo.get(0).getId())
        );
        assertEquals("Para pasar una orden de trabajo al estado [En reparación]," +
                " esta debe estar en estado 'creada', su estado actual es 'cerrada'", exc.getMessage());
    }

    @Test
    void estadoFacturar() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("en reparacion");
        Repuesto nuevoRepuesto = new Repuesto(2L,null,"modelo2",null,0,null);
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaOrdenTrabajo.get(0)));
        when(repository.save(any(OrdenTrabajo.class))).thenReturn(ordenUpdate);

        OrdenTrabajoDetalle result = service.EstadoFacturar(DummyData.listaOrdenTrabajo.get(0).getId(),1,nuevoRepuesto);
        assertEquals("Para Facturar",result.getOrdendetrabajo().getEstado());
        verify(repository,times(1)).save(ordenUpdate);
    }
    @Test
    void estadoFacturar_cantidadInvalida() {
        Repuesto nuevoRepuesto = new Repuesto(2L,null,"modelo2",null,0,null);
        Throwable exc = assertThrows(InputInvalidoException.class,()->
                service.EstadoFacturar(DummyData.listaOrdenTrabajo.get(0).getId(),-1,nuevoRepuesto)
        );
        assertEquals("la cantidad de repuestos ingresada no puede ser menor a 1", exc.getMessage());
    }
    @Test
    void estadoFacturar_ordenDeTrabajoNotFound() {
        Repuesto nuevoRepuesto = new Repuesto(2L,null,"modelo2",null,0,null);
        when(repository.existsById(anyLong())).thenReturn(false);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.EstadoFacturar(DummyData.listaOrdenTrabajo.get(0).getId(),1,nuevoRepuesto)
        );
        assertEquals("no existe una orden de trabajo con el id '1'", exc.getMessage());
    }
    @Test
    void estadoFacturar_estadoPrevioInvalido() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("cerrada");
        Repuesto nuevoRepuesto = new Repuesto(2L,null,"modelo2",null,0,null);
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(ordenUpdate));

        Throwable exc = assertThrows(StateRequiredException.class,()->
                service.EstadoFacturar(DummyData.listaOrdenTrabajo.get(0).getId(),1,nuevoRepuesto)
        );
        assertEquals("Para pasar una orden de trabajo al estado [Para facturar], " +
                "esta debe estar en estado 'en reparacion', su estado actual es 'cerrada'", exc.getMessage());
    }

    @Test
    void estadoFacturado() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("para facturar");
        ordenUpdate.setDetalle(DummyData.listaOrdenDetalle.get(0));
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaOrdenTrabajo.get(0)));
        when(repository.save(any(OrdenTrabajo.class))).thenReturn(ordenUpdate);

        OrdenTrabajo result = service.EstadoFacturado(DummyData.listaOrdenTrabajo.get(0).getId());
        assertEquals("Facturada",result.getEstado());
        verify(repository,times(1)).save(ordenUpdate);
    }
    @Test
    void estadoFacturado_ordenDeTrabajoNotFound() {
        when(repository.existsById(anyLong())).thenReturn(false);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.EstadoFacturado(DummyData.listaOrdenTrabajo.get(0).getId())
        );
        assertEquals("no existe una orden de trabajo con el id '1'", exc.getMessage());
    }
    @Test
    void estadoFacturado_estadoPrevioInvalido() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("cerrada");
        ordenUpdate.setDetalle(DummyData.listaOrdenDetalle.get(0));
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaOrdenTrabajo.get(0)));

        Throwable exc = assertThrows(StateRequiredException.class,()->
                service.EstadoFacturado(DummyData.listaOrdenTrabajo.get(0).getId())
        );
        assertEquals("Para pasar una orden de trabajo al estado [Facturada], " +
                "esta debe estar en estado 'Para Facturar', su estado actual es 'cerrada'", exc.getMessage());
    }

    @Test
    void estadoCerrado() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("facturada");
        Empleado nuevoAdmin = new Empleado(2L,"Doe",null,null,null,null,null,null,null,"John","administrativo",null,null);
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaOrdenTrabajo.get(0)));
        when(repository.save(any(OrdenTrabajo.class))).thenReturn(ordenUpdate);
        when(repositoryEmpleados.existsById(anyLong())).thenReturn(true);
        when(repositoryEmpleados.findById(anyLong())).thenReturn(Optional.of(nuevoAdmin));

        OrdenTrabajo result = service.EstadoCerrado(ordenUpdate.getId(),nuevoAdmin.getId(),null,null,1);
        assertEquals("Cerrada",result.getEstado());
        verify(repository,times(1)).save(ordenUpdate);
    }
    @Test
    void estadoCerrado_ordenDeTrabajoNotFound() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("facturada");
        Empleado nuevoAdmin = new Empleado(2L,"Doe",null,null,null,null,null,null,null,"John","administrativo",null,null);
        when(repository.existsById(anyLong())).thenReturn(false);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.EstadoCerrado(ordenUpdate.getId(),nuevoAdmin.getId(),null,null,1)
        );
        assertEquals("no existe una orden de trabajo con el id '1'", exc.getMessage());
    }
    @Test
    void estadoCerrado_estadoPrevioInvalido() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("cerrada");
        Empleado nuevoAdmin = new Empleado(2L,"Doe",null,null,null,null,null,null,null,"John","administrativo",null,null);
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaOrdenTrabajo.get(0)));

        Throwable exc = assertThrows(StateRequiredException.class,()->
                service.EstadoCerrado(ordenUpdate.getId(),nuevoAdmin.getId(),null,null,1)
        );
        assertEquals("Para pasar una orden de trabajo al estado [Cerrada], " +
                "esta debe estar en estado 'Facturada', su estado actual es 'cerrada'", exc.getMessage());
    }
    @Test
    void estadoCerrado_empleadoNotFound() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("facturada");
        Empleado nuevoAdmin = new Empleado(2L,"Doe",null,null,null,null,null,null,null,"John","administrativo",null,null);
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaOrdenTrabajo.get(0)));
        when(repositoryEmpleados.existsById(anyLong())).thenReturn(false);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.EstadoCerrado(ordenUpdate.getId(),nuevoAdmin.getId(),null,null,1)
        );
        assertEquals("no existe un empleado registrado con el id '1'", exc.getMessage());
    }
    @Test
    void estadoCerrado_tipoDeEmpleadoInvalido() {
        OrdenTrabajo ordenUpdate = DummyData.listaOrdenTrabajo.get(0);
        ordenUpdate.setEstado("facturada");
        Empleado nuevoAdmin = new Empleado(2L,"Doe",null,null,null,null,null,null,null,"John","recepcionista",null,null);
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaOrdenTrabajo.get(0)));
        when(repository.save(any(OrdenTrabajo.class))).thenReturn(ordenUpdate);
        when(repositoryEmpleados.existsById(anyLong())).thenReturn(true);
        when(repositoryEmpleados.findById(anyLong())).thenReturn(Optional.of(nuevoAdmin));

        Throwable exc = assertThrows(InputInvalidoException.class,()->
                service.EstadoCerrado(ordenUpdate.getId(),nuevoAdmin.getId(),null,null,1)
        );
        assertEquals("el empleado con el id '1' no es del tipo 'Administrativo'", exc.getMessage());
    }

    @Test
    void registrarNuevaOrdenDeTrabajo() {
        OrdenTrabajo nuevaOrden = new OrdenTrabajo(2L,1,null,"creada",null,null,null,null,0,null,null,null,null,
                DummyData.listaEmpleados.get(0),DummyData.listaVehiculos.get(0),null);
        when(repositoryVehiculos.findByPatente(anyString())).thenReturn(DummyData.listaVehiculos.get(0));
        when(repositoryEmpleados.existsById(anyLong())).thenReturn(true);
        when(repositoryEmpleados.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaEmpleados.get(0)));
        when(repository.save(any(OrdenTrabajo.class))).thenReturn(nuevaOrden);

        OrdenTrabajo result = service.RegistrarNuevaOrdenDeTrabajo(
                DummyData.listaVehiculos.get(0).getPatente(),nuevaOrden,DummyData.listaEmpleados.get(0).getId());
        assertEquals(nuevaOrden,result);
        verify(repository,times(1)).save(nuevaOrden);
    }
    @Test
    void registrarNuevaOrdenDeTrabajo_vehiculoNotFound() {
        OrdenTrabajo nuevaOrden = new OrdenTrabajo(2L,1,null,"creada",null,null,null,null,0,null,null,null,null,
                DummyData.listaEmpleados.get(0),DummyData.listaVehiculos.get(0),null);
        when(repositoryVehiculos.findByPatente(anyString())).thenReturn(null);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.RegistrarNuevaOrdenDeTrabajo(
                        DummyData.listaVehiculos.get(0).getPatente(),nuevaOrden,DummyData.listaEmpleados.get(0).getId())
        );
        assertEquals("no se encontró vehiculo con la patente '123'", exc.getMessage());
    }
    @Test
    void registrarNuevaOrdenDeTrabajo_estadoDeOrdenTrabajoInvalido() {
        OrdenTrabajo nuevaOrden = new OrdenTrabajo(2L,1,null,"asd",null,null,null,null,0,null,null,null,null,
                DummyData.listaEmpleados.get(0),DummyData.listaVehiculos.get(0),null);
        when(repositoryVehiculos.findByPatente(anyString())).thenReturn(DummyData.listaVehiculos.get(0));

        Throwable exc = assertThrows(InputInvalidoException.class,()->
                service.RegistrarNuevaOrdenDeTrabajo(
                        DummyData.listaVehiculos.get(0).getPatente(),nuevaOrden,DummyData.listaEmpleados.get(0).getId())
        );
        assertEquals("el estado de orden de trabajo 'asd' no es valido", exc.getMessage());
    }
    @Test
    void registrarNuevaOrdenDeTrabajo_empleadoNotFound() {
        OrdenTrabajo nuevaOrden = new OrdenTrabajo(2L,1,null,"creada",null,null,null,null,0,null,null,null,null,
                DummyData.listaEmpleados.get(0),DummyData.listaVehiculos.get(0),null);
        when(repositoryVehiculos.findByPatente(anyString())).thenReturn(DummyData.listaVehiculos.get(0));
        when(repositoryEmpleados.existsById(anyLong())).thenReturn(false);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.RegistrarNuevaOrdenDeTrabajo(
                        DummyData.listaVehiculos.get(0).getPatente(),nuevaOrden,DummyData.listaEmpleados.get(0).getId())
        );
        assertEquals("no existe un empleado registrado con el id '1'", exc.getMessage());
    }
    @Test
    void registrarNuevaOrdenDeTrabajo_tipoDeEmpleadoInvalido() {
        Empleado recepcionistaInvalid = new Empleado(1L,"Doe",null,null,null,null,null,null,null,"John","administrativo",null,null);
        OrdenTrabajo nuevaOrden = new OrdenTrabajo(2L,1,null,"creada",null,null,null,null,0,null,null,null,null,
                recepcionistaInvalid,DummyData.listaVehiculos.get(0),null);
        when(repositoryVehiculos.findByPatente(anyString())).thenReturn(DummyData.listaVehiculos.get(0));
        when(repositoryEmpleados.existsById(anyLong())).thenReturn(true);
        when(repositoryEmpleados.findById(anyLong())).thenReturn(Optional.ofNullable(recepcionistaInvalid));

        Throwable exc = assertThrows(InputInvalidoException.class,()->
                service.RegistrarNuevaOrdenDeTrabajo(
                        DummyData.listaVehiculos.get(0).getPatente(),nuevaOrden,DummyData.listaEmpleados.get(0).getId())
        );
        assertEquals("el empleado con el id '1' no es del tipo 'Recepcionista'", exc.getMessage());
    }
}