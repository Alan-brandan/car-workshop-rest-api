package com.alanbrandan.tallermecanico.controller;

import com.alanbrandan.tallermecanico.data.DummyData;
import com.alanbrandan.tallermecanico.service.implementations.ManoObraServiceImpl;
import com.alanbrandan.tallermecanico.service.implementations.VehiculoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehiculoController.class)
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VehiculoServiceImpl service;
    private ObjectMapper objectMapper;
    private String url;

    @BeforeEach
    void setUp() {
        this.url = "/vehiculo";
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void buscarVehiculoPorPatente() throws Exception{
        when(service.BuscarVehiculoPorPatente(anyString())).thenReturn(DummyData.listaVehiculos.get(0));
        mockMvc.perform(
                        get(this.url.concat("/123")).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}