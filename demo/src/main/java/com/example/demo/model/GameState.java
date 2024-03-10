package com.example.demo.model;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class GameState implements Serializable {
    public static final long serialVersionUID = 1L;
    private String player1, player2;
    private String[][] tablero1;
    private String[][] tablero2;
    private boolean turno;

    public GameState() {
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String[][] getTablero1() {
        return tablero1;
    }

    public void setTablero1(String[][] tablero) {
        tablero1 = tablero;
    }

    public String[][] getTablero2() {
        return tablero2;
    }

    public void setTablero2(String[][] tablero) {
        tablero2 = tablero;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }
    public boolean nextTurn(String player){
        // Metodo a editar para realizar los turnos.
        if (player.equals(player1)){
            turno=true;
        }else {
            turno=false;
        }
        return turno;
    }
}
