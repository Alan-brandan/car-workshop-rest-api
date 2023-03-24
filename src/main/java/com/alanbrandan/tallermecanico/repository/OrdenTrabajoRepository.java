package com.alanbrandan.tallermecanico.repository;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo,Long> {

}
