package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.EquipoDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.EquipoRepository;
import com.torneos.futbol.repository.TorneoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EquipoServiceImplTest {

    @InjectMocks
    private EquipoServiceImpl equipoService;

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private TorneoRepository torneoRepository;


    private final EquipoDto equipoDto = EquipoDto.builder().nombre("EquipoDto").build();


    @Test
    void save(){
        Equipo equipoGuardado = new Equipo();
        equipoGuardado.setNombre(equipoDto.getNombre());
        equipoGuardado.setId(1);

        equipoDto.setTorneoId(null);

        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoGuardado);

        Equipo resultado = equipoService.save(equipoDto);

        assertEquals(equipoGuardado,resultado);
        assertEquals(resultado.getNombre(),equipoDto.getNombre());
        verify(equipoRepository,times(1)).save(any(Equipo.class));
    }
    @Test
    void saveEquipoDtoTorneoIdExistente() {

        Integer idExistente = 1;
        Equipo equipoGuardado = new Equipo();
        equipoGuardado.setNombre(equipoDto.getNombre());
        equipoGuardado.setId(1);

        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(idExistente);
        equipoGuardado.setTorneo(torneoExistente);

        equipoDto.setTorneoId(idExistente);

        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoGuardado);
        when(torneoRepository.findById(idExistente)).thenReturn(Optional.of(torneoExistente));

        Equipo resultado = equipoService.save(equipoDto);

        assertEquals(equipoGuardado,resultado);
        assertEquals(resultado.getNombre(),equipoDto.getNombre());
        assertEquals(resultado.getTorneo().getId(),equipoDto.getTorneoId());
        verify(equipoRepository,times(1)).save(any(Equipo.class));
    }

    @Test
    void saveEquipoTorneoIdNoExiste(){

        Integer idNoExistente = 2;
        equipoDto.setTorneoId(idNoExistente);

        when(torneoRepository.findById(idNoExistente)).thenReturn(Optional.empty());

        try {
            equipoService.save(equipoDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Torneo con Id: "+idNoExistente+" no existe",exception.getMessage());
        }
        verify(equipoRepository,never()).save(any(Equipo.class));
    }

    @Test
    void delete() {
        Integer idExistente = 1;
        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(idExistente);

        when(equipoRepository.findById(idExistente)).thenReturn(Optional.of(equipoExistente));

        equipoService.delete(idExistente);

        verify(equipoRepository,times(1)).delete(equipoExistente);
    }

    @Test
    void deleteIdNoExistente(){
        Integer idNoExistente = 2;
        Equipo equipo = new Equipo();
        equipo.setId(idNoExistente);

        when(equipoRepository.findById(idNoExistente)).thenReturn(Optional.empty());

        try {
            equipoService.delete(idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encontro el Equipo con el Id: " +idNoExistente,exception.getMessage());
        }

        verify(equipoRepository,never()).delete(any(Equipo.class));
    }

    @Test
    void findById() {
        Integer id = 1;
        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(id);

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipoExistente));

        Equipo resultado = equipoService.findById(id);

        assertEquals(equipoExistente,resultado);
        verify(equipoRepository,times(1)).findById(id);
    }

    @Test
    void findByIdNoExistente(){
        Integer idNoExistente = 2;

        when(equipoRepository.findById(idNoExistente)).thenReturn(Optional.empty());

        try {
            equipoService.findById(idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encuentra ningun Equipo con Id: " +idNoExistente,exception.getMessage());
        }
        verify(equipoRepository,times(1)).findById(idNoExistente);
    }

    @Test
    void findAll() {
        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();
        List<Equipo> equipoList = new ArrayList<>();
        equipoList.add(equipo1);
        equipoList.add(equipo2);

        when(equipoRepository.findAll()).thenReturn(equipoList);

        List<Equipo> resultado = equipoService.findAll();

        assertEquals(equipoList,resultado);
        verify(equipoRepository,times(1)).findAll();
    }

    @Test
    void update(){
        Integer idExistente = 1;

        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(idExistente);
        equipoExistente.setNombre(equipoDto.getNombre());

        when(equipoRepository.findById(idExistente)).thenReturn(Optional.of(equipoExistente));
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoExistente);

        Equipo resultado = equipoService.update(equipoDto,idExistente);

        assertEquals(equipoExistente,resultado);
        verify(equipoRepository,times(1)).save(any(Equipo.class));
    }

    @Test
    void updateEquipoTorneoIdExistente() {
        Integer idExistente = 1;
        equipoDto.setTorneoId(idExistente);
        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(idExistente);
        equipoExistente.setNombre(equipoDto.getNombre());


        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(idExistente);

        equipoExistente.setTorneo(torneoExistente);

        when(equipoRepository.findById(idExistente)).thenReturn(Optional.of(equipoExistente));
        when(torneoRepository.findById(idExistente)).thenReturn(Optional.of(torneoExistente));
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoExistente);

        Equipo resultado = equipoService.update(equipoDto,idExistente);

        assertEquals(equipoExistente,resultado);
        verify(equipoRepository,times(1)).save(any(Equipo.class));

    }

    @Test
    void  updateEquipoDtoNull(){
        Integer idNoExistente = 2;

        try{
            equipoService.update(null,idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Equipo no puede ser null",exception.getMessage());
        }
        verify(equipoRepository,never()).save(any(Equipo.class));
    }

    @Test
    void updateEquipoNoExistente(){
        Integer idNoExistente = 2;
        equipoDto.setTorneoId(idNoExistente);

        when(equipoRepository.findById(idNoExistente)).thenReturn(Optional.empty());

        try{
            equipoService.update(equipoDto,idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Equipo con Id: " +idNoExistente + " no existe",exception.getMessage());
        }
        verify(equipoRepository,never()).save(any(Equipo.class));
    }

    @Test
    void updateTorneoNoExistente(){
        Integer idTorneoNoExistente = 2;
        equipoDto.setTorneoId(idTorneoNoExistente);
        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(1);


        when(torneoRepository.findById(idTorneoNoExistente)).thenReturn(Optional.empty());
        when(equipoRepository.findById(1)).thenReturn(Optional.of(equipoExistente));

        try {
            equipoService.update(equipoDto,1);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Torneo con Id: " + idTorneoNoExistente + " no existe",exception.getMessage());
        }
        verify(equipoRepository,never()).save(any(Equipo.class));
    }
}