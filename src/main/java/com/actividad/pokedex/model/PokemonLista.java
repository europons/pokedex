package com.actividad.pokedex.model;

import lombok.Data;

/**
 * Clase que representa los atributos de un Pokemon dentro de un listado.
 */
@Data
public class PokemonLista {

    /**
     * Nombre del Pokemon.
     */
    private String name;

    /**
     * Enlace con mas datos.
     */
    private String url;

}
