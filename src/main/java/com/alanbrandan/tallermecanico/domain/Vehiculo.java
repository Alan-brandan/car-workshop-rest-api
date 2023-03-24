package com.alanbrandan.tallermecanico.domain;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculos")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int anio;
    private String color;
    private String marca;
    private String modelo;
    @Column(nullable = false)
    private String patente;

   @JsonBackReference
   @JsonIdentityInfo(
           generator = ObjectIdGenerators.PropertyGenerator.class,
           property = "id")
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name = "cliente_vehiculo",
            joinColumns = @JoinColumn(name = "vehiculos_id"),
            inverseJoinColumns = @JoinColumn(name = "clientes_id")
    )
    private List<Cliente> clientes;
}
