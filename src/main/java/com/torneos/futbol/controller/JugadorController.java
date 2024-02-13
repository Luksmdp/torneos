package com.torneos.futbol.controller;

import com.torneos.futbol.model.dto.JugadorDto;
import com.torneos.futbol.model.entity.Jugador;
import com.torneos.futbol.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class JugadorController {
    @Autowired
    private JugadorService jugadorService;

    @GetMapping("jugadores")
    public List<Jugador> findAll() {
        return jugadorService.findAll();
    }

    @GetMapping("jugadores/{id}")
    public Jugador findById(@PathVariable Integer id) {
        return jugadorService.findById(id);
    }

    @PostMapping("jugadores")
    public Jugador save(@RequestBody JugadorDto jugadorDto) {
        Jugador jugador = new Jugador();
        jugador.setNombre(jugadorDto.getNombre());
        jugador.setPosicion(jugadorDto.getPosicion());
        jugador.setEdad(jugadorDto.getEdad());
        return jugadorService.save(jugador);
    }

    @DeleteMapping("jugadores/{id}")
    public void delete(@PathVariable Integer id) {
        jugadorService.delete(jugadorService.findById(id));
    }

    @PutMapping("jugadores/{id}")
    public Jugador update(@PathVariable Integer id,@RequestBody Jugador jugador) {
        return jugadorService.update(jugador);
    }
}
