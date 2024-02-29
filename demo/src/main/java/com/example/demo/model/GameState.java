package com.example.demo.model;

import javafx.scene.shape.Rectangle;

public class GameState {
    private Player player1, player2;
    private Rectangle posicion;
    private Rectangle[][] tablero;
    private String response;
    private boolean turno;

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Rectangle getPosicion() {
        return posicion;
    }

    public void setPosicion(Rectangle posicion) {
        this.posicion = posicion;
    }

    public Rectangle[][] getTablero() {
        return tablero;
    }

    public void setTablero(Rectangle[][] tablero) {
        this.tablero = tablero;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }
    public boolean nextTurn(Player player){
        if (player.getNombre().equals(player1.getNombre())){
            turno=true;
        }else {
            turno=false;
        }
        return turno;
    }
}
