package com.alanbrandan.tallermecanico.service.implementations;

import com.alanbrandan.tallermecanico.domain.Mecanico;
import com.alanbrandan.tallermecanico.repository.MecanicoRepository;
import com.alanbrandan.tallermecanico.service.interfaces.MecanicoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MecanicoServiceImpl implements MecanicoService {
    private final MecanicoRepository repository;

    @Override
    public Mecanico RegistrarNuevoMecanico(Mecanico mecanico) {
        return repository.save(mecanico);
    }
}
