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

@Controller
public class PokemonController {

    private final PokemonService servicioPokemon;

    public PokemonController(PokemonService servicioPokemon) {
        this.servicioPokemon = servicioPokemon;
    }
    

    @GetMapping("/buscar")
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("busqueda")) {
            model.addAttribute("busqueda", new FormularioRequest());
        }
        return "formulario";
    }

    @PostMapping("/buscar")
    public String buscarPokemon(@Valid @ModelAttribute("busqueda") FormularioRequest formularioRequest,
                                BindingResult errores,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (errores.hasErrors()) {
            return "formulario";
        }

        String nombreOId = formularioRequest.getNombreOId();

        Pokemon pokemon = servicioPokemon.obtenerPokemon(nombreOId);
        if (pokemon == null) {
            errores.rejectValue("nombreOId", "error", "Pokémon no encontrado. Por favor, verifica el nombre o ID introducido.");
            return "formulario";
        }

        redirectAttributes.addFlashAttribute("pokemon", pokemon);
        return "redirect:/detalles/" + nombreOId;
    }

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

    @GetMapping("/listado")
    public String mostrarListadoPokemons(Model model) {
        var listaPokemons = servicioPokemon.obtener20Pokemons();
        model.addAttribute("listaPokemons", listaPokemons);
        return "listadoPokemons";
    }

    @GetMapping("/")
    public String inicio() {
        return "index";
    }
}
