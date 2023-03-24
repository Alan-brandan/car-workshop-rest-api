package com.alanbrandan.tallermecanico.controller;

import com.alanbrandan.tallermecanico.data.DummyData;
import com.alanbrandan.tallermecanico.domain.ManoObra;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajo;
import com.alanbrandan.tallermecanico.domain.OrdenTrabajoDetalle;
import com.alanbrandan.tallermecanico.domain.Repuesto;
import com.alanbrandan.tallermecanico.service.implementations.OrdenTrabajoServiceImpl;
import com.alanbrandan.tallermecanico.service.implementations.VehiculoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdenTrabajoController.class)
class OrdenTrabajoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrdenTrabajoServiceImpl service;
    private ObjectMapper objectMapper;
    private String url;

    @BeforeEach
    void setUp() {
        this.url = "/trabajo";
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void estadoReparacion() throws Exception{
        OrdenTrabajo nuevo = new OrdenTrabajo(3L,1,null,"creada",null,null,null,null,0,null,null,null,null,null,null,null);
        when(service.EstadoReparacion(anyLong())).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(this.url.concat("/1/enreparacion"))
                                .contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L));
    }

    @Test
    void estadoFacturar() throws Exception{
        OrdenTrabajoDetalle nuevo = new OrdenTrabajoDetalle(3L,1,0,null,null);
        when(service.EstadoFacturar(anyLong(),anyInt(),any(Repuesto.class))).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(this.url.concat("/1/parafacturar/1"))
                                .contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L));
    }

    @Test
    void estadoFacturado() throws Exception{
        OrdenTrabajo nuevo = new OrdenTrabajo(3L,1,null,"para facturar",null,null,null,null,0,null,null,null,null,null,null,null);
        when(service.EstadoFacturado(anyLong())).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(this.url.concat("/1/facturado"))
                                .contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L));
    }

    @Test
    void estadoCerrado() throws Exception{
        OrdenTrabajo nuevo = new OrdenTrabajo(3L,1,null,"facturado",null,null,null,null,0,null,null,null,null,null,null,null);
        when(service.EstadoCerrado(anyLong(),anyLong(),anyString(),anyString(),anyInt())).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        MockMvcRequestBuilders.put(this.url.concat("/1/cerrar/?admin_id=1&forma_pago=efectivo&tarjeta=visa&cuotas=1"))
                                .contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L));
    }

    @Test
    void registrarNuevaOrdenDeTrabajo() throws Exception{
        OrdenTrabajo nuevo = new OrdenTrabajo(3L,1,null,"creada",null,null,null,null,0,null,null,null,null,null,null,null);
        when(service.RegistrarNuevaOrdenDeTrabajo(anyString(),any(OrdenTrabajo.class),anyLong())).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        post(this.url.concat("/123/1")).contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.estado").value("creada"));
    }
}