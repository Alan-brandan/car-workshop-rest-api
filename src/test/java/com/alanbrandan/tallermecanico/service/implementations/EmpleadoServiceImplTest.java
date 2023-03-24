package com.alanbrandan.tallermecanico.service.implementations;

import com.alanbrandan.tallermecanico.domain.Empleado;
import com.alanbrandan.tallermecanico.exception.InputInvalidoException;
import com.alanbrandan.tallermecanico.repository.EmpleadoRepository;
import com.alanbrandan.tallermecanico.repository.MecanicoRepository;
import com.alanbrandan.tallermecanico.repository.OrdenTrabajoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmpleadoServiceImplTest {

    @Autowired
    private EmpleadoServiceImpl service;
    @MockBean
    private EmpleadoRepository repository;

    @Test
    void registrarNuevoEmpleado() {
        Empleado nuevoEmpleado = new Empleado(2L,"Wayne",null,null,null,null,null,null,null,"Bruce","recepcionista",null,null);
        when(repository.save(any(Empleado.class))).thenReturn(nuevoEmpleado);

        Empleado result = service.RegistrarNuevoEmpleado(nuevoEmpleado);
        assertEquals(nuevoEmpleado,result);
        verify(repository,times(1)).save(nuevoEmpleado);
    }
    @Test
    void registrarNuevoEmpleado_tipoDeEmpleadoInvalido() {
        Empleado nuevoEmpleado = new Empleado(2L,"Wayne",null,null,null,null,null,null,null,"Bruce","cocinero",null,null);
        when(repository.save(any(Empleado.class))).thenReturn(nuevoEmpleado);

        Throwable exc = assertThrows(InputInvalidoException.class,()->
                service.RegistrarNuevoEmpleado(nuevoEmpleado)
        );
        assertEquals("tipo de empleado 'cocinero' invalido", exc.getMessage());
    }
}