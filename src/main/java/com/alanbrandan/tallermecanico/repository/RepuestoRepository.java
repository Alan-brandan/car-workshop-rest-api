package com.alanbrandan.tallermecanico.repository;

import com.alanbrandan.tallermecanico.domain.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto,Long> {
}
