package com.torneos.futbol.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String posicion;

    private int edad;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;


}
