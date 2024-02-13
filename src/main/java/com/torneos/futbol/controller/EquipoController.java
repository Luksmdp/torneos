package com.torneos.futbol.controller;

import com.torneos.futbol.model.entity.Equipo;
import com.torneos.futbol.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @GetMapping("equipos")
    public List<Equipo> findAll() {
        return equipoService.findAll();
    }

    @GetMapping("equipos/{id}")
    public Equipo findById(@PathVariable Integer id) {
        return equipoService.findById(id);
    }

    @PostMapping("equipos")
    public Equipo save(@RequestBody Equipo equipo) {
        return equipoService.save(equipo);
    }

    @DeleteMapping("equipos/{id}")
    public void delete(@PathVariable Integer id) {
        equipoService.delete(equipoService.findById(id));
    }

    @PutMapping("equipos/{id}")
    public Equipo update(@PathVariable Integer id, @RequestBody Equipo equipo) {
        return equipoService.update(equipo);
    }
}
