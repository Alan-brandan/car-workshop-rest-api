package com.alanbrandan.tallermecanico.controller;

import com.alanbrandan.tallermecanico.domain.Empleado;
import com.alanbrandan.tallermecanico.domain.Mecanico;
import com.alanbrandan.tallermecanico.service.implementations.EmpleadoServiceImpl;
import com.alanbrandan.tallermecanico.service.implementations.MecanicoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MecanicoController.class)
class MecanicoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MecanicoServiceImpl service;
    private ObjectMapper objectMapper;
    private String url;

    @BeforeEach
    void setUp() {
        this.url = "/mecanico/";
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void registrarNuevoMecanico() throws Exception{
        Mecanico nuevo = new Mecanico(3L,"Doe",null,null,null,null,null,null,null,"John","Diagnostico",null);
        when(service.RegistrarNuevoMecanico(any(Mecanico.class))).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        post(this.url).contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especialidad").value("Diagnostico"));
    }
}