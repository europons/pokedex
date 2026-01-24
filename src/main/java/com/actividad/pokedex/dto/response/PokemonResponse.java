package com.actividad.pokedex.dto.response;

import com.actividad.pokedex.model.ImagenPokemon;
import com.actividad.pokedex.model.PokemonTipos;
import java.util.List;
import lombok.Data;

@Data
public class PokemonResponse {

    private int id;
    private String name;
    private int height;
    private int weight;
    private List<PokemonTipos> types;
    private ImagenPokemon sprites;
}
