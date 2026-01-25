package com.actividad.pokedex.model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a un Pokemon con los datos que se muestran.
 * <p>
 * Se usa en la aplicación para presentar la información.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    /**
     * Nombre del Pokemon.
     */
    private String nombre;

    /**
     * Numero identificador.
     */
    private int id;

    /**
     * Altura en decámetros.
     */
    private Double altura;

    /**
     * Peso en decagramos.
     */
    private Double peso;

    /**
     * Lista de tipos del Pokemon.
     */
    private ArrayList<String> tipos;

    /**
     * Enlace de la imagen del Pokemon.
     */
    private String imagen;
}
