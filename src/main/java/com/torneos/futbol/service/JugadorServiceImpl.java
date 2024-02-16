package com.torneos.futbol.service;

import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.repository.EquipoRepository;
import com.torneos.futbol.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    @Override
    public Jugador save(JugadorDto jugadorDto) {
        Jugador jugador = new Jugador();
        jugador.setEdad(jugadorDto.getEdad());
        jugador.setNombre(jugadorDto.getNombre());
        jugador.setPosicion(jugadorDto.getPosicion());

        Integer equipoId = jugadorDto.getEquipoId();

        if(equipoId != null && equipoRepository.existsById(equipoId)){
            Equipo equipo = equipoRepository.findById(equipoId).orElse(null);
            jugador.setEquipo(equipo);
        }

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
    public Jugador update(JugadorDto jugadorDto, Integer id) {
        if (jugadorRepository.existsById(id)) {

            if (equipoRepository.existsById(jugadorDto.getEquipoId())) {
                Equipo equipo = equipoRepository.findById(jugadorDto.getEquipoId()).orElse(null);
                Jugador jugador = new Jugador();
                jugador.setId(id);
                jugador.setEquipo(equipo);
                jugador.setEdad(jugadorDto.getEdad());
                jugador.setNombre(jugadorDto.getNombre());
                jugador.setPosicion(jugadorDto.getPosicion());
                return jugadorRepository.save(jugador);
            } else {
                throw new RuntimeException("El equipo con ID " + jugadorDto.getEquipoId() + " no existe.");
            }
        }
        else {
            throw new RuntimeException("El jugador con ID " + id + " no existe.");
        }
    }
}
