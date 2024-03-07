package com.example.demo.model;

public class Boat {
    boolean tres;
    boolean horizontal;

    public Boat(boolean tres, boolean horizontal) {
        this.tres = tres;
        this.horizontal = horizontal;
    }

    public boolean isTres() {
        return tres;
    }

    public void setTres(boolean tres) {this.tres = tres;}

    public boolean isHorizontal() {return horizontal;}

    public void setHorizontal(boolean horizontal) {this.horizontal = horizontal;}
}
