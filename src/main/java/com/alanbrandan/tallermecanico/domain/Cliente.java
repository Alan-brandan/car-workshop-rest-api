package com.alanbrandan.tallermecanico.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 80)
    public String apellido;
    @Column(length = 15)
    public String celular;
    public String calle;
    public String codigoPostal;
    public String departamento;
    public String localidad;
    public String numero;
    public String piso;
    @Column(length = 100)
    public String nombres;
    @Column(length = 15)
    private String telefonoDeLinea;
    @Column(nullable = false)
    private String correoElectronico;

    //@JsonBackReference
    //@JsonManagedReference
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "clientes",fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculos;

}
