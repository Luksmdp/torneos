package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.TorneoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TorneoServiceImplTest {
    public static final String NUEVO_NOMBRE = "Nuevo Nombre";
    @Mock
    private TorneoRepository torneoRepository;

    @InjectMocks
    private TorneoServiceImpl torneoService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testFindAll() {
        // Arrange (preparación)
        Torneo torneo1 = new Torneo();
        Torneo torneo2 = new Torneo();

        List<Torneo> torneosSimulados = Arrays.asList(torneo1, torneo2);

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
        TorneoDto torneoDto = TorneoDto.builder().nombre("Nombre del Torneo").fechaInicio(new Date()).build();
        Torneo torneoGuardado = new Torneo();
        torneoGuardado.setNombre("Nombre del torneo guardado");
        torneoGuardado.setFechaInicio(new Date());
        torneoGuardado.setId(1); // Supongamos que el repositorio asigna un ID al guardar

        when(torneoRepository.save(any(Torneo.class))).thenReturn(torneoGuardado);

        // Act
        Torneo resultado = torneoService.save(torneoDto);

        // Assert
        assertEquals(torneoGuardado, resultado);
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
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            torneoService.delete(idTorneoNoExistente);
        });

        // Verificamos que se lanzó la excepción esperada con el mensaje correcto
        String mensajeEsperado = "400 No se encontró el Torneo con el ID: " + idTorneoNoExistente;
        assertEquals(mensajeEsperado, exception.getMessage());

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
        assertEquals(torneoExistente, resultado);
    }

    @Test
    void testFindByIdTorneoNoExistente() {
        // Arrange (preparación)
        Integer idTorneoNoExistente = 2;

        // Configuramos el comportamiento simulado del repository para devolver Optional vacío
        when(torneoRepository.findById(idTorneoNoExistente)).thenReturn(Optional.empty());

        // Act y Assert (acción y verificación)
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            torneoService.findById(idTorneoNoExistente);
        });

        // Verificamos que se lanzó la excepción esperada con el mensaje correcto
        String mensajeEsperado = "400 No se encuentra ningun Torneo con el ID: " + idTorneoNoExistente;
        assertEquals(mensajeEsperado, exception.getMessage());
    }

    @Test
    void testUpdate() throws BadRequestException {
        // Arrange (preparación)
        Integer idTorneoExistente = 1;
        TorneoDto torneoDtoActualizado = TorneoDto.builder().fechaInicio(new Date()).nombre(NUEVO_NOMBRE).build();
        Torneo torneoGuardar = new Torneo();
        torneoGuardar.setNombre(NUEVO_NOMBRE);
        torneoGuardar.setFechaInicio(new Date());
        torneoGuardar.setId(idTorneoExistente);

        Torneo torneoExistente = new Torneo();
        torneoExistente.setId(idTorneoExistente);

        // Configuramos el comportamiento simulado del repository
        when(torneoRepository.findById(idTorneoExistente)).thenReturn(Optional.of(torneoExistente));
        when(torneoRepository.save(torneoGuardar)).thenAnswer(invocation -> {
            // Devolvemos el Torneo actualizado
            return invocation.getArgument(0);
        });

        // Act (acción)
        Torneo resultado = torneoService.update(torneoDtoActualizado, idTorneoExistente);

        // Assert (verificación)
        assertEquals(torneoDtoActualizado.getNombre(), resultado.getNombre());
        assertEquals(torneoDtoActualizado.getFechaInicio(), resultado.getFechaInicio());
    }

    @Test
    void testUpdateTorneoNoExistente() {
        // Arrange (preparación)
        Integer idTorneoNoExistente = 2;
        TorneoDto torneoDtoActualizado = TorneoDto.builder().fechaInicio(new Date()).nombre(NUEVO_NOMBRE).build();

        // Configuramos el comportamiento simulado del repository para devolver Optional vacío
        when(torneoRepository.findById(idTorneoNoExistente)).thenReturn(Optional.empty());

        // Act y Assert (acción y verificación)
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            torneoService.update(torneoDtoActualizado, idTorneoNoExistente);
        });

        // Verificamos que se lanzó la excepción esperada con el mensaje correcto
        String mensajeEsperado = "400 El torneo con ID " + idTorneoNoExistente + " no existe.";
        assertEquals(mensajeEsperado, exception.getMessage());
    }

    @Test
    void testUpdateTorneoDtoNull() {
        // Arrange (preparación)
        Integer idTorneoExistente = 1;

        // Act y Assert (acción y verificación)
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            torneoService.update(null, idTorneoExistente);
        });

        // Verificamos que se lanzó la excepción esperada con el mensaje correcto
        assertEquals("400 El Torneo no puede ser null", exception.getMessage());
    }
}