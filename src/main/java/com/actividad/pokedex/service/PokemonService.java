package com.actividad.pokedex.service;

import com.actividad.pokedex.client.PokeApiClient;
import com.actividad.pokedex.dto.response.ListaPokeResponse;
import com.actividad.pokedex.dto.response.PokemonResponse;
import com.actividad.pokedex.model.Pokemon;
import java.util.ArrayList;
import java.util.List;

import com.actividad.pokedex.model.PokemonLista;
import org.springframework.stereotype.Service;

/**
 * Servicio que organiza y presenta la información de los Pokemon.
 * <p>
 * Pide los datos y los prepara para mostrarlos.
 * </p>
 */
@Service
public class PokemonService {

    /**
     * Constante para pasar de decímetros a metros.
     */
    private static final Double DECIMETROS_A_METROS = 0.1;

    /**
     * Constante para pasar de hectogramos a kilogramos.
     */
    private static final Double HECTOGRAMOS_A_KILOGRAMOS = 0.1;

    /**
     * Cliente que trae la información de la API PokeApi.
     */
    private final PokeApiClient clientePokeApi;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param clientePokeApi cliente que busca los datos
     */
    public PokemonService(PokeApiClient clientePokeApi) {
        this.clientePokeApi = clientePokeApi;
    }

    /**
     * Obtiene un Pokemon por nombre o id.
     *
     * @param nombreOId nombre o id del Pokemon
     * @return Pokemon con datos listos para mostrar o null si no existe
     */
    public Pokemon obtenerPokemon(String nombreOId) {
        PokemonResponse respuesta = clientePokeApi.obtenerPokemon(nombreOId);
        if (respuesta == null) {
            return null;
        }

        return mapearPokemon(respuesta);
    }

    /**
     * Obtiene un listado de los 20 primeros Pokemons.
     *
     * @return lista de Pokemon o null si no hay datos
     */
    public List<PokemonLista> obtener20Pokemons() {
        ListaPokeResponse respuesta = clientePokeApi.obtenerLista20Pokemons();
        if (respuesta == null || respuesta.getResults() == null) {
            return null;
        }

        return mapearPokemonLista(respuesta);
    }

    /**
     * Prepara un Pokemon con los datos recibidos.
     *
     * @param pokeResponse datos de entrada
     * @return Pokemon listo para usar
     */
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

    /**
     * Prepara una lista de Pokemon.
     *
     * @param pokeResponse datos de entrada
     * @return lista de Pokemon
     */
    private List<PokemonLista> mapearPokemonLista(ListaPokeResponse pokeResponse) {
        List<PokemonLista> listaPokemons = new ArrayList<>();

        if (pokeResponse.getResults() != null) {
            listaPokemons.addAll(pokeResponse.getResults());
        }
        return listaPokemons;
    }

    /**
     * Redondea un numero a dos decimales.
     *
     * @param valor numero a redondear
     * @return numero redondeado
     */
    private static Double redondear (Double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
