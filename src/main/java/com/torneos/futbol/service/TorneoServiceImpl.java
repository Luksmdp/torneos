package com.torneos.futbol.service;

import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TorneoServiceImpl implements TorneoService {
    @Autowired
    private TorneoRepository torneoRepository;
    @Override
    public Torneo save(Torneo torneo) {
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
    public Torneo update(Torneo torneo) {
        // Verifica si el torneo ya existe en la base de datos
        if (torneoRepository.existsById(torneo.getId())) {
            // Actualiza el torneo existente
            return torneoRepository.save(torneo);
        } else {
            // Manejar el caso en que el jugador no exista
            throw new RuntimeException("El jugador con ID " + torneo.getId() + " no existe.");
        }
    }
}
