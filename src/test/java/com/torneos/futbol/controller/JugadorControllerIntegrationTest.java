package com.torneos.futbol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.torneos.futbol.model.dto.JugadorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class JugadorControllerIntegrationTest {

    private final MockMvc mockMvc;

    @Autowired
    public JugadorControllerIntegrationTest(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    @Test
    @Transactional
    void save() throws Exception {
        JugadorDto jugadorDto = JugadorDto.builder().nombre("Nuevo Torneo")
                .posicion("Volante")
                .edad(33).build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/jugadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jugadorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Transactional
    void saveBadRequest() throws Exception {
        JugadorDto jugadorDto = JugadorDto.builder().build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/jugadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jugadorDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Transactional
    void findAll() throws Exception {
        int length = 2;

        mockMvc.perform(get("/api/v1/jugadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(length))
                .andExpect(jsonPath("$[0].nombre").value("Lucas"))
                .andExpect(jsonPath("$[0].posicion").value("Defensor"))
                .andExpect(jsonPath("$[0].edad").value(30))
                .andExpect(jsonPath("$[1].nombre").value("Juan"))
                .andExpect(jsonPath("$[1].posicion").value("Delantero"))
                .andExpect(jsonPath("$[1].edad").value(22));
    }

    @Test
    @Transactional
    void findById() throws Exception {

        mockMvc.perform(get("/api/v1/jugadores/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Lucas"))
                .andExpect(jsonPath("$.posicion").value("Defensor"))
                .andExpect(jsonPath("$.edad").value(30));

    }

    @Test
    @Transactional
    void findByIdBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/jugadores/{id}",3))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void delete()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/jugadores/{id}",2))
                .andExpect(status().isOk());


    }

    @Test
    @Transactional
    void deleteBadRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/jugadores/{id}",5))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void update()throws Exception{

        JugadorDto jugadorDto = JugadorDto.builder()
                .nombre("Nuevo Jugador Update")
                .posicion("Nueva Posicion")
                .edad(99).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/jugadores/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jugadorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo Jugador Update"))
                .andExpect(jsonPath("$.posicion").value("Nueva Posicion"))
                .andExpect(jsonPath("$.edad").value(99))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @Transactional
    void updateBadRequest()throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/jugadores/{id}",5))
                .andExpect((status().isBadRequest()));
    }

}