package com.torneos.futbol.service;

import com.torneos.futbol.exception.DataNullException;
import com.torneos.futbol.exception.IdNotFoundException;
import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.TorneoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TorneoServiceImpl implements TorneoService {

    private final TorneoRepository torneoRepository;
    @Override
    public Torneo save(TorneoDto torneoDto) {
        if(torneoDto.getNombre() != null && torneoDto.getFechaInicio() != null) {
            Torneo torneo = new Torneo();
            torneo.setEquipos(null);
            torneo.setNombre(torneoDto.getNombre());
            torneo.setFechaInicio(torneoDto.getFechaInicio());
            return torneoRepository.save(torneo);
        } else if (torneoDto.getNombre() == null) {
            throw new DataNullException("El nombre no puede ser null", HttpStatus.BAD_REQUEST.value());
        }
        else {
            throw new DataNullException("La FechaInicio no puede ser null",HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public void delete(Integer id) {
        Optional<Torneo> torneoOptional = torneoRepository.findById(id);

        // Verificar si el Torneo está presente
        if (torneoOptional.isPresent()) {
            // Obtener el Torneo
            Torneo torneo = torneoOptional.get();
            // Eliminar el Torneo
            torneoRepository.delete(torneo);
        } else {
            // Manejar el caso donde el Torneo no está presente
            throw new IdNotFoundException("No se encontró el Torneo con el ID: " + id, HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public Torneo findById(Integer id) {
        Optional<Torneo> torneoOptional = torneoRepository.findById(id);
        if(torneoOptional.isPresent()){
            return torneoOptional.get();
        }
        else {
            throw new IdNotFoundException("No se encuentra ningun Torneo con el ID: " + id, HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public List<Torneo> findAll() {
        return torneoRepository.findAll();
    }


    @Override
    public Torneo update(Torneo torneo,Integer id) {
        if (torneo != null) {
            // Verifica si el torneo ya existe en la base de datos
            if (torneoRepository.existsById(id)) {
                torneo.setId(id);
                // Actualiza el torneo existente
                return torneoRepository.save(torneo);
            } else {
                // Manejar el caso en que el jugador no exista
                throw new IdNotFoundException("El torneo con ID " + id + " no existe.",HttpStatus.NOT_FOUND.value());
            }
        }
        else {
            throw new DataNullException("El Torneo no puede ser null",HttpStatus.BAD_REQUEST.value());
        }
    }
}
