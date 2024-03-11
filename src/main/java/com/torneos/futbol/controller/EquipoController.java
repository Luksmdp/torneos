package com.torneos.futbol.controller;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.EquipoDto;
import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.service.EquipoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class EquipoController {

    private final EquipoService equipoService;

    @GetMapping("equipos")
    public List<Equipo> findAll() {
        return equipoService.findAll();
    }

    @GetMapping("equipos/{id}")
    public Equipo findById(@PathVariable Integer id) throws BadRequestException {
        return equipoService.findById(id);
    }

    @PostMapping("equipos")
    public Equipo save(@Valid @RequestBody EquipoDto equipoDto) throws BadRequestException{
        return equipoService.save(equipoDto);
    }

    @DeleteMapping("equipos/{id}")
    public void delete(@PathVariable Integer id) throws BadRequestException{
        equipoService.delete(id);
    }

    @PutMapping("equipos/{id}")
    public Equipo update(@PathVariable Integer id,@Valid @RequestBody EquipoDto equipoDto) throws BadRequestException{
        return equipoService.update(equipoDto,id);
    }
}
