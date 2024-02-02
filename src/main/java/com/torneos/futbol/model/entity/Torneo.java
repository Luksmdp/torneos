package com.torneos.futbol.model.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
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

