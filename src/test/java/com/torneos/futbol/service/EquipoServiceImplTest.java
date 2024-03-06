package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.EquipoDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.EquipoRepository;
import com.torneos.futbol.repository.TorneoRepository;
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
class EquipoServiceImplTest {

    @InjectMocks
    private EquipoServiceImpl equipoService;

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private TorneoRepository torneoRepository;

    @Test
    void save(){
        Integer idEquipoNuevo = 1;

        String nuevoNombre = "nuevoNombre";
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(null).build();

        Equipo equipoExistente = new Equipo();
        equipoExistente.setNombre(nuevoNombre);
        equipoExistente.setId(idEquipoNuevo);

        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoExistente);

        Equipo resultado = equipoService.save(equipoDto);

        assertEquals(nuevoNombre,resultado.getNombre());
        verify(equipoRepository,times(1)).save(any(Equipo.class));
    }
    @Test
    void saveEquipoDtoTorneoIdExistente() {
        String nuevoNombre = "nuevoNombre";
        Integer nuevoTorneoId = 1;
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(nuevoTorneoId).build();

        Integer idEquipoExistente = 5;
        Equipo equipoExistente = new Equipo();
        equipoExistente.setNombre(equipoDto.getNombre());
        equipoExistente.setId(idEquipoExistente);

        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(nuevoTorneoId);

        equipoExistente.setTorneo(torneoExistente);

        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipoExistente);
        when(torneoRepository.findById(nuevoTorneoId)).thenReturn(Optional.of(torneoExistente));

        Equipo resultado = equipoService.save(equipoDto);

        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(nuevoTorneoId,resultado.getTorneo().getId());
        verify(equipoRepository,times(1)).save(any(Equipo.class));
        verify(torneoRepository,times(1)).findById(nuevoTorneoId);
    }

    @Test
    void saveEquipoTorneoIdNoExiste(){

        String nuevoNombre = "nuevoNombre";
        Integer nuevoTorneoIdNoExistente = 1;
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(nuevoTorneoIdNoExistente).build();

        when(torneoRepository.findById(nuevoTorneoIdNoExistente)).thenReturn(Optional.empty());

        try {
            equipoService.save(equipoDto);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Torneo con Id: "+nuevoTorneoIdNoExistente+" no existe",exception.getMessage());
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

        assertEquals(id,resultado.getId());
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
        String nuevoNombre = "nuevoNombre";
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(null).build();

        Integer idEquipoExistente = 1;

        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(idEquipoExistente);
        equipoExistente.setNombre("nombreViejo");

        Equipo equipoActualizado = new Equipo();
        equipoActualizado.setNombre(nuevoNombre);
        equipoActualizado.setId(idEquipoExistente);

        when(equipoRepository.findById(idEquipoExistente)).thenReturn(Optional.of(equipoExistente));
        when(equipoRepository.save(equipoActualizado)).thenReturn(equipoActualizado);

        Equipo resultado = equipoService.update(equipoDto,idEquipoExistente);

        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(idEquipoExistente,resultado.getId());
        verify(equipoRepository,times(1)).save(any(Equipo.class));
        verify(equipoRepository,times(1)).findById(idEquipoExistente);
    }

    @Test
    void updateEquipoTorneoIdExistente() {
        String nuevoNombre = "nuevoNombre";
        Integer idTorneoIdExistente = 3;
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(idTorneoIdExistente).build();

        Integer idExistente = 1;
        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(idExistente);
        equipoExistente.setNombre("nombreViejo");


        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(idTorneoIdExistente);

        equipoExistente.setTorneo(torneoExistente);

        Equipo equipoActualizado = new Equipo();
        equipoActualizado.setTorneo(torneoExistente);
        equipoActualizado.setNombre(nuevoNombre);
        equipoActualizado.setId(idExistente);

        when(equipoRepository.findById(idExistente)).thenReturn(Optional.of(equipoExistente));
        when(torneoRepository.findById(idTorneoIdExistente)).thenReturn(Optional.of(torneoExistente));
        when(equipoRepository.save(equipoActualizado)).thenReturn(equipoActualizado);

        Equipo resultado = equipoService.update(equipoDto,idExistente);

        assertEquals(nuevoNombre,resultado.getNombre());
        assertEquals(idTorneoIdExistente,resultado.getTorneo().getId());
        assertEquals(idExistente,resultado.getId());
        verify(equipoRepository,times(1)).save(any(Equipo.class));
        verify(equipoRepository,times(1)).findById(idExistente);
        verify(torneoRepository,times(1)).findById(idTorneoIdExistente);

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
        String nuevoNombre = "nuevoNombre";
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(null).build();

        Integer idNoExistente = 2;

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
        Integer idEquipoExistente = 1;

        String nuevoNombre = "nuevoNombre";
        Integer idTorneoIdNoExistente = 2;
        EquipoDto equipoDto = EquipoDto.builder().nombre(nuevoNombre).torneoId(idTorneoIdNoExistente).build();

        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(idEquipoExistente);


        when(torneoRepository.findById(idTorneoIdNoExistente)).thenReturn(Optional.empty());
        when(equipoRepository.findById(idEquipoExistente)).thenReturn(Optional.of(equipoExistente));

        try {
            equipoService.update(equipoDto,idEquipoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Torneo con Id: " + idTorneoIdNoExistente + " no existe",exception.getMessage());
        }
        verify(equipoRepository,never()).save(any(Equipo.class));
    }
}