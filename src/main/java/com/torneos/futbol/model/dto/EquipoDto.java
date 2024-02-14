package com.torneos.futbol.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EquipoDto implements Serializable {

    private String nombre;
    private Integer torneoId;
}
