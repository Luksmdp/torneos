package com.torneos.futbol.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@Builder
public class TorneoDto implements Serializable {

    private String nombre;
    private Date fechaInicio;
}
