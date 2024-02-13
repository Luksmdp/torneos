package com.torneos.futbol.service;

import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class JugadorServiceImpl implements JugadorService {
    @Autowired
    private JugadorRepository jugadorRepository;

    @Override
    public Jugador save(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    @Override
    public void delete(Jugador jugador) {
        jugadorRepository.delete(jugador);
    }

    @Override
    public Jugador findById(Integer id) {
        return jugadorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    @Override
    public Jugador update(Jugador jugador) {
        // Verifica si el jugador ya existe en la base de datos
        if (jugadorRepository.existsById(jugador.getId())) {
            // Actualiza el jugador existente
            return jugadorRepository.save(jugador);
        } else {
            // Manejar el caso en que el jugador no exista
            throw new RuntimeException("El jugador con ID " + jugador.getId() + " no existe.");
        }
    }
}
