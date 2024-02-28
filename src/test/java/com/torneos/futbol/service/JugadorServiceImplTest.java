package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.repository.EquipoRepository;
import com.torneos.futbol.repository.JugadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JugadorServiceImplTest {
    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private EquipoRepository equipoRepository;
    @InjectMocks
    private  JugadorServiceImpl jugadorService;

    private final JugadorDto jugadorDto = JugadorDto.builder().nombre("Prueba").edad(12).posicion("Defensor").build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Jugador jugadorGuardado = new Jugador();
        jugadorGuardado.setNombre("Prueba");
        jugadorGuardado.setEdad(12);
        jugadorGuardado.setPosicion("Defensor");
        jugadorGuardado.setId(1);

        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorGuardado);

        Jugador resultado = jugadorService.save(jugadorDto);

        assertEquals(jugadorGuardado,resultado);
        assertEquals(jugadorGuardado.getNombre(),jugadorDto.getNombre());
        assertEquals(jugadorGuardado.getEdad(),jugadorDto.getEdad());
        assertEquals(jugadorGuardado.getPosicion(),jugadorDto.getPosicion());

        verify(jugadorRepository,times(1)).save(any(Jugador.class));
    }

    @Test
    void saveJugadorIdEquipoExistente(){
        Integer idEquipoExistente = 1;
        Equipo equipoOptional = new Equipo();
        equipoOptional.setId(idEquipoExistente);
        Jugador jugadorGuardado = new Jugador();
        jugadorGuardado.setEquipo(equipoOptional);
        jugadorDto.setEquipoId(idEquipoExistente);

        when(equipoRepository.findById(idEquipoExistente)).thenReturn(Optional.of(equipoOptional));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorGuardado);

        Jugador resultado = jugadorService.save(jugadorDto);

        assertEquals(jugadorGuardado,resultado);
        assertEquals(jugadorGuardado.getEquipo(),resultado.getEquipo());

        verify(jugadorRepository, times(1)).save(any(Jugador.class));
    }

    @Test
    void saveJugadorIdEquipoNoExistente(){
        Integer idEquipoNoExistente = 2;
        jugadorDto.setEquipoId(idEquipoNoExistente);

        when(equipoRepository.findById(idEquipoNoExistente)).thenReturn(Optional.empty());

        try {
            jugadorService.save(jugadorDto);
            equipoRepository.findById(idEquipoNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Equipo con Id: "+idEquipoNoExistente+ " no existe",exception.getMessage());
        }
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

        verify(jugadorRepository,times(0)).delete(jugadorNoExistente);
    }

    @Test
    void findById() {
        Integer idExistente = 1;
        Jugador jugadorExistente = new Jugador();
        jugadorExistente.setId(idExistente);

        when(jugadorRepository.findById(idExistente)).thenReturn(Optional.of(jugadorExistente));

        Jugador resultado = jugadorService.findById(idExistente);

        assertEquals(jugadorExistente,resultado);
        assertEquals(jugadorExistente.getId(),idExistente);
        verify(jugadorRepository,times(1)).findById(idExistente);
    }

    @Test
    void findByIdNoExistente(){
        Integer idNoExistente = 2;
        Jugador jugadorNoExistente = new Jugador();
        jugadorNoExistente.setId(idNoExistente);

        when(jugadorRepository.findById(idNoExistente)).thenReturn(Optional.empty());

        try {
            jugadorService.findById(idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encuentra ningun Jugador con Id "+ idNoExistente,exception.getMessage());
        }


    }

    @Test
    void findAll() {
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        List<Jugador> jugadorList = Arrays.asList(jugador1,jugador2);

        when(jugadorRepository.findAll()).thenReturn(jugadorList);

        List<Jugador> resultado = jugadorService.findAll();

        assertEquals(jugadorList,resultado);
        verify(jugadorRepository,times(1)).findAll();
    }

    @Test
    void update() {
        Integer idExistente = 1;
        jugadorDto.setEquipoId(idExistente);

        Jugador jugadorGuardado = new Jugador();
        jugadorGuardado.setNombre("Prueba2");
        jugadorGuardado.setPosicion("Delantero");
        jugadorGuardado.setEdad(14);
        jugadorGuardado.setId(idExistente);

        Equipo equipoOptional = new Equipo();
        equipoOptional.setId(idExistente);
        equipoOptional.setNombre("Equipo A");


        when(jugadorRepository.findById((idExistente))).thenReturn(Optional.of(jugadorGuardado));
        when(equipoRepository.findById((idExistente))).thenReturn(Optional.of(equipoOptional));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugadorGuardado);

        Jugador resultado = jugadorService.update(jugadorDto,idExistente);

        assertEquals(jugadorGuardado,resultado);
        assertEquals(jugadorDto.getNombre(), resultado.getNombre());
        assertEquals(jugadorDto.getPosicion(), resultado.getPosicion());
        assertEquals(jugadorDto.getEdad(), resultado.getEdad());
        assertNotNull(resultado);

        assertNotNull(resultado.getEquipo());
        assertEquals(equipoOptional.getId(), resultado.getEquipo().getId());
        assertEquals(equipoOptional.getNombre(), resultado.getEquipo().getNombre());

        verify(jugadorRepository,times(1)).save(jugadorGuardado);
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
    }

    @Test
    void updateJugadorDtoNull(){
        Integer id = 3;
        try{
            jugadorService.update(null,id);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Jugador no puede ser null",exception.getMessage());
        }
    }

    @Test
    void updateEquipoIdNoExistente(){
        Integer id = 3;
        jugadorDto.setEquipoId(id);
        Jugador jugadorGuardado = new Jugador();
        jugadorGuardado.setId(id);
        when(jugadorRepository.findById(id)).thenReturn(Optional.of(jugadorGuardado));
        when(equipoRepository.findById(id)).thenReturn(Optional.empty());
        try{
            jugadorService.update(jugadorDto,id);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encuentra ningun Equipo con el Id: " +id,exception.getMessage());
        }
    }
}