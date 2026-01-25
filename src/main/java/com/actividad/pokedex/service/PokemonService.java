package com.actividad.pokedex.service;

import com.actividad.pokedex.client.PokeApiClient;
import com.actividad.pokedex.dto.response.ListaPokeResponse;
import com.actividad.pokedex.dto.response.PokemonResponse;
import com.actividad.pokedex.model.Pokemon;
import java.util.ArrayList;
import java.util.List;

import com.actividad.pokedex.model.PokemonLista;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {

    private static final Double DECIMETROS_A_METROS = 0.1;
    private static final Double HECTOGRAMOS_A_KILOGRAMOS = 0.1;

    private final PokeApiClient clientePokeApi;

    public PokemonService(PokeApiClient clientePokeApi) {
        this.clientePokeApi = clientePokeApi;
    }

    public Pokemon obtenerPokemon(String nombreOId) {
        PokemonResponse respuesta = clientePokeApi.obtenerPokemon(nombreOId);
        if (respuesta == null) {
            return null;
        }

        return mapearPokemon(respuesta);
    }

    public List<PokemonLista> obtener20Pokemons() {
        ListaPokeResponse respuesta = clientePokeApi.obtenerLista20Pokemons();
        if (respuesta == null || respuesta.getResults() == null) {
            return null;
        }

        return mapearPokemonLista(respuesta);
    }

    private Pokemon mapearPokemon(PokemonResponse pokeResponse) {
        Pokemon pokemon = new Pokemon();

        pokemon.setId(pokeResponse.getId());
        pokemon.setNombre(pokeResponse.getName());
        pokemon.setAltura(redondear(pokeResponse.getHeight() * DECIMETROS_A_METROS));
        pokemon.setPeso(redondear(pokeResponse.getWeight() * HECTOGRAMOS_A_KILOGRAMOS));
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

    private List<PokemonLista> mapearPokemonLista(ListaPokeResponse pokeResponse) {
        List<PokemonLista> listaPokemons = new ArrayList<>();

        if (pokeResponse.getResults() != null) {
            listaPokemons.addAll(pokeResponse.getResults());
        }
        return listaPokemons;
    }

    private static Double redondear (Double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
