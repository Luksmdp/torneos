package com.torneos.futbol.service;

import com.torneos.futbol.model.dto.EquipoDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Jugador;
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
    public Equipo update(EquipoDto equipoDto,Integer id) {

        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDto.getNombre());

        if(equipoDto.getTorneoId() !=null && torneoRepository.existsById(equipoDto.getTorneoId())){
            Torneo torneo = torneoRepository.findById(equipoDto.getTorneoId()).orElse(null);
            equipo.setTorneo(torneo);
        }
        equipo.setId(id);
        return  equipoRepository.save(equipo);
    }
}
