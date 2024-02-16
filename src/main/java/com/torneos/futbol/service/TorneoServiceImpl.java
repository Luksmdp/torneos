package com.torneos.futbol.service;

import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.TorneoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TorneoServiceImpl implements TorneoService {

    private final TorneoRepository torneoRepository;
    @Override
    public Torneo save(TorneoDto torneoDto) throws BadRequestException {
            Torneo torneo = new Torneo();
            torneo.setEquipos(null);
            torneo.setNombre(torneoDto.getNombre());
            torneo.setFechaInicio(torneoDto.getFechaInicio());
            return torneoRepository.save(torneo);
    }

    @Override
    public void delete(Integer id) throws BadRequestException {
        Optional<Torneo> torneoOptional = torneoRepository.findById(id);

        // Verificar si el Torneo está presente
        if (torneoOptional.isPresent()) {
            // Obtener el Torneo
            Torneo torneo = torneoOptional.get();
            // Eliminar el Torneo
            torneoRepository.delete(torneo);
        } else {
            // Manejar el caso donde el Torneo no está presente
            throw new BadRequestException("No se encontró el Torneo con el ID: " + id);
        }
    }

    @Override
    public Torneo findById(Integer id) throws BadRequestException {
        Optional<Torneo> torneoOptional = torneoRepository.findById(id);
        if(torneoOptional.isPresent()){
            return torneoOptional.get();
        }
        else {
            throw new BadRequestException("No se encuentra ningun Torneo con el ID: " + id);
        }
    }

    @Override
    public List<Torneo> findAll() {
        return torneoRepository.findAll();
    }


    @Override
    public Torneo update(Torneo torneo,Integer id) throws BadRequestException {
        if (torneo != null) {
            // Verifica si el torneo ya existe en la base de datos
            if (torneoRepository.existsById(id)) {
                torneo.setId(id);
                // Actualiza el torneo existente
                return torneoRepository.save(torneo);
            } else {
                // Manejar el caso en que el jugador no exista
                throw new BadRequestException("El torneo con ID " + id + " no existe.");
            }
        }
        else {
            throw new BadRequestException("El Torneo no puede ser null");
        }
    }
}
