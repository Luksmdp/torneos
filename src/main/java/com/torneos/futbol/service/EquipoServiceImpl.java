package com.torneos.futbol.service;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.EquipoDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.repository.EquipoRepository;
import com.torneos.futbol.repository.TorneoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class EquipoServiceImpl  implements EquipoService {

    private final EquipoRepository equipoRepository;
    private  final TorneoRepository torneoRepository;
    @Override
    public Equipo save(EquipoDto equipoDto) throws BadRequestException {
        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDto.getNombre());
        if (equipoDto.getTorneoId() != null) {
            Optional<Torneo> torneoOptional = torneoRepository.findById(equipoDto.getTorneoId());
            if (torneoOptional.isPresent()){
                equipo.setTorneo(torneoOptional.get());
            }
            else {
                throw new BadRequestException("El Torneo con Id: "+equipoDto.getTorneoId()+ " no existe");
            }
        }

        return equipoRepository.save(equipo);
    }

    @Override
    public void delete(Integer id) throws BadRequestException {
        Optional<Equipo> equipoOptional = equipoRepository.findById(id);
        if (equipoOptional.isPresent()) {
            Equipo equipo = equipoOptional.get();
            equipoRepository.delete(equipo);
        }
        else {
            throw new BadRequestException("No se encontro el Equipo con el Id: " +id);
        }
    }

    @Override
    public Equipo findById(Integer id) throws BadRequestException {
        Optional<Equipo> equipoOptional = equipoRepository.findById(id);
        if (equipoOptional.isPresent()){
            return equipoOptional.get();
        }
        else {
            throw new BadRequestException("No se encuentra ningun Equipo con Id: " +id);
        }
    }

    @Override
    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo update(EquipoDto equipoDto,Integer id) throws BadRequestException {

        if(equipoDto != null) {
            Optional<Equipo> equipoOptional = equipoRepository.findById(id);
            if(equipoOptional.isPresent()) {
                Equipo equipo = equipoOptional.get();
                equipo.setNombre(equipoDto.getNombre());
                if (equipoDto.getTorneoId() != null) {
                    Optional<Torneo> torneoOptional = torneoRepository.findById(equipoDto.getTorneoId());
                    if (torneoOptional.isPresent()) {
                        equipo.setTorneo(torneoOptional.get());
                    } else {
                        throw new BadRequestException("El Torneo con Id: " + equipoDto.getTorneoId() + " no existe");
                    }
                }
                return equipoRepository.save(equipo);
            }
            else {
                throw new BadRequestException("El Equipo con Id: " +id + " no existe");
            }
        }
        else {
            throw new BadRequestException("El Equipo no puede ser null");
        }
    }
}
