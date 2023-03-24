package com.alanbrandan.tallermecanico.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleados")
public class Empleado{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String apellido;
    @Column(length = 15)
    public String celular;
    public String calle;
    public String codigo_postal;
    public String departamento;
    public String localidad;
    public String numero;
    public String piso;
    public String nombres;
    public String tipo_empleado;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "recepcionista",fetch = FetchType.LAZY)
    private List<OrdenTrabajo> ordentrabajo_recep;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "administrativo",fetch = FetchType.LAZY)
    private List<OrdenTrabajo> ordentrabajo_admin;

    public boolean ValidarTipoEmpleado(String tipo){
        if(tipo==null || tipo.isEmpty()){
            return false;
        }
        String tipoRecepc =  "recepcionista";
        String tipoAdmin =  "administrativo";

        return tipo.toLowerCase().equals(tipoRecepc) || tipo.toLowerCase().equals(tipoAdmin);
    }
}
