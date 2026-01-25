package com.actividad.pokedex.controller;

import com.actividad.pokedex.dto.request.FormularioRequest;
import com.actividad.pokedex.model.Pokemon;
import com.actividad.pokedex.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador que atiende las páginas de la pokedex.
 * <p>
 * Recibe las acciones del usuario y prepara las vistas.
 * </p>
 */
@Controller
public class PokemonController {

    /**
     * Servicio que ofrece la información Pokemon.
     */
    private final PokemonService servicioPokemon;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param servicioPokemon servicio principal de la aplicación Pokedex
     */
    public PokemonController(PokemonService servicioPokemon) {
        this.servicioPokemon = servicioPokemon;
    }
    
    /**
     * Muestra el formulario de búsqueda.
     *
     * @param model datos para la vista
     * @return la vista del formulario
     */
    @GetMapping("/buscar")
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("busqueda")) {
            model.addAttribute("busqueda", new FormularioRequest());
        }
        return "formulario";
    }

    /**
     * Procesa la búsqueda enviada por el usuario.
     * <p>
     * Si hay errores, vuelve a mostrar el formulario.
     * </p>
     *
     * @param formularioRequest datos escritos por el usuario
     * @param errores resultado de las comprobaciones
     * @param model datos para la vista
     * @param redirectAttributes datos temporales para el siguiente salto de página
     * @return vista de destino. Si algo falla muestra formulario si no redirecciona a los detalles del Pokemon buscado
     */
    @PostMapping("/buscar")
    public String buscarPokemon(@Valid @ModelAttribute("busqueda") FormularioRequest formularioRequest,
                                BindingResult errores,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (errores.hasErrors()) {
            return "formulario";
        }

        String nombreOId = formularioRequest.getNombreOId();

        try {
            Pokemon pokemon = servicioPokemon.obtenerPokemon(nombreOId);

            if (pokemon == null) {
                // SOLO llega aquí si la API devolvió 404 (no existe)
                errores.rejectValue(
                    "nombreOId",
                    "error",
                    "Pokémon no encontrado. Por favor, verifica el nombre o ID introducido."
                );
                return "formulario";
            }

            redirectAttributes.addFlashAttribute("pokemon", pokemon);
            return "redirect:/detalles/" + nombreOId;

        } catch (org.springframework.web.client.ResourceAccessException e) {
            // Timeout / sin conexión / DNS
            errores.rejectValue(
                "nombreOId",
                "error",
                "No se puede conectar con PokeAPI ahora mismo. Inténtalo de nuevo en unos segundos."
            );
            return "formulario";

        } catch (org.springframework.web.client.HttpServerErrorException e) {
            // Errores 5xx de la API
            errores.rejectValue(
                "nombreOId",
                "error",
                "PokeAPI está teniendo problemas ahora mismo. Inténtalo más tarde."
            );
            return "formulario";

        } catch (org.springframework.web.client.RestClientException e) {
            // Cualquier otro error de cliente HTTP
            errores.rejectValue(
                "nombreOId",
                "error",
                "Error al consultar PokeAPI. Inténtalo más tarde."
            );
            return "formulario";
        }
    }

    /**
     * Muestra la ficha de un Pokemon concreto.
     * <p>
     * Si no existe, vuelve al formulario con un aviso.
     * </p>
     *
     * @param nombreOId nombre o numero del Pokemon
     * @param model datos para la vista
     * @param redirectAttributes datos temporales para el aviso
     * @return vista de detalles o vuelta al formulario
     */
    @GetMapping("/detalles/{nombreOId}")
    public String mostrarDetallesPokemon(@PathVariable("nombreOId") String nombreOId,
                                         Model model,
                                         RedirectAttributes redirectAttributes) {
        if (model.getAttribute("pokemon") == null) {
            Pokemon pokemon = servicioPokemon.obtenerPokemon(nombreOId);
            if (pokemon == null) {
                redirectAttributes.addFlashAttribute("error", "Pokémon no encontrado. Por favor, verifica el nombre o ID.");
                return "redirect:/buscar";
            }
            model.addAttribute("pokemon", pokemon);
        }
        return "detallesPokemon";
    }

    /**
     * Muestra un listado de Pokemon.
     *
     * @param model datos para la vista
     * @return nombre de la vista con el listado
     */
    @GetMapping("/listado")
    public String mostrarListadoPokemons(Model model) {

        try {
            var listaPokemons = servicioPokemon.obtener20Pokemons();
            model.addAttribute("listaPokemons", listaPokemons);
            return "listadoPokemons";

        } catch (org.springframework.web.client.ResourceAccessException e) {
            // Timeout / sin conexión / DNS
            model.addAttribute(
                "error",
                "No se puede conectar con PokeAPI ahora mismo. Inténtalo más tarde."
            );
            return "listadoPokemons";

        } catch (org.springframework.web.client.HttpServerErrorException e) {
            // Errores 5xx de la API
            model.addAttribute(
                "error",
                "PokeAPI está teniendo problemas ahora mismo. Inténtalo más tarde."
            );
            return "listadoPokemons";

        } catch (org.springframework.web.client.RestClientException e) {
            // Cualquier otro error de cliente HTTP
            model.addAttribute(
                "error",
                "Error al cargar el listado de Pokémon. Inténtalo más tarde."
            );
            return "listadoPokemons";
        }
    }

    /**
     * Muestra la página de inicio.
     *
     * @return vista inicial
     */
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
}
