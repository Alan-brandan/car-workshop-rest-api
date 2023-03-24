package com.alanbrandan.tallermecanico.service.implementations;
import com.alanbrandan.tallermecanico.domain.*;
import com.alanbrandan.tallermecanico.exception.InputInvalidoException;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.exception.StateRequiredException;
import com.alanbrandan.tallermecanico.repository.*;
import com.alanbrandan.tallermecanico.service.interfaces.OrdenTrabajoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class OrdenTrabajoServiceImpl implements OrdenTrabajoService {

    private final OrdenTrabajoRepository repository;
    private final DetalleOrdenTrabajoRepository repositoryDetalles;
    private final VehiculoRepository repositoryVehiculos;
    private final EmpleadoRepository repositoryEmpleados;
    private final RepuestoRepository repositoryRepuestos;

    @Override
    public OrdenTrabajo EstadoReparacion(Long id) {
        if(!repository.existsById(id)){
            throw new NotFoundException("no existe una orden de trabajo con el id '" + id+"'");
        }
        OrdenTrabajo result = repository.findById(id).get();
        if(!result.getEstado().equalsIgnoreCase("Creada")){
            throw new StateRequiredException("Para pasar una orden de trabajo al estado [En reparación], esta debe estar en estado 'creada'," +
                    " su estado actual es '" + result.getEstado().toLowerCase()+"'" );
        }
        result.setEstado("En reparacion");
        return repository.save(result);
    }

    @Override
    public OrdenTrabajoDetalle EstadoFacturar(Long id, int cantidad, Repuesto repuesto) {
        if(cantidad<1){
            throw new InputInvalidoException("la cantidad de repuestos ingresada no puede ser menor a 1");
        }
        if(!repository.existsById(id)){
            throw new NotFoundException("no existe una orden de trabajo con el id '" + id+"'");
        }
        OrdenTrabajo result = repository.findById(id).get();
        if(!result.getEstado().equalsIgnoreCase("En reparacion")){
            throw new StateRequiredException("Para pasar una orden de trabajo al estado [Para facturar], esta debe estar en estado 'en reparacion'," +
                    " su estado actual es '" + result.getEstado().toLowerCase()+"'" );
        }

        OrdenTrabajoDetalle nuevoDetalle = new OrdenTrabajoDetalle(
                null,cantidad,
                ((double)Math.round((repuesto.getValor()*cantidad) * 100d) / 100d)
                ,result,repuesto);
        result.setFechaFinReparacion(LocalDate.now());
        result.setDetalle(nuevoDetalle);

        result.setEstado("Para Facturar");
        repositoryDetalles.save(nuevoDetalle);
        repositoryRepuestos.save(repuesto);
        repository.save(result);
        return nuevoDetalle;
    }

    @Override
    public OrdenTrabajo EstadoFacturado(Long id) {
        if(!repository.existsById(id)){
            throw new NotFoundException("no existe una orden de trabajo con el id '" + id+"'");
        }
        OrdenTrabajo result = repository.findById(id).get();
        if(!result.getEstado().equalsIgnoreCase("Para Facturar")){
            throw new StateRequiredException("Para pasar una orden de trabajo al estado [Facturada], esta debe estar en estado 'Para Facturar'," +
                    " su estado actual es '" + result.getEstado().toLowerCase()+"'" );
        }

        result.setImporteTotal(result.getDetalle().getValorTotal());
        result.setEstado("Facturada");
        return repository.save(result);
    }

    @Override
    public OrdenTrabajo EstadoCerrado(Long id,Long adminid,String formapago,String tarjeta,int cuotas) {
        if(!repository.existsById(id)){
            throw new NotFoundException("no existe una orden de trabajo con el id '" + id+"'");
        }
        OrdenTrabajo result = repository.findById(id).get();
        if(!result.getEstado().equalsIgnoreCase("Facturada")){
            throw new StateRequiredException("Para pasar una orden de trabajo al estado [Cerrada], esta debe estar en estado 'Facturada'," +
                    " su estado actual es '" + result.getEstado().toLowerCase()+"'" );
        }

        if(!repositoryEmpleados.existsById(adminid)){
            throw new NotFoundException("no existe un empleado registrado con el id '" + id +"'");
        }
        Empleado empleado = repositoryEmpleados.findById(adminid).get();

        if(!empleado.getTipo_empleado().equalsIgnoreCase("Administrativo")){
            throw new InputInvalidoException("el empleado con el id '"+ id +"' no es del tipo 'Administrativo'");
        }

        result.setAdministrativo(empleado);
        result.setFechaPago(LocalDate.now());

        result.setFormaPago(formapago);
        result.setTipoTarjeta(tarjeta);
        result.setCantidadCuotas(cuotas);

        result.setEstado("Cerrada");
        return repository.save(result);
    }

    @Override
    public OrdenTrabajo RegistrarNuevaOrdenDeTrabajo(String patente,OrdenTrabajo nuevoTrabajo,Long id) {

        Vehiculo vehiculoencontrado = repositoryVehiculos.findByPatente(patente);
        if(vehiculoencontrado==null){
            throw new NotFoundException("no se encontró vehiculo con la patente '"+patente+"'");
        }
        if(nuevoTrabajo.getEstado()!=null){
            nuevoTrabajo.setEstado(nuevoTrabajo.getEstado().toLowerCase());
            if(!nuevoTrabajo.ValidarEstado(nuevoTrabajo.getEstado())){
                throw new InputInvalidoException("el estado de orden de trabajo '" + nuevoTrabajo.getEstado().toLowerCase()+"' no es valido");
            }
        }
        if(!repositoryEmpleados.existsById(id)){
            throw new NotFoundException("no existe un empleado registrado con el id '" + id +"'");
        }
        Empleado recepcionista = repositoryEmpleados.findById(id).get();
        if(!recepcionista.getTipo_empleado().equalsIgnoreCase("Recepcionista")){
            throw new InputInvalidoException("el empleado con el id '"+ id +"' no es del tipo 'Recepcionista'");
        }
        nuevoTrabajo.setRecepcionista(recepcionista);
        nuevoTrabajo.setVehiculo_id(vehiculoencontrado);
        nuevoTrabajo.setFechaIngreso(LocalDate.now());
        return repository.save(nuevoTrabajo);
    }
}