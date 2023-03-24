package com.alanbrandan.tallermecanico.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordenes_trabajo")
public class OrdenTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidadCuotas;
    private String detalleFalla;
    private String estado;
    private LocalDate fechaFinReparacion;
    private LocalDate fechaIngreso;
    private LocalDate fechaPago;
    private String formaPago;
    private double importeTotal;
    private Long kilometraje;
    private String nivelCombustible;
    private String tipoTarjeta;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "administrativo_id")
    private Empleado administrativo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recepcionista_id")
    private Empleado recepcionista;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo_id;

    @JsonIgnore
    @OneToOne (cascade = CascadeType.ALL,mappedBy = "ordendetrabajo",fetch = FetchType.LAZY)
    private OrdenTrabajoDetalle detalle;
    //@OneToMany (cascade = CascadeType.ALL,mappedBy = "orden",fetch = FetchType.LAZY)
    //private List<ManoObra> manoobra;



    public boolean ValidarEstado(String estado){
        if(estado==null || estado.isEmpty()){
            return false;
        }
        String estadoCreado =  "creada";
        String estadoEnprogreso =  "en reparacion";
        String estadoParafacturar =  "para facturar";
        String estadoFacturada =  "facturada";
        String estadoCerrado =  "cerrada";

        return estado.toLowerCase().equals(estadoCreado) || estado.toLowerCase().equals(estadoEnprogreso)
                || estado.toLowerCase().equals(estadoParafacturar) || estado.toLowerCase().equals(estadoFacturada)
                || estado.toLowerCase().equals(estadoCerrado);
    }
}
