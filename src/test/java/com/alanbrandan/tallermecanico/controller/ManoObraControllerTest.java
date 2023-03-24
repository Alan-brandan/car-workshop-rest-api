package com.alanbrandan.tallermecanico.controller;

import com.alanbrandan.tallermecanico.data.DummyData;
import com.alanbrandan.tallermecanico.domain.Empleado;
import com.alanbrandan.tallermecanico.domain.ManoObra;
import com.alanbrandan.tallermecanico.service.implementations.EmpleadoServiceImpl;
import com.alanbrandan.tallermecanico.service.implementations.ManoObraServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManoObraController.class)
class ManoObraControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ManoObraServiceImpl service;
    private ObjectMapper objectMapper;
    private String url;

    @BeforeEach
    void setUp() {
        this.url = "/manodeobra/";
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void registrarNuevaManodeObra() throws Exception{
        ManoObra nuevo = new ManoObra(3L,null,null, null,null);
        when(service.RegistrarNuevaManodeObra(anyLong(),anyLong(),any(ManoObra.class))).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        post(this.url.concat("?mecanico_id=1&trabajo_id=1")).contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L));
    }

    @Test
    void completarManodeObra() throws Exception{
        ManoObra nuevo = new ManoObra(3L,"detalle", LocalTime.now(), null,null);
        when(service.CompletarManodeObra(anyLong(),any(ManoObra.class))).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(this.url.concat("1")).contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.detalle").value("detalle"));
    }
}