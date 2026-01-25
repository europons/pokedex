package com.actividad.pokedex.client;

import com.actividad.pokedex.dto.response.ListaPokeResponse;
import com.actividad.pokedex.dto.response.PokemonResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente que consulta datos de Pokemon a la api externa de PokeApi.
 * <p>
 * Se encarga de pedir la información cuando se necesita.
 * </p>
 */
@Component
public class PokeApiClient {

    /**
     * Dirección base de PokeApi.
     */
    private static final String URL_BASE = "https://pokeapi.co/api/v2/";

    /**
     * RestTemplate que realiza las peticiones HTTP a la API.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Busca un Pokemon por nombre o numero.
     *
     * @param nombreOId nombre o numero escrito por el usuario
     * @return datos del Pokemon o null si no se encuentra
     */
    public PokemonResponse obtenerPokemon(String nombreOId) {
        String url = URL_BASE + "/pokemon/" + nombreOId.toLowerCase();
        try {
            return restTemplate.getForObject(url, PokemonResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    /**
     * Obtiene un listado de Pokemon.
     *
     * @return lista con los primeros 20 o null si no hay resultado
     */
    public ListaPokeResponse obtenerLista20Pokemons() {
        String url = URL_BASE + "/pokemon?limit=20";
        try {
            return restTemplate.getForObject(url, ListaPokeResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
