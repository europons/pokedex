package com.actividad.pokedex.dto.response;

import com.actividad.pokedex.model.ImagenPokemon;
import com.actividad.pokedex.model.PokemonTipos;
import java.util.List;
import lombok.Data;

/**
 * Datos de un Pokemon tal y como llegan del servicio.
 * <p>
 * Se usa para leer la informacion recibida de la PokeApi.
 * </p>
 */
@Data
public class PokemonResponse {

    /**
     * Identificador del Pokemon.
     */
    private int id;

    /**
     * Nombre del Pokemon.
     */
    private String name;

    /**
     * Altura del Pokemon.
     */
    private int height;

    /**
     * Peso del Pokemon.
     */
    private int weight;

    /**
     * Tipos del Pokemon.
     */
    private List<PokemonTipos> types;

    /**
     * Imagen principal del Pokemon.
     */
    private ImagenPokemon sprites;
}
