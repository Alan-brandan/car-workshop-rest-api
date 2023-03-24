package com.alanbrandan.tallermecanico.service.implementations;
import com.alanbrandan.tallermecanico.domain.ManoObra;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.repository.ManoObraRepository;
import com.alanbrandan.tallermecanico.repository.MecanicoRepository;
import com.alanbrandan.tallermecanico.repository.OrdenTrabajoRepository;
import com.alanbrandan.tallermecanico.service.interfaces.ManoObraService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@AllArgsConstructor
public class ManoObraServiceImpl implements ManoObraService {

    private final ManoObraRepository repository;
    private final MecanicoRepository repositorymecanicos;
    private final OrdenTrabajoRepository repositoryordentrabajo;

    @Override
    public ManoObra RegistrarNuevaManodeObra(Long idmecanic,Long idorden,ManoObra manodeobra) {
        if(!repositorymecanicos.existsById(idmecanic)){
            throw new NotFoundException("No se encontro mecanico con el id '" + idmecanic +"'");
        }
        if(!repositoryordentrabajo.existsById(idorden)){
            throw new NotFoundException("No se encontro orden de trabajo con el id '" + idorden +"'");
        }
        manodeobra.setMecanico(repositorymecanicos.findById(idmecanic).get());
        manodeobra.setOrden(repositoryordentrabajo.findById(idorden).get());
        return repository.save(manodeobra);
    }

    @Override
    public ManoObra CompletarManodeObra(Long id, ManoObra manodeobra) {
        if(!repository.existsById(id)){
            throw new NotFoundException("No se encontro orden de trabajo con el id '" + id +"'");
        }
        ManoObra result = repository.findById(id).get();
        result.setDetalle(manodeobra.getDetalle());
        result.setDuracion_hs(manodeobra.getDuracion_hs());
        return repository.save(result);
    }
}
