package com.actividad.pokedex.client;

import com.actividad.pokedex.dto.response.ListaPokeResponse;
import com.actividad.pokedex.dto.response.PokemonResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PokeApiClient {

    private static final String URL_BASE = "https://pokeapi.co/api/v2/";

    private final RestTemplate restTemplate = new RestTemplate();

    public PokemonResponse obtenerPokemon(String nombreOId) {
        String url = URL_BASE + "/pokemon/" + nombreOId.toLowerCase();
        try {
            return restTemplate.getForObject(url, PokemonResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public ListaPokeResponse obtenerLista20Pokemons() {
        String url = URL_BASE + "/pokemon?limit=20";
        try {
            return restTemplate.getForObject(url, ListaPokeResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
