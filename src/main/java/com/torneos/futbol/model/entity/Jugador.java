package com.torneos.futbol.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String posicion;

    private int edad;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    @JsonBackReference
    private Equipo equipo;


}
