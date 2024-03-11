package com.torneos.futbol.controller;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.service.JugadorService;
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
class JugadorControllerTest {

    @InjectMocks
    private JugadorController jugadorController;

    @Mock
    private JugadorService jugadorService;

    @Test
    void findAll() {
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        List<Jugador> jugadorList = new ArrayList<>();
        jugadorList.add(jugador1);
        jugadorList.add(jugador2);

        when(jugadorService.findAll()).thenReturn(jugadorList);

        List<Jugador> resultado = jugadorController.findAll();

        assertEquals(jugadorList,resultado);
        verify(jugadorService,times(1)).findAll();
    }

    @Test
    void findById() {
        Integer id = 1;
        Jugador jugador = new Jugador();
        jugador.setId(id);
        jugador.setNombre("Jugador Guardado");
        jugador.setEdad(20);
        jugador.setPosicion("Posicion Guardada");

        when(jugadorService.findById(id)).thenReturn(jugador);

        Jugador resultado = jugadorController.findById(id);

        assertEquals(id,resultado.getId());
        verify(jugadorService,times(1)).findById(id);
    }

    @Test
    void findByIdBadRequest(){
        Integer id = 2;

        when(jugadorService.findById(id)).thenThrow(new  BadRequestException("BadRequest"));

        try{
            jugadorController.findById(id);
        }
        catch (BadRequestException exception) {
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(jugadorService,times(1)).findById(id);
    }

    @Test
    void save() {
        String nuevoNombre = "Nombre";
        Integer nuevaEdad = 20;
        String nuevaPosicion = "Defensor";
        Integer equipoId = 5;
        JugadorDto jugadorDto = JugadorDto.builder().nombre(nuevoNombre).edad(nuevaEdad).posicion(nuevaPosicion).equipoId(equipoId).build();
        Integer idJugador = 1;

        Jugador jugadorGuardado = new Jugador();
        jugadorGuardado.setPosicion(nuevaPosicion);
        jugadorGuardado.setEdad(nuevaEdad);
        jugadorGuardado.setId(idJugador);
        jugadorGuardado.setNombre(nuevoNombre);

        Equipo equipo = new Equipo();
        equipo.setId(equipoId);
        jugadorGuardado.setEquipo(equipo);

        when(jugadorService.save(jugadorDto)).thenReturn(jugadorGuardado);

        Jugador resultado = jugadorController.save(jugadorDto);

        assertEquals(idJugador,resultado.getId());
        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(nuevaEdad,resultado.getEdad());
        assertEquals(nuevaPosicion,resultado.getPosicion());
        assertEquals(equipoId,resultado.getEquipo().getId());
        verify(jugadorService,times(1)).save(jugadorDto);
    }

    @Test
    void saveBadRequest(){
        String nuevoNombre = "Nombre";
        Integer nuevaEdad = 20;
        String nuevaPosicion = "Defensor";
        Integer equipoId = 5;
        JugadorDto jugadorDto = JugadorDto.builder().nombre(nuevoNombre).edad(nuevaEdad).posicion(nuevaPosicion).equipoId(equipoId).build();

        when(jugadorService.save(jugadorDto)).thenThrow(new BadRequestException("BadRequest"));

        try{
            jugadorController.save(jugadorDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(jugadorService,times(1)).save(jugadorDto);
    }

    @Test
    void delete() {
        Integer id = 1;

        doNothing().when(jugadorService).delete(id);

        jugadorController.delete(id);

        verify(jugadorService,times(1)).delete(id);
    }

    @Test
    void deleteBadRequest(){
        Integer id = 2;
        doThrow(new BadRequestException("BadRequest")).when(jugadorService).delete(id);

        try {
            jugadorController.delete(id);
        }
        catch (BadRequestException exception){
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(jugadorService,times(1)).delete(id);
    }

    @Test
    void update() {
        String nuevoNombre = "Nombre";
        Integer nuevaEdad = 20;
        String nuevaPosicion = "Defensor";
        Integer equipoId = 5;
        Integer idJugador = 1;
        JugadorDto jugadorDto = JugadorDto.builder().nombre(nuevoNombre).edad(nuevaEdad).posicion(nuevaPosicion).equipoId(equipoId).build();

        Jugador jugadorActualizado = new Jugador();
        jugadorActualizado.setId(idJugador);
        jugadorActualizado.setNombre(nuevoNombre);
        jugadorActualizado.setEdad(nuevaEdad);
        jugadorActualizado.setPosicion(nuevaPosicion);

        when(jugadorService.update(jugadorDto,idJugador)).thenReturn(jugadorActualizado);

        Jugador resultado = jugadorController.update(idJugador,jugadorDto);

        assertEquals(idJugador,resultado.getId());
        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(nuevaEdad,resultado.getEdad());
        assertEquals(nuevaPosicion,resultado.getPosicion());
        verify(jugadorService,times(1)).update(jugadorDto,idJugador);
    }

    @Test
    void updateBadRequest(){
        String nuevoNombre = "Nombre";
        Integer nuevaEdad = 20;
        String nuevaPosicion = "Defensor";
        Integer equipoId = 5;
        Integer idJugador = 1;
        JugadorDto jugadorDto = JugadorDto.builder().nombre(nuevoNombre).edad(nuevaEdad).posicion(nuevaPosicion).equipoId(equipoId).build();

        when(jugadorService.update(jugadorDto,idJugador)).thenThrow(new BadRequestException("BadRequest"));

        try{
            jugadorController.update(idJugador,jugadorDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 BadRequest",exception.getMessage());
        }
        verify(jugadorService,times(1)).update(jugadorDto,idJugador);
    }
}