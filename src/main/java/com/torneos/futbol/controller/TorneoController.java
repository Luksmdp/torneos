package com.torneos.futbol.controller;

import com.torneos.futbol.model.dto.TorneoDto;
import com.torneos.futbol.model.entity.Torneo;
import com.torneos.futbol.service.TorneoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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
    public Torneo findById(@PathVariable Integer id) throws BadRequestException {
        return torneoService.findById(id);
    }

    @PostMapping("torneos")
    public Torneo save(@RequestBody TorneoDto torneoDto) throws BadRequestException {
        return torneoService.save(torneoDto);
    }

    @DeleteMapping("torneos/{id}")
    public void delete(@PathVariable Integer id) throws BadRequestException {
        torneoService.delete(id);
    }

    @PutMapping("torneos/{id}")
    public Torneo update(@PathVariable Integer id, @RequestBody Torneo torneo) throws BadRequestException {
        return torneoService.update(torneo,id);
    }

}
