package com.torneos.futbol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.torneos.futbol.model.dto.TorneoDto;
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TorneoControllerIntegrationTest {

    private final MockMvc mockMvc;

    @Autowired
    public TorneoControllerIntegrationTest(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    @Test
    @Transactional
    void save() throws Exception {
        TorneoDto torneoDto = TorneoDto.builder().nombre("Nuevo Torneo")
                .fechaInicio(new Timestamp(2020-1-1)).build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/torneos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(torneoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Transactional
    void saveBadRequest() throws Exception {
        TorneoDto torneoDto = TorneoDto.builder().build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/torneos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(torneoDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void findAll() throws Exception {
        int length = 2;

        mockMvc.perform(get("/api/v1/torneos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(length))
                .andExpect(jsonPath("$[0].nombre").value("Parada 5"))
                .andExpect(jsonPath("$[0].fechaInicio").value("2020-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].nombre").value("Smith"))
                .andExpect(jsonPath("$[1].fechaInicio").value("2021-01-01T00:00:00.000+00:00"));
    }

    @Test
    @Transactional
    void findById() throws Exception {

        mockMvc.perform(get("/api/v1/torneos/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Parada 5"))
                .andExpect(jsonPath("$.fechaInicio").value("2020-01-01T00:00:00.000+00:00"));

    }

    @Test
    @Transactional
    void findByIdBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/torneos/{id}",3))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void delete()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/torneos/{id}",2))
                .andExpect(status().isOk());


    }

    @Test
    @Transactional
    void deleteBadRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/torneos/{id}",5))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void update()throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String fechaInicioUTC = "2020-01-01 00:00:00"; // Fecha y hora en formato UTC

        TorneoDto torneoDto = TorneoDto.builder()
                .nombre("Nuevo Torneo Update")
                .fechaInicio(Timestamp.valueOf(fechaInicioUTC)).build();

        // Serializar el objeto TorneoDto a JSON
        String jsonRequest = new ObjectMapper().writeValueAsString(torneoDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/torneos/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo Torneo Update"))
                //.andExpect(jsonPath("$.fechaInicio").value("2020-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @Transactional
    void updateBadRequest()throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/torneos/{id}",5))
                .andExpect((status().isBadRequest()));
    }

}