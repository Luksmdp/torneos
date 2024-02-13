package com.torneos.futbol.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
public class JugadorDto implements Serializable {
    private String nombre;
    private String posicion;
    private int edad;
}
