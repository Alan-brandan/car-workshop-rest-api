package com.alanbrandan.tallermecanico.data;
import com.alanbrandan.tallermecanico.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyData {

    public static List<Vehiculo> listaVehiculos = new ArrayList<>(Arrays.asList(
            new Vehiculo(1L,2005,"rojo",null,null,"123",null)
    ));
    public static List<Cliente> listaClientes = new ArrayList<>(Arrays.asList(
            new Cliente(1L,"Doe",null,null,null,null,null,null,null,"John",null,"jDoe@domain.com",null)
    ));
    public static List<Empleado> listaEmpleados = new ArrayList<>(Arrays.asList(
            new Empleado(1L,"Doe",null,null,null,null,null,null,null,"John","recepcionista",null,null)
    ));
    public static List<Mecanico> listaMecanicos = new ArrayList<>(Arrays.asList(
            new Mecanico(1L,"Doe",null,null,null,null,null,null,null,"John","Diagnostico",null)
    ));
    public static List<OrdenTrabajoDetalle> listaOrdenDetalle = new ArrayList<>(Arrays.asList(
            new OrdenTrabajoDetalle(1L,1,0,null,null)
    ));
    public static List<OrdenTrabajo> listaOrdenTrabajo = new ArrayList<>(Arrays.asList(
            new OrdenTrabajo(1L,1,null,"creada",null,null,null,null,0,null,null,null,null,listaEmpleados.get(0),listaVehiculos.get(0),null)
    ));
    public static List<ManoObra> listaManoObra = new ArrayList<>(Arrays.asList(
            new ManoObra(1L,null,null,null,null)
    ));
    public static List<Repuesto> listaRepuestos = new ArrayList<>(Arrays.asList(
            new Repuesto(1L,null,"modelo",null,0,null)
    ));

}
