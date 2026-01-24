package com.actividad.pokedex.client;

import com.actividad.pokedex.dto.response.PokeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PokeApiClient {

    private static final String URL_BASE = "https://pokeapi.co/api/v2/pokemon/";

    private final RestTemplate restTemplate = new RestTemplate();

    public PokeResponse obtenerPokemon(String nombreOId) {
        String url = URL_BASE + nombreOId.toLowerCase();
        try {
            return restTemplate.getForObject(url, PokeResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
