package com.alanbrandan.tallermecanico.repository;
import com.alanbrandan.tallermecanico.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    @Query("SELECT c FROM Cliente c WHERE c.correoElectronico = ?1")
    Cliente findByCorreoElectronico(String email);
}
