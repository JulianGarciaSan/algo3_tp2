package edu.fiuba.algo3.model.defense;

import edu.fiuba.algo3.model.Creditos;

public class TorreBlanca extends Defensa {

    public TorreBlanca(){
        this.coste = new Creditos(10);
        this.danio = 1;
        this.turnosRestantes = 1;
        this.rango = 3;
    }

}
