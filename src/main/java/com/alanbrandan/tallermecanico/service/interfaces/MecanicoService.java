package com.alanbrandan.tallermecanico.service.interfaces;

import com.alanbrandan.tallermecanico.domain.Mecanico;
import org.springframework.web.bind.annotation.RequestBody;

public interface MecanicoService {
    Mecanico RegistrarNuevoMecanico(Mecanico mecanico);
}
