package com.example.demo.model;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Jugada implements Serializable {

    private String player;
    private String[][] tablero;
    private String[][] tableroDelJugador;

    public Jugada(String player, String[][] tablero, String[][] tableroDelJugador) {
        this.player = player;
        this.tablero = tablero;
        this.tableroDelJugador = tableroDelJugador;
    }

    public String getPlayer() {
        return player;
    }

    public String[][] getTablero() {
        return tablero;
    }

    public String[][] getTableroDelJugador() {
        return tableroDelJugador;
    }
}
