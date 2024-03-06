package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.repository.EquipoRepository;
import com.torneos.futbol.repository.JugadorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JugadorServiceImplTest {
    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private EquipoRepository equipoRepository;
    @InjectMocks
    private  JugadorServiceImpl jugadorService;



    @Test
    void save() {
        Integer nuevoId = 1;

        String nuevoNombre = "nuevojugador";
        String nuevaPosicion = "nuevoPosicion";
        int nuevaEdad = 3;
        JugadorDto jugadorDto = JugadorDto.builder().edad(nuevaEdad).posicion(nuevaPosicion).nombre(nuevoNombre).build();


        Jugador jugadorGuardado = new Jugador();
        jugadorGuardado.setNombre(nuevoNombre);
        jugadorGuardado.setEdad(nuevaEdad);
        jugadorGuardado.setPosicion(nuevaPosicion);
        jugadorGuardado.setId(nuevoId);

        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorGuardado);

        Jugador resultado = jugadorService.save(jugadorDto);

        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(nuevaPosicion,resultado.getPosicion());
        assertEquals(nuevaEdad,resultado.getEdad());
        assertEquals(nuevoId,resultado.getId());

        verify(jugadorRepository,times(1)).save(any(Jugador.class));
    }

    @Test
    void saveJugadorIdEquipoExistente(){
        Integer nuevoJugadorId = 1;

        String nuevoNombre = "nuevojugador";
        String nuevaPosicion = "nuevoPosicion";
        int nuevaEdad = 3;
        Integer nuevoEquipoId = 5;
        JugadorDto jugadorDto = JugadorDto.builder().edad(nuevaEdad).posicion(nuevaPosicion).nombre(nuevoNombre).equipoId(nuevoEquipoId).build();

        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(nuevoEquipoId);

        Jugador jugadorGuardado = new Jugador();
        jugadorGuardado.setEquipo(equipoExistente);
        jugadorGuardado.setNombre(nuevoNombre);
        jugadorGuardado.setPosicion(nuevaPosicion);
        jugadorGuardado.setEdad(nuevaEdad);
        jugadorGuardado.setId(nuevoJugadorId);

        when(equipoRepository.findById(nuevoEquipoId)).thenReturn(Optional.of(equipoExistente));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorGuardado);

        Jugador resultado = jugadorService.save(jugadorDto);

        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(nuevaPosicion,resultado.getPosicion());
        assertEquals(nuevaEdad,resultado.getEdad());
        assertEquals(nuevoEquipoId,resultado.getEquipo().getId());

        verify(jugadorRepository, times(1)).save(any(Jugador.class));
    }

    @Test
    void saveJugadorIdEquipoNoExistente(){
        String nuevoNombre = "nuevojugador";
        String nuevaPosicion = "nuevoPosicion";
        int nuevaEdad = 3;
        Integer equipoIdNoExistente = 2;
        JugadorDto jugadorDto = JugadorDto.builder().edad(nuevaEdad).posicion(nuevaPosicion).nombre(nuevoNombre).equipoId(equipoIdNoExistente).build();

        when(equipoRepository.findById(equipoIdNoExistente)).thenReturn(Optional.empty());

        try {
            jugadorService.save(jugadorDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Equipo con Id: "+equipoIdNoExistente+ " no existe",exception.getMessage());
        }
        verify(jugadorRepository,never()).save(any(Jugador.class));
    }

    @Test
    void delete() {
        Integer idExistente = 1;
        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setId(idExistente);

        when(jugadorRepository.findById(idExistente)).thenReturn(Optional.of(jugadorExistente));

        jugadorService.delete(idExistente);

        verify(jugadorRepository,times(1)).delete(jugadorExistente);
    }

    @Test
    void deleteJugadorNoExistente(){
        Integer idNoExistente = 2;
        Jugador jugadorNoExistente = new Jugador();
        jugadorNoExistente.setId(idNoExistente);

        when(jugadorRepository.findById(idNoExistente)).thenReturn(Optional.empty());

        try {
            jugadorService.delete(idNoExistente);
        } catch (BadRequestException exception) {
            assertEquals("400 No se encontró el Torneo con el ID: 2", exception.getMessage());
        }

        verify(jugadorRepository,never()).save(any(Jugador.class));
    }

    @Test
    void findById() {
        Integer idExistente = 1;
        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setId(idExistente);

        when(jugadorRepository.findById(idExistente)).thenReturn(Optional.of(jugadorExistente));

        Jugador resultado = jugadorService.findById(idExistente);

        assertEquals(jugadorExistente,resultado);
        verify(jugadorRepository,times(1)).findById(idExistente);
    }

    @Test
    void findByIdNoExistente(){
        Integer idNoExistente = 2;

        when(jugadorRepository.findById(idNoExistente)).thenReturn(Optional.empty());

        try {
            jugadorService.findById(idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encuentra ningun Jugador con Id "+ idNoExistente,exception.getMessage());
        }
        verify(jugadorRepository,times(1)).findById(idNoExistente);
    }

    @Test
    void findAll() {
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        List<Jugador> jugadorList = new ArrayList<>();
        jugadorList.add(jugador1);
        jugadorList.add(jugador2);

        when(jugadorRepository.findAll()).thenReturn(jugadorList);

        List<Jugador> resultado = jugadorService.findAll();

        assertEquals(jugadorList,resultado);
        verify(jugadorRepository,times(1)).findAll();
    }

    @Test
    void update() {
        Integer idExistente = 1;

        String nuevoNombre = "nuevojugador";
        String nuevaPosicion = "nuevaPosicion";
        int nuevaEdad = 3;
        JugadorDto jugadorDto = JugadorDto.builder().edad(nuevaEdad).posicion(nuevaPosicion).nombre(nuevoNombre).build();

        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setNombre("nombreViejo");
        jugadorExistente.setPosicion("posicionVieja");
        jugadorExistente.setEdad(0);
        jugadorExistente.setId(idExistente);

        Jugador jugadorActualizado = new Jugador();
        jugadorActualizado.setNombre(nuevoNombre);
        jugadorActualizado.setPosicion(nuevaPosicion);
        jugadorActualizado.setEdad(nuevaEdad);
        jugadorActualizado.setId(idExistente);

        when(jugadorRepository.findById((idExistente))).thenReturn(Optional.of(jugadorExistente));
        when(jugadorRepository.save((jugadorActualizado))).thenReturn(jugadorActualizado);

        Jugador resultado = jugadorService.update(jugadorDto, idExistente);

        assertEquals(idExistente,resultado.getId());
        assertEquals(nuevaEdad,resultado.getEdad());
        assertEquals(nuevaPosicion,resultado.getPosicion());
        assertEquals(nuevoNombre,resultado.getNombre());



        verify(jugadorRepository, times(1)).save(jugadorActualizado);
        verify(jugadorRepository, times(1)).findById((idExistente));
        verifyNoInteractions(equipoRepository);
    }

    @Test
    void updateJugadorEquipoExistente() {
        Integer idJugadorExistente = 2;

        String nuevoNombre = "nuevojugador";
        String nuevaPosicion = "nuevoPosicion";
        int nuevaEdad = 3;
        Integer equipoIdExistente = 1;
        JugadorDto jugadorDto = JugadorDto.builder().edad(nuevaEdad).posicion(nuevaPosicion).nombre(nuevoNombre).equipoId(equipoIdExistente).build();

        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setNombre("nombreViejo");
        jugadorExistente.setPosicion("posicionVieja");
        jugadorExistente.setEdad(99);
        jugadorExistente.setId(idJugadorExistente);

        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(equipoIdExistente);
        equipoExistente.setNombre("Equipo A");

        Jugador jugadorActualizado = new Jugador();
        jugadorActualizado.setNombre(nuevoNombre);
        jugadorActualizado.setPosicion(nuevaPosicion);
        jugadorActualizado.setEdad(nuevaEdad);
        jugadorActualizado.setEquipo(equipoExistente);
        jugadorActualizado.setId(idJugadorExistente);

        when(jugadorRepository.findById((idJugadorExistente))).thenReturn(Optional.of(jugadorExistente));
        when(equipoRepository.findById((equipoIdExistente))).thenReturn(Optional.of(equipoExistente));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorActualizado);

        Jugador resultado = jugadorService.update(jugadorDto,idJugadorExistente);

        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(nuevaPosicion,resultado.getPosicion());
        assertEquals(nuevaEdad,resultado.getEdad());
        assertEquals(equipoIdExistente,resultado.getEquipo().getId());

        verify(equipoRepository,times(1)).findById(equipoIdExistente);
        verify(jugadorRepository,times(1)).save(jugadorActualizado);
        verify(jugadorRepository, times(1)).findById((idJugadorExistente));
    }

    @Test
    void updateIdJugadorNoExistente(){
        String nuevoNombre = "nuevojugador";
        String nuevaPosicion = "nuevoPosicion";
        int nuevaEdad = 3;
        JugadorDto jugadorDto = JugadorDto.builder().edad(nuevaEdad).posicion(nuevaPosicion).nombre(nuevoNombre).build();

        Integer idNoExistente = 2;

        when(jugadorRepository.findById(idNoExistente)).thenReturn(Optional.empty());
        try {
            jugadorService.update(jugadorDto,idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encuentra ningun Jugador con Id: " + idNoExistente, exception.getMessage());
        }
        verify(jugadorRepository,never()).save(any(Jugador.class));
    }

    @Test
    void updateJugadorDtoNull(){
        Integer id = 1;
        try{
            jugadorService.update(null,id);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Jugador no puede ser null",exception.getMessage());
        }
        verify(jugadorRepository,never()).save(any(Jugador.class));
    }

    @Test
    void updateEquipoIdNoExistente(){
        String nuevoNombre = "nuevojugador";
        String nuevaPosicion = "nuevoPosicion";
        int nuevaEdad = 3;
        Integer idEquipoNoExistente = 2;
        JugadorDto jugadorDto = JugadorDto.builder().edad(nuevaEdad).posicion(nuevaPosicion).nombre(nuevoNombre).equipoId(idEquipoNoExistente).build();

        Integer idJugadorExistente = 1;
        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setId(idJugadorExistente);

        when(jugadorRepository.findById(idJugadorExistente)).thenReturn(Optional.of(jugadorExistente));
        when(equipoRepository.findById(idEquipoNoExistente)).thenReturn(Optional.empty());

        try{
            jugadorService.update(jugadorDto,idJugadorExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encuentra ningun Equipo con el Id: " +idEquipoNoExistente,exception.getMessage());
        }

        verify(jugadorRepository,times(1)).findById((idJugadorExistente));
        verify(equipoRepository,times(1)).findById((idEquipoNoExistente));
        verify(jugadorRepository,never()).save(any(Jugador.class));
    }
}