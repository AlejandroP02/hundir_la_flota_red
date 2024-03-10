package com.example.demo.model;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Jugada implements Serializable {
    private String player;
    private Rectangle[][] tablero;

    public Jugada(String player, Rectangle[][] tablero) {
        this.player = player;
        this.tablero = tablero;
    }

    public String getPlayer() {
        return player;
    }

    public Rectangle[][] getTablero() {
        return tablero;
    }
}
