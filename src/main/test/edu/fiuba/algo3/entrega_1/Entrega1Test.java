package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.model.*;
import edu.fiuba.algo3.model.defense.Defensa;
import edu.fiuba.algo3.model.defense.Tierra;
import edu.fiuba.algo3.model.defense.TorreBlanca;
import edu.fiuba.algo3.model.defense.TorrePlateada;
import edu.fiuba.algo3.model.enemy.Arania;
import edu.fiuba.algo3.model.enemy.Enemigo;
import edu.fiuba.algo3.model.enemy.Hormiga;
import edu.fiuba.algo3.model.exceptions.CreditosInsuficientesError;
import edu.fiuba.algo3.model.map.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class Entrega1Test {

    //Verificar que jugador empieza con la vida y los créditos correspondientes.
    @Test
    public void Test01UnJugadorComienzaEnUnEstadoValido() {
        Jugador jugador = new Jugador(20,100,"mati");

        assertFalse(jugador.estaMuerto());
        assertEquals(jugador.getCreditos(), 100);
    }

    //Verificar que cada defensa tarde en construirse lo que dice que
    // tarda y que recién están “operativas” cuando ya se terminaron de construir.
    @Test
    public void Test02UnaDefensaSeConstruyeEnElTiempoCorrecto() {
        Jugador jugador = new Jugador(20,100,"mati");
        Defensa torre = new TorreBlanca();

        assertFalse(torre.estaOperativa());
        torre.atacar();
        assertTrue(torre.estaOperativa());
    }

    //Verificar que se disponga de credito para realizar las construcciones.
    @Test
    public void Test03UnJugadorDebeTenerCreditosSuficientesParaConstruirUnaTorre() {
        Jugador jugador = new Jugador(20,30,"A");
        //assertDoesNotThrow(TorrePlateada::new);
        Defensa torre = new TorrePlateada();
        Parcela t1 = new Tierra(new Coordenadas(0,0));
        Parcela t2 = new Tierra(new Coordenadas(1,0));

        assertDoesNotThrow(()->t1.ubicar(torre,jugador));

        assertThrows(CreditosInsuficientesError.class, ()-> t2.ubicar(torre,jugador));

    }

    //Verificar solo se pueda construir defensas sobre tierra (y verificar lo contrario)
    @Test
    public void Test04SoloSePuedeConstruirDefensasSobreTierra() {
        Jugador jugador = new Jugador(20,100,"a");
        Defensa defensa = new TorreBlanca();

        Parcela tierra = new Tierra();
        Parcela roca = new Rocoso();
        Parcela pasarela = new Pasarela();

        assertTrue(tierra.ubicar(defensa, jugador));
        assertFalse(roca.ubicar(defensa, jugador));
        assertFalse(pasarela.ubicar(defensa, jugador));
    }

    //Verificar que las defensas ataquen dentro del rango esperado (y verificar lo contrario)
    @Test
    public void Test05LasDefensasAtacanDentroDelRangoEsperado() {
        Jugador jugador = new Jugador(20,100,"a");
        Defensa defensa = new TorreBlanca();
        Enemigo enemigo1 = new Arania();
        Enemigo enemigo2 = new Arania();

        Parcela lejos = new Pasarela(new Coordenadas(0, 5));
        Parcela cerca = new Pasarela(new Coordenadas(0,3));
        Parcela tierra = new Tierra(new Coordenadas(0,1));

        cerca.ubicar(enemigo1, jugador);
        lejos.ubicar(enemigo2, jugador);
        tierra.ubicar(defensa, jugador);

        assertTrue(tierra.defender(cerca));
        assertFalse(tierra.defender(lejos));

    }

    //Verificar que las unidades enemigas son dañadas acorde al ataque recibido.
    @Test
    public void Test06UnEnemigoRecibeElDanioCorrecto() {
        Jugador jugador = new Jugador(20,100,"a");
        Enemigo enemigo = new Arania();
        Defensa defensa = new TorrePlateada();

        enemigo.recibirDanio(defensa);
        assertTrue(enemigo.estaMuerto());

    }

    //Verificar que las unidades enemigas solo se muevan por la parcela autorizada.
    @Test
    public void Test07LasUnidadesEnemigasSoloSeMuevanPorLaParcelaAutorizada() {
        Jugador jugador = new Jugador(20,100,"a");
        Enemigo enemigo = new Arania();

        Parcela tierra = new Tierra();
        Parcela piedra = new Rocoso();
        Parcela pasarela = new Pasarela();

        assertFalse(piedra.ubicar(enemigo, jugador));
        assertFalse(tierra.ubicar(enemigo, jugador));
        assertTrue(pasarela.ubicar(enemigo, jugador));
    }


    //Verificar que al destruir una unidad enemiga, el jugador cobra el crédito que le corresponde.
    @Test
    public void Test08DestruirUnEnemigoDaLosCreditosCorrectos() {
        Jugador jugador = new Jugador(10, 1, "Julian");
        Enemigo enemigo = new Hormiga();

        jugador.recibirCreditos(enemigo.obtenerCreditos());

        assertEquals(jugador.getCreditos(), 2);
    }

    //Verificar que al pasar un turno las unidades enemigas se hayan movido según sus capacidades.
    @Test
    public void Test09PasarUnTurnoMueveEnemigoSegunCapacidad() {
        Jugador jugador = new Jugador(20, 100, "Julian");
        Enemigo hormiga = new Hormiga();
        Enemigo arania = new Arania();

        Camino camino = new Camino(new Meta(new Coordenadas(0,4)));
        Pasarela pasarela = new Pasarela(new Coordenadas(0,0));
        Pasarela siguienteHormiga = new Pasarela(new Coordenadas(0,1));
        Pasarela siguienteArania = new Pasarela(new Coordenadas(0,2));

        camino.agregarPasarela(pasarela);
        camino.agregarPasarela(siguienteHormiga);
        camino.agregarPasarela(siguienteArania);

        pasarela.ubicar(hormiga, jugador);
        pasarela.ubicar(arania, jugador);

        camino.mover(jugador);

        assertTrue(pasarela.estaVacia());

        assertFalse(siguienteHormiga.estaVacia());

        assertFalse(siguienteArania.estaVacia());
    }

    //Verificar que al eliminar todas la unidades enemigas el jugador gana el juego
    @Test
    public void Test10ElJugadorGanaEliminandoTodosLosEnemigos() {
        Jugador jugador = new Jugador(20,100,"Julian");
        Camino camino = new Camino(new Meta(new Coordenadas(0,3)));

        Pasarela c1 = new Pasarela(new Coordenadas(0,0));
        Pasarela c2 = new Pasarela(new Coordenadas(0,1));
        Pasarela c3 = new Pasarela(new Coordenadas(0,2));
        Parcela t1 = new Tierra(new Coordenadas (0,0));
        
        camino.agregarPasarela(c1);
        camino.agregarPasarela(c2);
        camino.agregarPasarela(c3);

        Enemigo enemigo = new Hormiga();
        Defensa defensa = new TorreBlanca();
        c1.ubicar(enemigo, jugador);
        t1.ubicar(defensa, jugador);
        c1.atacado(defensa,t1);

        assertTrue(camino.gano(jugador));


    }


  //  Verificar que sin eliminar todas la unidades enemigas, pero las pocas que
  // llegaron a la meta no alcanzan para matar al jugador, este también gana el juego.
    @Test
    public void Test11ElJugadorGanaEliminandoTodosLosEnemigosAunqueAlgunosLleguenALaMeta() {
        Jugador jugador = new Jugador(20,100,"Julian");
        Camino camino = new Camino(new Meta(new Coordenadas(0,3)));

        Pasarela c1 = new Pasarela(new Coordenadas(0,0));
        Pasarela c2 = new Pasarela(new Coordenadas(0,1));
        Pasarela c3 = new Pasarela(new Coordenadas(0,2));

        camino.agregarPasarela(c1);
        camino.agregarPasarela(c2);
        camino.agregarPasarela(c3);

        Enemigo enemigo = new Hormiga();
        c3.ubicar(enemigo, jugador);

        camino.mover( jugador);

        assertEquals(19,jugador.vida());
        assertTrue(camino.gano(jugador));

    }

    //Verificar que si las unidades enemigas llegadas a la meta matan al jugador, este pierde el juego
    @Test
    public void Test12ElJugadorPierdeSiLosEnemigosQueLlegaronALaMetaYLoMatan() {
        Jugador jugador = new Jugador(1,100,"Julian");
        Camino camino = new Camino(new Meta(new Coordenadas(0,3)));

        Pasarela c1 = new Pasarela(new Coordenadas(0,0));
        Pasarela c2 = new Pasarela(new Coordenadas(0,1));
        Pasarela c3 = new Pasarela(new Coordenadas(0,2));

        camino.agregarPasarela(c1);
        camino.agregarPasarela(c2);
        camino.agregarPasarela(c3);

        Enemigo enemigo = new Hormiga();
        c3.ubicar(enemigo, jugador);

        camino.mover( jugador);

        assertTrue(jugador.estaMuerto());
        assertFalse(camino.gano(jugador));
        assertTrue(camino.perdio(jugador));
    }
}




