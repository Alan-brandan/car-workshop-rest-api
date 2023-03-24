package com.alanbrandan.tallermecanico.service.implementations;

import com.alanbrandan.tallermecanico.data.DummyData;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import com.alanbrandan.tallermecanico.exception.NotFoundException;
import com.alanbrandan.tallermecanico.repository.VehiculoRepository;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class VehiculoServiceImplTest {

    @Autowired
    private VehiculoServiceImpl service;
    @MockBean
    private VehiculoRepository repository;

    @Test
    void buscarVehiculoPorPatente() {
        Long idtocompare = 1L;
        when(repository.findByPatente(anyString())).thenReturn(DummyData.listaVehiculos.get(0));
        Vehiculo result = service.BuscarVehiculoPorPatente("123");
        assertEquals(idtocompare,result.getId());
        verify(repository,times(1)).findByPatente("123");
    }
    @Test
    void buscarVehiculoPorPatente_vehiculoNotFound() {
        Long idtocompare = 1L;
        when(repository.findByPatente(anyString())).thenReturn(null);

        Throwable exc = assertThrows(NotFoundException.class,()->
                service.BuscarVehiculoPorPatente("123")
        );
        assertEquals("No se encontro un Vehiculo con la patente '123'", exc.getMessage());
    }
}