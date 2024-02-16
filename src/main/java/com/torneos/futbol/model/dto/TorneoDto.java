package com.torneos.futbol.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@Builder
public class TorneoDto implements Serializable {

    @NotBlank(message = "El campo 'nombre' no puede estar vacío")
    private String nombre;
    @NotNull(message = "El campo 'fechaInicio' no puede estar vacío")
    private Date fechaInicio;
}
