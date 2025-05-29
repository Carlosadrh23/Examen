package bomber;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import javax.swing.JFrame;

public class Ventana extends JFrame {

    static HashMap<Integer, Integer> bufferTeclas;
    Canvas canvas;
    HiloJuego hilo;

    public Ventana() {
        this.setTitle("Bomberman");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centrar ventana
        this.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(800, 800));

        hilo = new HiloJuego(canvas);
        hilo.start();

        bufferTeclas = new HashMap<>();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("tecla:" + e.getKeyCode());
                bufferTeclas.put(e.getKeyCode(), e.getKeyCode());

                if (e.getKeyCode() == 32) { // Espacio
                    hilo.getJugador1().agregarBomba();
                }

                if (e.getKeyCode() == 82) { // R
                    hilo.getJugador2().agregarBomba();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                bufferTeclas.remove(e.getKeyCode());
            }
        });

        this.add(canvas);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.validate();
        this.repaint();
    }
}
