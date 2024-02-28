package com.example.demo.model;

public class Player {
    String nombre;
    Boat[] boats = new Boat[3];

    public Player(String nombre, Boat[] boats) {
        this.nombre = nombre;
        this.boats = boats;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boat[] getBoats() {
        return boats;
    }

    public void setBoats(Boat[] boats) {
        this.boats = boats;
    }
}
