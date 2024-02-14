package com.torneos.futbol.service;

import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EquipoServiceImpl  implements EquipoService {

    private final EquipoRepository equipoRepository;
    @Override
    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public void delete(Equipo equipo) {
        equipoRepository.delete(equipo);
    }

    @Override
    public Equipo findById(Integer id) {
        return equipoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo update(Equipo equipo) {
        // Verifica si el equipo ya existe en la base de datos
        if (equipoRepository.existsById(equipo.getId())) {
            // Actualiza el equipo existente
            return equipoRepository.save(equipo);
        } else {
            // Manejar el caso en que el jugador no exista
            throw new RuntimeException("El jugador con ID " + equipo.getId() + " no existe.");
        }
    }
}
