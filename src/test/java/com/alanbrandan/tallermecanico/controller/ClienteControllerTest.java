package com.alanbrandan.tallermecanico.controller;

import com.alanbrandan.tallermecanico.data.DummyData;
import com.alanbrandan.tallermecanico.domain.Cliente;
import com.alanbrandan.tallermecanico.domain.Vehiculo;
import com.alanbrandan.tallermecanico.service.implementations.ClienteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteServiceImpl service;
    private ObjectMapper objectMapper;
    private String url;

    @BeforeEach
    void setUp() {
        this.url = "/cliente";
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void buscarClientePorEmail() throws Exception{
        when(service.BuscarClientePorEmail(anyString())).thenReturn(DummyData.listaClientes.get(0));
        mockMvc.perform(
                        get(this.url.concat("/jDoe@domain.com")).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void vincularVehiculoNuevo() throws Exception{
        Cliente nuevo = new Cliente(3L,"Doe",null,null,null,null,null,null,null,"John",null,"jDoe@domain.com",null);
        when(service.VincularVehiculoNuevo(any(Vehiculo.class),anyString())).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        post(this.url.concat("/jDoe@domain.com/vehiculonuevo")).contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk());
    }

    @Test
    void registrarNuevoCliente() throws Exception{
        Cliente nuevo = new Cliente(3L,"Doe",null,null,null,null,null,null,null,"John",null,"jDoe@domain.com",null);
        when(service.RegistrarNuevoCliente(any(Cliente.class))).thenReturn(nuevo);
        String json = this.objectMapper.writeValueAsString(nuevo);
        mockMvc.perform(
                        post(this.url.concat("/")).contentType(MediaType.APPLICATION_JSON).content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L));
    }
}