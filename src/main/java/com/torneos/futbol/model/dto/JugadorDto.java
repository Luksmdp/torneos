package com.torneos.futbol.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
@Data
@Builder
public class JugadorDto implements Serializable {
    @NotBlank(message = "El campo 'nombre' no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El campo 'posicion' no puede estar vacío")
    private String posicion;
    @NotNull(message = "El campo 'edad' no puede estar vacío")
    @Range(min = 1)
    private Integer edad;
    private Integer equipoId;
}
