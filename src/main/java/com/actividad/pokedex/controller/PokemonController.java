package com.actividad.pokedex.controller;

import com.actividad.pokedex.dto.request.FormularioRequest;
import com.actividad.pokedex.model.Pokemon;
import com.actividad.pokedex.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService servicioPokemon;

    public PokemonController(PokemonService servicioPokemon) {
        this.servicioPokemon = servicioPokemon;
    }
    

    @GetMapping("/buscar")
    public String mostrarFormulario() {
        return "formulario";
    }

    @PostMapping("/buscar")
    public String buscarPokemon(@Valid @ModelAttribute("busqueda") FormularioRequest formularioRequest,
                                BindingResult errores,
                                Model model) {
        if (errores.hasErrors()) {
            return "formulario";
        }

        String nombreOId = formularioRequest.getNombreOId();

        Pokemon pokemon = servicioPokemon.obtenerPokemon(nombreOId);
        if (pokemon == null) {
            errores.rejectValue("nombreOId", "error pokemon no encontrado", "Pokémon no encontrado. Por favor, verifica el nombre o ID introducido.");
            return "formulario";
        }

        model.addAttribute("pokemon", pokemon);
        return "redirect:/pokemon/resultado";
    }

    @GetMapping("/resultado")
    public String mostrarResultado(Model model) {
        return "resultado";
    }
}
