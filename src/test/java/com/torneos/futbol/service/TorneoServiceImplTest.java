package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.TorneoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

class TorneoServiceImplTest {
    @Mock
    private TorneoRepository torneoRepository;

    @InjectMocks
    private TorneoServiceImpl torneoService;


    @Test
    void testFindAll() {
        // Arrange (preparación)
        Torneo torneo1 = new Torneo();
        Torneo torneo2 = new Torneo();

        List<Torneo> torneosSimulados = new ArrayList<>();
        torneosSimulados.add(torneo1);
        torneosSimulados.add(torneo2);

        // Configuramos el comportamiento simulado del repository
        when(torneoRepository.findAll()).thenReturn(torneosSimulados);

        // Act (acción)
        List<Torneo> resultado = torneoService.findAll();

        // Assert (verificación)
        assertEquals(torneosSimulados, resultado);
        verify(torneoRepository, times(1)).findAll(); // Verificamos que el método findAll del repository fue llamado una vez
    }

    @Test
    void testSave() {
        // Arrange
        Integer idNuevoTorneo = 1;
        String nuevoNombre = "nuevoNombre";
        Date nuevaFechaInicio = new Date(1990,Calendar.JANUARY,1);
        TorneoDto torneoDto = TorneoDto.builder().nombre(nuevoNombre).fechaInicio(nuevaFechaInicio).build();

        Torneo torneoGuardado = new Torneo();
        torneoGuardado.setNombre(nuevoNombre);
        torneoGuardado.setFechaInicio(nuevaFechaInicio);
        torneoGuardado.setId(idNuevoTorneo);

        when(torneoRepository.save(any(Torneo.class))).thenReturn(torneoGuardado);

        // Act
        Torneo resultado = torneoService.save(torneoDto);

        // Assert
        assertEquals(nuevoNombre, resultado.getNombre());
        assertEquals(nuevaFechaInicio, resultado.getFechaInicio());
        verify(torneoRepository, times(1)).save(any(Torneo.class));
    }

    @Test
    void testDelete() throws BadRequestException {
        // Arrange (preparación)
        Integer idTorneoExistente = 1;
        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(idTorneoExistente);

        // Configuramos el comportamiento simulado del repository
        when(torneoRepository.findById(idTorneoExistente)).thenReturn(Optional.of(torneoExistente));

        // Act (acción)
        torneoService.delete(idTorneoExistente);

        // Assert (verificación)
        verify(torneoRepository, times(1)).delete(torneoExistente); // Verificamos que el método delete del repository fue llamado una vez
    }

    @Test
    void testDeleteTorneoNoExistente() {
        // Arrange (preparación)
        Integer idTorneoNoExistente = 2;

        // Configuramos el comportamiento simulado del repository para devolver Optional vacío
        when(torneoRepository.findById(idTorneoNoExistente)).thenReturn(Optional.empty());

        // Act y Assert (acción y verificación)
        try{
            torneoService.delete(idTorneoNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encontró el Torneo con el ID: " + idTorneoNoExistente,exception.getMessage());
        }

        // Verificamos que el método delete del repository no fue llamado
        verify(torneoRepository, never()).delete(any(Torneo.class));
    }

    @Test
    void testFindById() throws BadRequestException {
        // Arrange (preparación)
        Integer idTorneoExistente = 1;
        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(idTorneoExistente);

        // Configuramos el comportamiento simulado del repository
        when(torneoRepository.findById(idTorneoExistente)).thenReturn(Optional.of(torneoExistente));

        // Act (acción)
        Torneo resultado = torneoService.findById(idTorneoExistente);

        // Assert (verificación)
        assertEquals(torneoExistente.getId(), resultado.getId());
        verify(torneoRepository, times(1)).findById(idTorneoExistente);
    }

    @Test
    void testFindByIdTorneoNoExistente() {
        // Arrange (preparación)
        Integer idTorneoNoExistente = 2;

        // Configuramos el comportamiento simulado del repository para devolver Optional vacío
        when(torneoRepository.findById(idTorneoNoExistente)).thenReturn(Optional.empty());

        // Act y Assert (acción y verificación)
        try{
            torneoService.findById(idTorneoNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 No se encuentra ningun Torneo con el ID: " + idTorneoNoExistente,exception.getMessage());
        }
        verify(torneoRepository,times(1)).findById(idTorneoNoExistente);
    }

    @Test
    void testUpdate() throws BadRequestException {
        // Arrange (preparación)
        Integer idTorneoExistente = 1;
        String nuevoNombre = "nuevoNombre";
        Date nuevaFechaInicio = new Date(1990,Calendar.JANUARY,1);

        TorneoDto torneoDto = TorneoDto.builder().nombre(nuevoNombre).fechaInicio(nuevaFechaInicio).build();

        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(idTorneoExistente);
        torneoExistente.setNombre("nombreViejo");
        torneoExistente.setFechaInicio(new Date(2000,Calendar.FEBRUARY,2));

        Torneo torneoActualizado = new Torneo();
        torneoActualizado.setNombre(nuevoNombre);
        torneoActualizado.setFechaInicio(nuevaFechaInicio);
        torneoActualizado.setId(idTorneoExistente);

        // Configuramos el comportamiento simulado del repository
        when(torneoRepository.findById(idTorneoExistente)).thenReturn(Optional.of(torneoExistente));
        when(torneoRepository.save(torneoActualizado)).thenReturn(torneoActualizado);

        // Act (acción)
        Torneo resultado = torneoService.update(torneoDto, idTorneoExistente);

        // Assert (verificación)
        assertEquals(nuevoNombre, resultado.getNombre());
        assertEquals(nuevaFechaInicio, resultado.getFechaInicio());
        assertEquals(idTorneoExistente,resultado.getId());
        verify(torneoRepository,times(1)).findById(idTorneoExistente);
        verify(torneoRepository,times(1)).save(any(Torneo.class));
    }

    @Test
    void testUpdateTorneoNoExistente() {
        // Arrange (preparación)
        String nuevoNombre = "nuevoNombre";
        Date nuevaFechaInicio = new Date(1990,Calendar.JANUARY,1);
        Integer idTorneoNoExistente = 2;
        TorneoDto torneoDto = TorneoDto.builder().nombre(nuevoNombre).fechaInicio(nuevaFechaInicio).build();


        // Configuramos el comportamiento simulado del repository para devolver Optional vacío
        when(torneoRepository.findById(idTorneoNoExistente)).thenReturn(Optional.empty());

        // Act y Assert (acción y verificación)
        try{
            torneoService.update(torneoDto,idTorneoNoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 El torneo con ID " + idTorneoNoExistente + " no existe.",exception.getMessage());
        }

        verify(torneoRepository,never()).save(any(Torneo.class));
    }

    @Test
    void testUpdateTorneoDtoNull() {
        // Arrange (preparación)
        Integer idTorneoExistente = 1;

        // Act y Assert (acción y verificación)
        try{
            torneoService.update(null,idTorneoExistente);
        }
        catch (BadRequestException exception){
            assertEquals("400 El Torneo no puede ser null", exception.getMessage());
        }

        verify(torneoRepository,never()).save(any(Torneo.class));
    }
}