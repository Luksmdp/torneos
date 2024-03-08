package com.torneos.futbol.controller;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.service.TorneoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class TorneoControllerTest {

    @InjectMocks
    private TorneoController torneoController;

    @Mock
    private TorneoService torneoService;


    @Test
    void findAll() {
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
        Integer id = 1;
        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(id);

        when(torneoService.findById(id)).thenReturn(torneoExistente);

        Torneo resultado = torneoController.findById(id);

        verify(torneoService, times(1)).findById(id);
        assertEquals(torneoExistente, resultado);
    }

    @Test
    void findByIdNoExistente(){
        Integer idNoExistente = 2;

        when(torneoService.findById(idNoExistente)).thenThrow(new BadRequestException("Torneo no encontrado"));

        try{
            torneoController.findById(idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 Torneo no encontrado",exception.getMessage());
        }
        verify(torneoService,times(1)).findById(idNoExistente);
    }

    @Test
    void save() {
        Integer id = 1;
        String nuevoNombre = "Nuevo Torneo";
        Date nuevaFechaInicio = new Date(2000, Calendar.JANUARY,1);
        TorneoDto torneoDto = TorneoDto.builder().nombre(nuevoNombre).fechaInicio(nuevaFechaInicio).build();

        Torneo torneo = new Torneo();
        torneo.setId(id);
        torneo.setNombre(nuevoNombre);
        torneo.setFechaInicio(nuevaFechaInicio);

        when(torneoService.save(any(TorneoDto.class))).thenReturn(torneo);

        Torneo resultado = torneoController.save(torneoDto);

        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(nuevaFechaInicio,resultado.getFechaInicio());
        assertEquals(id,resultado.getId());
        verify(torneoService,times(1)).save(torneoDto);
    }

    @Test
    void saveBadRequest(){
        String nuevoNombre = "Nuevo Torneo";
        Date nuevaFechaInicio = new Date(2000, Calendar.JANUARY,1);
        TorneoDto torneoDto = TorneoDto.builder().nombre(nuevoNombre).fechaInicio(nuevaFechaInicio).build();

        when(torneoService.save(torneoDto)).thenThrow(new BadRequestException("BadRequest"));

        try {
            torneoController.save(torneoDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(torneoService,times(1)).save(torneoDto);
    }

    @Test
    void delete() {
        Integer id = 1;

        doNothing().when(torneoService).delete(id);

        assertDoesNotThrow(()-> torneoController.delete(id));
        verify(torneoService,times(1)).delete(id);
    }

    @Test
    void deleteBadRequest(){
        Integer id = 2;

        doThrow(new BadRequestException("BadRequest")).when(torneoService).delete(id);

        try{
            torneoController.delete(id);
        }
        catch (BadRequestException exception)
        {
            assertEquals("400 BadRequest",exception.getMessage());
        }
        assertThrows(BadRequestException.class, () -> torneoController.delete(id));
    }

    @Test
    void update() {
        Integer id = 1;
        String nuevoNombre = "Nuevo Torneo";
        Date nuevaFechaInicio = new Date(2000, Calendar.JANUARY,1);
        TorneoDto torneoDto = TorneoDto.builder().nombre(nuevoNombre).fechaInicio(nuevaFechaInicio).build();

        Torneo torneo = new Torneo();
        torneo.setId(id);
        torneo.setNombre(nuevoNombre);
        torneo.setFechaInicio(nuevaFechaInicio);

        when(torneoService.update(torneoDto,id)).thenReturn(torneo);

        Torneo resultado = torneoController.update(id,torneoDto);

        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(nuevaFechaInicio,resultado.getFechaInicio());
        assertEquals(id,resultado.getId());
        verify(torneoService,times(1)).update(torneoDto,id);
    }

    @Test
    void updateBadRequest(){
        Integer id = 2;
        String nuevoNombre = "Nuevo Torneo";
        Date nuevaFechaInicio = new Date(2000, Calendar.JANUARY,1);
        TorneoDto torneoDto = TorneoDto.builder().nombre(nuevoNombre).fechaInicio(nuevaFechaInicio).build();

        when(torneoService.update(torneoDto,id)).thenThrow(new BadRequestException("BadRequest"));

        try {
            torneoController.update(id,torneoDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(torneoService,times(1)).update(torneoDto,id);
    }
}