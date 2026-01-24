package com.actividad.pokedex.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FormularioRequest {

    @NotBlank(message = "Introduce un nombre o un id.")
    private String nombreOId;
}
