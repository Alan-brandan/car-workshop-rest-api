package com.alanbrandan.tallermecanico.repository;

import com.alanbrandan.tallermecanico.domain.ManoObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManoObraRepository extends JpaRepository<ManoObra,Long> {
}
