package com.torneos.futbol.controller;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.EquipoDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.service.EquipoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipoControllerTest {

    @InjectMocks
    EquipoController equipoController;

    @Mock
    EquipoService equipoService;

    @Test
    void findAll() {
        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();
        List<Equipo> equipoList = new ArrayList<>();
        equipoList.add(equipo1);
        equipoList.add(equipo2);

        when(equipoService.findAll()).thenReturn(equipoList);

        List<Equipo> resultado = equipoController.findAll();

        assertEquals(equipoList,resultado);
        verify(equipoService,times(1)).findAll();
    }
    @Test
    void findById() {
        Integer id = 1;
        Equipo equipo = new Equipo();
        equipo.setId(id);
        String nombreGuardado = "Equipo Guardado";
        equipo.setNombre(nombreGuardado);

        when(equipoService.findById(id)).thenReturn(equipo);

        Equipo resultado = equipoController.findById(id);

        assertEquals(id,resultado.getId());
        assertEquals(nombreGuardado,resultado.getNombre());
        verify(equipoService,times(1)).findById(id);
    }

    @Test
    void findByIdBadRequest(){
        Integer id = 2;

        when(equipoService.findById(id)).thenThrow(new BadRequestException("BadRequest"));

        try{
            equipoController.findById(id);
        }
        catch (BadRequestException exception) {
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(equipoService,times(1)).findById(id);
    }

    @Test
    void save() {
        String nuevoNombre = "Nombre";
        Integer torneoId = 5;
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(torneoId).build();
        Integer idEquipo = 1;

        Equipo equipoGuardado = new Equipo();
        equipoGuardado.setNombre(nuevoNombre);
        equipoGuardado.setId(idEquipo);

        Torneo torneo = new Torneo();
        torneo.setId(torneoId);
        equipoGuardado.setTorneo(torneo);

        when(equipoService.save(equipoDto)).thenReturn(equipoGuardado);

        Equipo resultado = equipoController.save(equipoDto);

        assertEquals(idEquipo,resultado.getId());
        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(torneoId,resultado.getTorneo().getId());
        verify(equipoService,times(1)).save(equipoDto);
    }

    @Test
    void saveBadRequest(){
        String nuevoNombre = "Nombre";
        Integer torneoId = 5;
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(torneoId).build();

        when(equipoService.save(equipoDto)).thenThrow(new BadRequestException("BadRequest"));

        try{
            equipoController.save(equipoDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(equipoService,times(1)).save(equipoDto);
    }

    @Test
    void delete() {
        Integer id = 1;

        doNothing().when(equipoService).delete(id);

        equipoController.delete(id);

        verify(equipoService,times(1)).delete(id);
    }

    @Test
    void deleteBadRequest(){
        Integer id = 2;
        doThrow(new BadRequestException("BadRequest")).when(equipoService).delete(id);

        try {
            equipoController.delete(id);
        }
        catch (BadRequestException exception){
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(equipoService,times(1)).delete(id);
    }

    @Test
    void update() {
        String nuevoNombre = "Nombre";
        Integer torneoId = 5;
        Integer idEquipo = 1;
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(torneoId).build();

        Equipo equipoActualizado = new Equipo();
        equipoActualizado.setId(idEquipo);
        equipoActualizado.setNombre(nuevoNombre);

        when(equipoService.update(equipoDto,idEquipo)).thenReturn(equipoActualizado);

        Equipo resultado = equipoController.update(idEquipo,equipoDto);

        assertEquals(idEquipo,resultado.getId());
        assertEquals(nuevoNombre,resultado.getNombre());
        verify(equipoService,times(1)).update(equipoDto,idEquipo);
    }

    @Test
    void updateBadRequest(){
        String nuevoNombre = "Nombre";
        Integer torneoId = 5;
        Integer idEquipo = 1;
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(torneoId).build();

        when(equipoService.update(equipoDto,idEquipo)).thenThrow(new BadRequestException("BadRequest"));

        try{
            equipoController.update(idEquipo,equipoDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(equipoService,times(1)).update(equipoDto,idEquipo);
    }
}