package main;

import gui.Controlador;
import gui.Modelo;
import gui.Vista;

/**
 * Clase Main que inicia la aplicación de MMA.
 * Esta clase crea una instancia de Modelo, Vista y Controlador para iniciar la aplicación.
 */
public class Main {

    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);
    }
}
