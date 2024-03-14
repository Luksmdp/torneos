package com.torneos.futbol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.torneos.futbol.model.dto.EquipoDto;
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
class EquipoControllerIntegrationTest {

    private final MockMvc mockMvc;

    @Autowired
    public EquipoControllerIntegrationTest(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    @Test
    @Transactional
    void save() throws Exception {
        EquipoDto equipoDto = EquipoDto.builder().nombre("Nuevo Torneo").build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/equipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(equipoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Transactional
    void saveBadRequest() throws Exception {
        EquipoDto equipoDto = EquipoDto.builder().build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/equipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(equipoDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Transactional
    void findAll() throws Exception {
        int length = 3;

        mockMvc.perform(get("/api/v1/equipos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(length))
                .andExpect(jsonPath("$[0].nombre").value("El Remanso"))
                .andExpect(jsonPath("$[1].nombre").value("Real Madrid"))
                .andExpect(jsonPath("$[2].nombre").value("Boquita"));
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
        mockMvc.perform(get("/api/v1/equipos/{id}",4))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void delete()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/equipos/{id}",2))
                .andExpect(status().isOk());


    }

    @Test
    @Transactional
    void deleteBadRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/equipos/{id}",5))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void update()throws Exception{

        EquipoDto equipoDto = EquipoDto.builder()
                .nombre("Nuevo Equipo Update")
                .torneoId(2).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/equipos/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(equipoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo Equipo Update"))
                //.andExpect(jsonPath("$.torneo.id").value(2))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @Transactional
    void updateBadRequest()throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/equipos/{id}",5))
                .andExpect((status().isBadRequest()));
    }

}