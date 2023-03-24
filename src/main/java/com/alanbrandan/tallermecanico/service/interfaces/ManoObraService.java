package com.alanbrandan.tallermecanico.service.interfaces;
import com.alanbrandan.tallermecanico.domain.ManoObra;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajo;
import org.springframework.http.ResponseEntity;

public interface ManoObraService {
    ManoObra RegistrarNuevaManodeObra(Long idmecanic,Long idorden, ManoObra manodeobra);
    ManoObra CompletarManodeObra( Long id, ManoObra manodeobra);
}
