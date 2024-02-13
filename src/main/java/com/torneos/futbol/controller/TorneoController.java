package com.torneos.futbol.controller;

import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.service.TorneoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TorneoController {


    private final TorneoService torneoService;

    @GetMapping("torneos")
    public List<Torneo> findAll() {
        return torneoService.findAll();
    }

    @GetMapping("torneos/{id}")
    public Torneo findById(@PathVariable Integer id) {
        return torneoService.findById(id);
    }

    @PostMapping("torneos")
    public Torneo save(@RequestBody Torneo torneo) {
        return torneoService.save(torneo);
    }

    @DeleteMapping("torneos/{id}")
    public void delete(@PathVariable Integer id) {
        torneoService.delete(torneoService.findById(id));
    }

    @PutMapping("torneos/{id}")
    public Torneo update(@PathVariable Integer id, @RequestBody Torneo torneo) {
        return torneoService.update(torneo);
    }

}
