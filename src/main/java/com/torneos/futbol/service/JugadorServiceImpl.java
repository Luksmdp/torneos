package com.torneos.futbol.service;


import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.repository.EquipoRepository;
import com.torneos.futbol.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    @Override
    public Jugador save(JugadorDto jugadorDto) throws BadRequestException {
        Jugador jugador = new Jugador();
        jugador.setEdad(jugadorDto.getEdad());
        jugador.setNombre(jugadorDto.getNombre());
        jugador.setPosicion(jugadorDto.getPosicion());


        if (jugadorDto.getEquipoId() != null) {
            Optional<Equipo> equipoOptional = equipoRepository.findById(jugadorDto.getEquipoId());
            if (equipoOptional.isPresent()){
                jugador.setEquipo(equipoOptional.get());
            }
            else {
                throw new BadRequestException("El Torneo con Id: "+jugadorDto.getEquipoId()+ " no existe");
            }
        }

        return jugadorRepository.save(jugador);
    }

    @Override
    public void delete(Integer id) throws BadRequestException {
        Optional<Jugador> jugadorOptional = jugadorRepository.findById(id);
        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
            jugadorRepository.delete(jugador);
        } else {
            throw new BadRequestException("No se encontró el Torneo con el ID: " + id);
        }
    }

    @Override
    public Jugador findById(Integer id) throws BadRequestException {
        Optional<Jugador> jugadorOptional = jugadorRepository.findById(id);
        if (jugadorOptional.isPresent()) {
            return jugadorOptional.get();
        } else {
            throw new BadRequestException("No se encuentra ningun Jugador con Id " + id);
        }
    }

    @Override
    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    @Override
    public Jugador update(JugadorDto jugadorDto, Integer id) throws BadRequestException {
        if (jugadorDto != null) {

            Optional<Jugador> jugadorOptional = jugadorRepository.findById(id);
            if (jugadorOptional.isPresent()) {
                Jugador jugador = jugadorOptional.get();
                jugador.setEdad(jugadorDto.getEdad());
                jugador.setNombre(jugadorDto.getNombre());
                jugador.setPosicion(jugadorDto.getPosicion());
                if (jugadorDto.getEquipoId() != null) {
                    Optional<Equipo> equipoOptional = equipoRepository.findById(jugadorDto.getEquipoId());
                    if (equipoOptional.isPresent()) {
                        Equipo equipo = equipoOptional.get();
                        jugador.setEquipo(equipo);
                    }
                    else {
                        throw new BadRequestException("No se encuentra ningun Equipo con el Id: " +jugadorDto.getEquipoId());
                    }
                }
                return jugadorRepository.save(jugador);
            } else {
                throw new BadRequestException("No se encuentra ningun Jugador con Id: " + id);
            }
        } else {
            throw new BadRequestException("El Jugador no puede ser null");
        }
    }
}
