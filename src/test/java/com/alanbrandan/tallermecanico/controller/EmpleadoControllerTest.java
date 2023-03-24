package com.alanbrandan.tallermecanico.controller;

import com.alanbrandan.tallermecanico.domain.Empleado;
import com.alanbrandan.tallermecanico.repository.ClienteRepository;
import com.alanbrandan.tallermecanico.repository.EmpleadoRepository;
import com.alanbrandan.tallermecanico.service.implementations.ClienteServiceImpl;
import com.alanbrandan.tallermecanico.service.implementations.EmpleadoServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmpleadoServiceImpl service;
    private ObjectMapper objectMapper;
    private String url;

    @BeforeEach
    void setUp() {
        this.url = "/empleado/";
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void registrarNuevoEmpleado() throws Exception {
        Empleado nuevo = new Empleado(3L,"Doe",null,null,null,null,null,null,null,"John","recepcionista",null,null);
        when(service.RegistrarNuevoEmpleado(any(Empleado.class))).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        post(this.url).contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo_empleado").value("recepcionista"));
    }
}