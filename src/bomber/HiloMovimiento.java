package bomber;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloMovimiento extends Thread {

    Personaje jugador;
    Field nuevaPoss;

    public HiloMovimiento(Personaje jugador, Field nuevaPoss) {
        this.jugador = jugador;
        this.nuevaPoss = nuevaPoss;
    }

    public void actualizaPoss() {
        jugador.setParado(nuevaPoss);
    }

    @Override
    public void run() {
        jugador.setEnMovimiento(true);
        try {
            // Movimiento en el eje X
            if (jugador.getY() == nuevaPoss.getY()) {
                while (jugador.getX() < nuevaPoss.getX()) {
                    jugador.setX(jugador.getX() + 1);
                    sleep(10);
                }
                while (jugador.getX() > nuevaPoss.getX()) {
                    jugador.setX(jugador.getX() - 1);
                    sleep(10);
                }
            }

            // Movimiento en el eje Y
            if (jugador.getX() == nuevaPoss.getX()) {
                while (jugador.getY() < nuevaPoss.getY()) {
                    jugador.setY(jugador.getY() + 1);
                    sleep(10);
                }
                while (jugador.getY() > nuevaPoss.getY()) {
                    jugador.setY(jugador.getY() - 1);
                    sleep(10);
                }
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
     
        actualizaPoss();
        jugador.setEnMovimiento(false);
    }

}
