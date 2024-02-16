package com.torneos.futbol.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EquipoDto implements Serializable {
    @NotBlank(message = "El campo 'nombre' no puede estar vacío")
    private String nombre;
    private Integer torneoId;
}
