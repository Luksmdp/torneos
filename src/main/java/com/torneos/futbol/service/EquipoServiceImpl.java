package com.torneos.futbol.service;

import com.torneos.futbol.model.dto.EquipoDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.EquipoRepository;
import com.torneos.futbol.repository.TorneoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EquipoServiceImpl  implements EquipoService {

    private final EquipoRepository equipoRepository;
    private  final TorneoRepository torneoRepository;
    @Override
    public Equipo save(EquipoDto equipoDto) {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDto.getNombre());

        Integer torneoId = equipoDto.getTorneoId();

        if (torneoId != null && torneoRepository.existsById(torneoId)) {
            Torneo torneo = torneoRepository.findById(torneoId).orElse(null);
            equipo.setTorneo(torneo);
        }

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
    public Equipo update(Equipo equipo,Integer id) {

        if(equipo != null) {

            // Verifica si el equipo ya existe en la base de datos
            if (equipoRepository.existsById(id)) {
                equipo.setId(id);
                // Actualiza el equipo existente
                return equipoRepository.save(equipo);
            } else {
                // Manejar el caso en que el jugador no exista
                throw new RuntimeException("El equipo con ID " + id + " no existe.");
            }
        }
        else {
            throw new RuntimeException("El equipo esta vacio");
        }
    }
}
