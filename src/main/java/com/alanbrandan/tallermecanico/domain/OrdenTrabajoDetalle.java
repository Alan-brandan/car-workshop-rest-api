package com.alanbrandan.tallermecanico.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_ordenes_trabajo")
public class OrdenTrabajoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private double valorTotal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orden_trabajo_id")
    private OrdenTrabajo ordendetrabajo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "repuesto_id")
    private Repuesto repuesto;

  /*  public OrdenTrabajoDetalle(int cantidad, double valorTotal, OrdenTrabajo ordendetrabajo, Repuesto repuesto) {
        this.cantidad = cantidad;
        this.valorTotal = valorTotal;
        this.ordendetrabajo = ordendetrabajo;
        this.repuesto = repuesto;
    }*/
}
