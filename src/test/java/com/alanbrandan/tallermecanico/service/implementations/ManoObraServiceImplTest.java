package com.alanbrandan.tallermecanico.service.implementations;

import com.alanbrandan.tallermecanico.data.DummyData;
import com.alanbrandan.tallermecanico.domain.ManoObra;
import com.alanbrandan.tallermecanico.exception.InputInvalidoException;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.repository.ClienteRepository;
import com.alanbrandan.tallermecanico.repository.ManoObraRepository;
import com.alanbrandan.tallermecanico.repository.MecanicoRepository;
import com.alanbrandan.tallermecanico.repository.OrdenTrabajoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ManoObraServiceImplTest {

    @Autowired
    private ManoObraServiceImpl service;
    @MockBean
    private ManoObraRepository repository;
    @MockBean
    private MecanicoRepository repositorymecanicos;
    @MockBean
    private OrdenTrabajoRepository repositoryordentrabajo;

    @Test
    void registrarNuevaManodeObra() {
        ManoObra nuevaManoObra = new ManoObra(2L,null,null,null,null);
        when(repository.save(any(ManoObra.class))).thenReturn(nuevaManoObra);
        when(repositoryordentrabajo.existsById(anyLong())).thenReturn(true);
        when(repositorymecanicos.existsById(anyLong())).thenReturn(true);
        when(repositoryordentrabajo.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaOrdenTrabajo.get(0)));
        when(repositorymecanicos.findById(anyLong())).thenReturn(Optional.ofNullable(DummyData.listaMecanicos.get(0)));

        ManoObra result = service.RegistrarNuevaManodeObra(
                DummyData.listaMecanicos.get(0).getId(),
                DummyData.listaOrdenTrabajo.get(0).getId(),
                nuevaManoObra);
        assertEquals(nuevaManoObra,result);
        verify(repository,times(1)).save(nuevaManoObra);
    }
    @Test
    void registrarNuevaManodeObra_mecanicoNotFound() {
        ManoObra nuevaManoObra = new ManoObra(2L,null,null,null,null);
        when(repositorymecanicos.existsById(anyLong())).thenReturn(false);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.RegistrarNuevaManodeObra(
                        DummyData.listaMecanicos.get(0).getId(),
                        DummyData.listaOrdenTrabajo.get(0).getId(),
                        nuevaManoObra)
        );
        assertEquals("No se encontro mecanico con el id '1'", exc.getMessage());
    }
    @Test
    void registrarNuevaManodeObra_ordenDeTrabajoNotFound() {
        ManoObra nuevaManoObra = new ManoObra(2L,null,null,null,null);
        when(repositorymecanicos.existsById(anyLong())).thenReturn(true);
        when(repositoryordentrabajo.existsById(anyLong())).thenReturn(false);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.RegistrarNuevaManodeObra(
                        DummyData.listaMecanicos.get(0).getId(),
                        DummyData.listaOrdenTrabajo.get(0).getId(),
                        nuevaManoObra)
        );
        assertEquals("No se encontro orden de trabajo con el id '1'", exc.getMessage());
    }

    @Test
    void completarManodeObra() {
        ManoObra nuevaManoObra = new ManoObra(2L,"detalle", LocalTime.now(),null,null);
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.findById(anyLong())).thenReturn(Optional.of(nuevaManoObra));
        when(repository.save(any(ManoObra.class))).thenReturn(nuevaManoObra);

        ManoObra result = service.CompletarManodeObra(1L,nuevaManoObra);
        assertEquals(nuevaManoObra,result);
        verify(repository,times(1)).save(nuevaManoObra);
    }
    @Test
    void completarManodeObra_ordenDeTrabajoNotFound() {
        ManoObra nuevaManoObra = new ManoObra(2L,"detalle", LocalTime.now(),null,null);
        when(repository.existsById(anyLong())).thenReturn(false);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.CompletarManodeObra(1L,nuevaManoObra)
        );
        assertEquals("No se encontro orden de trabajo con el id '1'", exc.getMessage());
    }
}