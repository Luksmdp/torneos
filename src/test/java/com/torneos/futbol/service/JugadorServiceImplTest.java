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

    private final JugadorDto jugadorDto = JugadorDto.builder().nombre("Prueba").edad(12).posicion("Defensor").build();


    @Test
    void save() {
        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setNombre(jugadorDto.getNombre());
        jugadorExistente.setEdad(jugadorDto.getEdad());
        jugadorExistente.setPosicion(jugadorDto.getPosicion());
        jugadorExistente.setId(1);

        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorExistente);

        Jugador resultado = jugadorService.save(jugadorDto);

        assertEquals(jugadorExistente,resultado);

        verify(jugadorRepository,times(1)).save(any(Jugador.class));
    }

    @Test
    void saveJugadorIdEquipoExistente(){
        Integer idEquipoExistente = 1;
        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(idEquipoExistente);

        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setEquipo(equipoExistente);

        jugadorDto.setEquipoId(idEquipoExistente);

        when(equipoRepository.findById(idEquipoExistente)).thenReturn(Optional.of(equipoExistente));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorExistente);

        Jugador resultado = jugadorService.save(jugadorDto);

        assertEquals(jugadorExistente,resultado);

        verify(jugadorRepository, times(1)).save(any(Jugador.class));
    }

    @Test
    void saveJugadorIdEquipoNoExistente(){
        Integer idEquipoNoExistente = 2;
        jugadorDto.setEquipoId(idEquipoNoExistente);

        when(equipoRepository.findById(idEquipoNoExistente)).thenReturn(Optional.empty());

        try {
            jugadorService.save(jugadorDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Equipo con Id: "+idEquipoNoExistente+ " no existe",exception.getMessage());
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
    void update(){
        Integer idExistente = 1;

        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setNombre(jugadorDto.getNombre());
        jugadorExistente.setPosicion(jugadorDto.getPosicion());
        jugadorExistente.setEdad(jugadorDto.getEdad());
        jugadorExistente.setId(idExistente);

        when(jugadorRepository.findById((idExistente))).thenReturn(Optional.of(jugadorExistente));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorExistente);

        Jugador resultado = jugadorService.update(jugadorDto,idExistente);

        assertEquals(jugadorExistente,resultado);
        verify(jugadorRepository,times(1)).save(jugadorExistente);
        verify(jugadorRepository, times(1)).findById((idExistente));
    }

    @Test
    void updateJugadorEquipoExistente() {
        Integer idExistente = 1;
        jugadorDto.setEquipoId(idExistente);

        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setNombre(jugadorDto.getNombre());
        jugadorExistente.setPosicion(jugadorDto.getPosicion());
        jugadorExistente.setEdad(jugadorDto.getEdad());
        jugadorExistente.setId(1);

        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(idExistente);
        equipoExistente.setNombre("Equipo A");

        jugadorExistente.setEquipo(equipoExistente);


        when(jugadorRepository.findById((1))).thenReturn(Optional.of(jugadorExistente));
        when(equipoRepository.findById((idExistente))).thenReturn(Optional.of(equipoExistente));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorExistente);

        Jugador resultado = jugadorService.update(jugadorDto,1);

        assertEquals(jugadorExistente,resultado);

        assertNotNull(resultado);
        assertNotNull(resultado.getEquipo());

        verify(jugadorRepository,times(1)).save(jugadorExistente);
        verify(jugadorRepository, times(1)).findById((idExistente));
    }

    @Test
    void updateIdJugadorNoExistente(){
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
        try{
            jugadorService.update(null,1);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Jugador no puede ser null",exception.getMessage());
        }
        verify(jugadorRepository,never()).save(any(Jugador.class));
    }

    @Test
    void updateEquipoIdNoExistente(){
        Integer idEquipoNoExistente = 2;

        jugadorDto.setEquipoId(idEquipoNoExistente);

        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setId(1);

        when(jugadorRepository.findById(1)).thenReturn(Optional.of(jugadorExistente));
        when(equipoRepository.findById(idEquipoNoExistente)).thenReturn(Optional.empty());

        try{
            jugadorService.update(jugadorDto,1);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encuentra ningun Equipo con el Id: " +idEquipoNoExistente,exception.getMessage());
        }

        verify(jugadorRepository,times(1)).findById((1));
        verify(equipoRepository,times(1)).findById((idEquipoNoExistente));
        verify(jugadorRepository,never()).save(any(Jugador.class));
    }
}