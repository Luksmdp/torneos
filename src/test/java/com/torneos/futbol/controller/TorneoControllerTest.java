package com.torneos.futbol.controller;

import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.service.TorneoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class TorneoControllerTest {

    @InjectMocks
    private TorneoController torneoController;

    @Mock
    private TorneoService torneoService;

    private WebTestClient webTestClient;

    @Test
    void findAll(
    ) {
        Torneo torneo1 = new Torneo();
        Torneo torneo2 = new Torneo();
        List<Torneo> torneosSimulados = new ArrayList<>();
        torneosSimulados.add(torneo1);
        torneosSimulados.add(torneo2);
        when(torneoService.findAll()).thenReturn(torneosSimulados);

        List<Torneo> resultado = torneoController.findAll();

        verify(torneoService, times(1)).findAll();
        assertEquals(torneosSimulados, resultado);
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}