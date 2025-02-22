package edu.fiuba.algo3.model.enemy;

import edu.fiuba.algo3.model.Creditos;
import edu.fiuba.algo3.model.defense.Defensa;
import edu.fiuba.algo3.model.Jugador;
import edu.fiuba.algo3.model.Vida;

public abstract class Enemigo {
    protected Vida vida;
    protected int danio;
    protected int velocidad;

    protected Creditos creditos;

    public void recibirDanio(Defensa defensa){
        this.vida.quitar(defensa.danioGenerado());
    }

    public boolean estaMuerto(){return this.vida.estaMuerto();}

    public int getVelocidad() {return velocidad;}

    public void atacar(Jugador jugador){
        jugador.recibirDanio(this.danio);
    }
    public Creditos obtenerCreditos(){
        return creditos;
    }
}
