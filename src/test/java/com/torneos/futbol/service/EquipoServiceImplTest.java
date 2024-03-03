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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EquipoServiceImplTest {

    @InjectMocks
    private EquipoServiceImpl equipoService;

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private TorneoRepository torneoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final EquipoDto equipoDto = EquipoDto.builder().nombre("EquipoDto").build();

    @Test
    void save() {

        Integer idExistente = 1;
        Equipo equipoGuardado = new Equipo();
        equipoGuardado.setNombre(equipoDto.getNombre());
        equipoGuardado.setId(idExistente);

        Torneo torneoOptional = new Torneo();
        torneoOptional.setId(idExistente);

        equipoDto.setTorneoId(idExistente);

        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoGuardado);
        when(torneoRepository.findById(idExistente)).thenReturn(Optional.of(torneoOptional));

        Equipo resultado = equipoService.save(equipoDto);

        assertEquals(equipoGuardado,resultado);
        assertEquals(resultado.getNombre(),equipoDto.getNombre());
        assertEquals(equipoDto.getTorneoId(),torneoOptional.getId());
    }

    @Test
    void saveTorneoIdNoExiste(){

        Integer idNoExistente = 2;
        equipoDto.setTorneoId(idNoExistente);

        when(torneoRepository.findById(idNoExistente)).thenReturn(Optional.empty());
        try {
            equipoService.save(equipoDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Torneo con Id: "+idNoExistente+" no existe",exception.getMessage());
        }
    }

    @Test
    void delete() {
        Integer idExistente = 1;
        Equipo equipoOptional = new Equipo();
        equipoOptional.setId(idExistente);

        when(equipoRepository.findById(idExistente)).thenReturn(Optional.of(equipoOptional));

        equipoService.delete(idExistente);

        verify(equipoRepository,times(1)).delete(equipoOptional);
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

        verify(equipoRepository,times(0)).delete(equipo);
    }

    @Test
    void findById() {
        Integer id = 1;
        Equipo equipoOptional = new Equipo();
        equipoOptional.setId(id);

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipoOptional));

        Equipo resultado = equipoService.findById(id);

        assertEquals(equipoOptional,resultado);
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
    }

    @Test
    void findAll() {
        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();
        List<Equipo> equipoList = Arrays.asList(equipo1, equipo2);

        when(equipoRepository.findAll()).thenReturn(equipoList);

        List<Equipo> resultado = equipoService.findAll();

        assertEquals(equipoList,resultado);
        verify(equipoRepository,times(1)).findAll();
    }

    @Test
    void update() {
        Integer idExistente = 1;
        equipoDto.setTorneoId(idExistente);
        Equipo equipoOptional = new Equipo();
        equipoOptional.setId(idExistente);
        equipoOptional.setNombre(equipoDto.getNombre());


        Torneo torneoOptional = new Torneo();
        torneoOptional.setId(idExistente);
        equipoOptional.setTorneo(torneoOptional);

        when(equipoRepository.findById(idExistente)).thenReturn(Optional.of(equipoOptional));
        when(torneoRepository.findById(idExistente)).thenReturn(Optional.of(torneoOptional));
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoOptional);

        Equipo resultado = equipoService.update(equipoDto,idExistente);

        assertEquals(equipoOptional,resultado);
        assertEquals(equipoDto.getTorneoId(),resultado.getTorneo().getId());

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
    }

    @Test
    void updateEquipoNoExistente(){
        Integer idNoExistente = 2;
        equipoDto.setTorneoId(idNoExistente);

        when(torneoRepository.findById(idNoExistente)).thenReturn(Optional.empty());

        try{
            equipoService.update(equipoDto,idNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Equipo con Id: " +idNoExistente + " no existe",exception.getMessage());
        }
    }

    @Test
    void updateTorneoNoExistente(){
        Integer id = 3;
        equipoDto.setTorneoId(id);
        Equipo equipoOptional = new Equipo();
        equipoOptional.setId(id);


        when(torneoRepository.findById(id)).thenReturn(Optional.empty());
        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipoOptional));

        try {
            equipoService.update(equipoDto,id);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Torneo con Id: " + id + " no existe",exception.getMessage());
        }
    }
}