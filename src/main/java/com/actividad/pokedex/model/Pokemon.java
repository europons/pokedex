package com.actividad.pokedex.model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    private String nombre;
    private int id;
    private Double altura;
    private Double peso;
    private ArrayList<String> tipos;
    private String imagen;
}
