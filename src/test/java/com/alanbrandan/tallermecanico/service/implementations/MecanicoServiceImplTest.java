package com.alanbrandan.tallermecanico.service.implementations;

import com.alanbrandan.tallermecanico.domain.Mecanico;
import com.alanbrandan.tallermecanico.repository.ClienteRepository;
import com.alanbrandan.tallermecanico.repository.MecanicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MecanicoServiceImplTest {

    @Autowired
    private MecanicoServiceImpl service;
    @MockBean
    private MecanicoRepository repository;

    @Test
    void registrarNuevoMecanico() {
        Mecanico nuevoMecanico = new Mecanico(2L,"Doe",null,null,null,null,null,null,null,"Jane","Diagnostico",null);
        when(repository.save(any(Mecanico.class))).thenReturn(nuevoMecanico);

        Mecanico result = service.RegistrarNuevoMecanico(nuevoMecanico);
        assertEquals(nuevoMecanico,result);
        verify(repository,times(1)).save(nuevoMecanico);

    }
}