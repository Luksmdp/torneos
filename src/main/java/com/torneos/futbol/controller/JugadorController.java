package com.torneos.futbol.controller;

import com.torneos.futbol.exception.BadRequestException;
import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.service.JugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class JugadorController {

    private final JugadorService jugadorService;

    @GetMapping("jugadores")
    public List<Jugador> findAll() {
        return jugadorService.findAll();
    }

    @GetMapping("jugadores/{id}")
    public Jugador findById(@PathVariable Integer id) {
        return jugadorService.findById(id);
    }

    @PostMapping("jugadores")
    public Jugador save(@Valid @RequestBody JugadorDto jugadorDto) {
        return jugadorService.save(jugadorDto);
    }

    @DeleteMapping("jugadores/{id}")
    public void delete(@PathVariable Integer id) throws BadRequestException{
        jugadorService.delete(id);
    }

    @PutMapping("jugadores/{id}")
    public Jugador update(@PathVariable Integer id,@Valid @RequestBody JugadorDto jugadorDto) {
        return jugadorService.update(jugadorDto,id);
    }
}
