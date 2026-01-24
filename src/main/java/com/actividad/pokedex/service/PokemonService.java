package com.actividad.pokedex.service;

import com.actividad.pokedex.client.PokeApiClient;
import com.actividad.pokedex.dto.response.PokeResponse;
import com.actividad.pokedex.model.Pokemon;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {

    private final PokeApiClient clientePokeApi;

    public PokemonService(PokeApiClient clientePokeApi) {
        this.clientePokeApi = clientePokeApi;
    }

    public Pokemon obtenerPokemon(String nombreOId) {
        PokeResponse respuesta = clientePokeApi.obtenerPokemon(nombreOId);
        if (respuesta == null) {
            return null;
        }

        return mapearRespuesta(respuesta);
    }

    private Pokemon mapearRespuesta(PokeResponse pokeResponse) {
        Pokemon pokemon = new Pokemon();

        pokemon.setId(pokeResponse.getId());
        pokemon.setNombre(pokeResponse.getName());
        pokemon.setAltura(pokeResponse.getHeight());
        pokemon.setPeso(pokeResponse.getWeight());
        // Mapear tipos
        ArrayList<String> tipos = new ArrayList<>();
        if (pokeResponse.getTypes() != null) {
            for (var tipo : pokeResponse.getTypes()) {
                if (tipo != null && tipo.getType() != null) {
                    tipos.add(tipo.getType().getName());
                }
            }
        }
        pokemon.setTipos(tipos);
        // Mapear imagen
        if (pokeResponse.getSprites() != null) {
            pokemon.setImagen(pokeResponse.getSprites().getFront_default());
        } else {
            pokemon.setImagen(null);
        }

        return pokemon;
    }
}
