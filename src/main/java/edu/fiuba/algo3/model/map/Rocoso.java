package edu.fiuba.algo3.model.map;

import edu.fiuba.algo3.model.defense.Defensa;
import edu.fiuba.algo3.model.Jugador;
import edu.fiuba.algo3.model.enemy.Enemigo;

public class Rocoso extends Parcela{


    public boolean ubicar(Defensa defensa, Jugador jugador) {
        return false;
    }

    public boolean ubicar(Enemigo enemigo, Jugador jugador) {
        return false;
    }

    //TODO: funcion en rocoso que no se utiliza en Rocoso
    public Rocoso(Coordenadas ubicacion){
        this.ubicacion = ubicacion;
    }
    public Rocoso(){
        this.ubicacion = new Coordenadas(0,0);
    }

}
