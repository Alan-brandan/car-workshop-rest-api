package com.alanbrandan.tallermecanico.repository;

import com.alanbrandan.tallermecanico.domain.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico,Long> {
}
