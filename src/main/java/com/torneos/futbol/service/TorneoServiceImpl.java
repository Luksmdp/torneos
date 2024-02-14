package com.torneos.futbol.service;

import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.TorneoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TorneoServiceImpl implements TorneoService {

    private final TorneoRepository torneoRepository;
    @Override
    public Torneo save(TorneoDto torneoDto) {
        Torneo torneo = new Torneo();
        torneo.setEquipos(null);
        torneo.setNombre(torneoDto.getNombre());
        torneo.setFechaInicio(torneoDto.getFechaInicio());
        return torneoRepository.save(torneo);
    }

    @Override
    public void delete(Torneo torneo) {
        torneoRepository.delete(torneo);
    }

    @Override
    public Torneo findById(Integer id) {
        return torneoRepository.findById(id).orElse(null);
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
                throw new RuntimeException("El torneo con ID " + id + " no existe.");
            }
        }
        else {
            throw new RuntimeException("El torneo con esta vacio");
        }
    }
}
