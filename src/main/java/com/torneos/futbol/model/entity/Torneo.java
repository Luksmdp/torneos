package com.torneos.futbol.model.entity;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "torneos")
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Temporal(TemporalType.DATE)
    private java.util.Date fechaInicio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "torneo")
    private List<Equipo> equipos;
}

