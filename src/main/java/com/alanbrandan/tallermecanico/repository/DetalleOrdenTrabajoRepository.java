package com.alanbrandan.tallermecanico.repository;

import com.alanbrandan.tallermecanico.domain.Cliente;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenTrabajoRepository extends JpaRepository<OrdenTrabajoDetalle,Long> {
    @Query("SELECT c FROM OrdenTrabajoDetalle c WHERE c.ordendetrabajo = ?1")
    OrdenTrabajoDetalle findByOrdendetrabajo(Long id);

}
