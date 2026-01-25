package com.actividad.pokedex.dto.response;

import com.actividad.pokedex.model.PokemonLista;
import lombok.Data;

import java.util.List;

/**
 * Respuesta con una lista de Pokemon.
 * <p>
 * Contiene los elementos que llegan de la PokeApi.
 * </p>
 */
@Data
public class ListaPokeResponse {

    /**
     * Listado de Pokemon disponibles.
     */
    private List<PokemonLista> results;

}
