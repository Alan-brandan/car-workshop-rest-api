package com.alanbrandan.tallermecanico.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mecanicos")
public class Mecanico{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String apellido;
    @Column(length = 15)
    public String celular;
    public String calle;
    public String codigoPostal;
    public String departamento;
    public String localidad;
    public String numero;
    public String piso;
    public String nombres;
    private String especialidad;

    @JsonIgnore


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "mecanico",fetch = FetchType.LAZY)
    private List<ManoObra> manoobra;
}
