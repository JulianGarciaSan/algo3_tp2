package edu.fiuba.algo3.modelo;

public class Rocoso extends Parcela{


    public boolean ubicar(Defensa defensa, Jugador jugador) {
        return false;
    }

    public boolean ubicar(Enemigo enemigo, Jugador jugador) {
        return false;
    }

    public Rocoso(Coordenadas ubicacion){
        this.ubicacion = ubicacion;
    }
    public Rocoso(){
        this.ubicacion = new Coordenadas(0,0);
    }

}
