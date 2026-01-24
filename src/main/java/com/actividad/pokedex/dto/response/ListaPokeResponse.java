package com.actividad.pokedex.dto.response;

import com.actividad.pokedex.model.PokemonLista;
import lombok.Data;

import java.util.List;

@Data
public class ListaPokeResponse {

    private List<PokemonLista> results;

}
