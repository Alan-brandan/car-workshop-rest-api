package com.alanbrandan.tallermecanico.repository;
import com.alanbrandan.tallermecanico.domain.Cliente;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {
    @Query("SELECT v FROM Vehiculo v WHERE v.patente = ?1")
    Vehiculo findByPatente(String patente);
}
