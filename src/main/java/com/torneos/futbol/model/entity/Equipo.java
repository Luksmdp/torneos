package com.torneos.futbol.model.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    @JsonBackReference
    private Torneo torneo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipo")
    @JsonManagedReference
    private List<Jugador> jugadores;

}
