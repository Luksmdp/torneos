package com.torneos.futbol.model.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "torneos")
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaInicio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "torneo")
    @JsonManagedReference
    private List<Equipo> equipos;
}

