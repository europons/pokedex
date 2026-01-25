package com.actividad.pokedex.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Datos que el usuario escribe en el formulario de búsqueda.
 */
@Data
public class FormularioRequest {

    /**
     * Nombre o numero id introducido por el usuario.
     */
    @NotBlank(message = "Introduce un nombre o un id.")
    private String nombreOId;
}
