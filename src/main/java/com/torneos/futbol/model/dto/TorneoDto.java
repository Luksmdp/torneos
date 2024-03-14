package com.torneos.futbol.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
@Builder

public class TorneoDto implements Serializable {

    @NotBlank(message = "El campo 'nombre' no puede estar vacío")
    @JsonProperty
    private String nombre;
    @JsonProperty
    @NotNull(message = "El campo 'fechaInicio' no puede estar vacío")
    private Timestamp fechaInicio;
}
