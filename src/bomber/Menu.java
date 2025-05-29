package bomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Menu extends JFrame implements KeyListener {
    
    private int opcionSeleccionada = 0;
    private final int TOTAL_OPCIONES = 2;
    private Timer animacionTimer;
    private boolean mostrarCursor = true;
    private BufferedImage bombaSprite;
    private Font fuenteTitulo;
    private Font fuenteOpciones;
    private Font fuenteCreditos;
    

    private int bombaFrame = 0;
    
    
    private final Color NEGRO_FONDO = new Color(0, 0, 0);              
    private final Color AMARILLO_TITULO = new Color(255, 255, 0);      
    private final Color BLANCO_OPCIONES = new Color(255, 255, 255);    
    private final Color AMARILLO_SELECCION = new Color(255, 255, 0);      
    private final Color ROJO_SOMBRA = new Color(200, 0, 0);            
    
    public Menu() {
        inicializarVentana();
        cargarRecursos();
        iniciarAnimacion();
        setVisible(true);
    }
    
    private void inicializarVentana() {
        setTitle("BOMBERMAN");
        setSize(512, 448); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(NEGRO_FONDO);
        
        setFocusable(true);
        addKeyListener(this);
    }
    
    private void cargarRecursos() {
        try {
            bombaSprite = SpriteToBuffer.convertir("/sprites/bomba1.png");
        } catch (Exception e) {
            bombaSprite = null;
        }
        
     
        fuenteTitulo = new Font("Monospaced", Font.BOLD, 48);
        fuenteOpciones = new Font("Monospaced", Font.BOLD, 20);
        fuenteCreditos = new Font("Monospaced", Font.PLAIN, 12);
    }
    
    private void iniciarAnimacion() {
       
        animacionTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCursor = !mostrarCursor;
                bombaFrame = (bombaFrame + 1) % 2;
                repaint();
            }
        });
        animacionTimer.start();
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        
      
        g2d.setColor(NEGRO_FONDO);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        dibujarTituloOriginal(g2d);
        dibujarOpcionesOriginales(g2d);
    }
    
    private void dibujarTituloOriginal(Graphics2D g2d) {
        g2d.setFont(fuenteTitulo);
        
        
        String bomberman = "BOMBERMAN";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(bomberman)) / 2;
        int y = 100;
        
        
        g2d.setColor(ROJO_SOMBRA);
        g2d.drawString(bomberman, x + 3, y + 3);
        g2d.drawString(bomberman, x + 2, y + 2);
        
       
        g2d.setColor(AMARILLO_TITULO);
        g2d.drawString(bomberman, x, y);
    }
    
    private void dibujarOpcionesOriginales(Graphics2D g2d) {
        String[] opciones = {"INICIAR", "SALIR"};
        g2d.setFont(fuenteOpciones);
        FontMetrics fm = g2d.getFontMetrics();
        
        int startY = 220;
        int spacing = 45;
        
        for (int i = 0; i < opciones.length; i++) {
            int x = (getWidth() - fm.stringWidth(opciones[i])) / 2;
            int y = startY + (i * spacing);
            
            
            if (i == opcionSeleccionada) {
                g2d.setColor(AMARILLO_SELECCION);
                
                
                if (mostrarCursor) {
                    int[] xTriangle = {x - 40, x - 25, x - 40};
                    int[] yTriangle = {y - 10, y - 5, y};
                    g2d.fillPolygon(xTriangle, yTriangle, 3);
                }
            } else {
                g2d.setColor(BLANCO_OPCIONES);
            }
            
            g2d.drawString(opciones[i], x, y);
        }
    }
    
    private void ejecutarOpcion() {
        switch (opcionSeleccionada) {
            case 0: 
                iniciarJuego();
                break;
            case 1: 
                salirJuego();
                break;
        }
    }
    
    private void iniciarJuego() {
        animacionTimer.stop();
        dispose();
        new Ventana(); 
    }
    
    private void salirJuego() {
        String[] opciones = {"SI", "NO"};
        int respuesta = JOptionPane.showOptionDialog(
            this,
            "¿Seguro que quieres salir?",
            "SALIR DEL JUEGO",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[1]
        );
        
        if (respuesta == 0) {
            animacionTimer.stop();
            System.exit(0);
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                opcionSeleccionada = (opcionSeleccionada - 1 + TOTAL_OPCIONES) % TOTAL_OPCIONES;
                repaint();
                break;
                
            case KeyEvent.VK_DOWN:
                opcionSeleccionada = (opcionSeleccionada + 1) % TOTAL_OPCIONES;
                repaint();
                break;
                
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                ejecutarOpcion();
                break;
                
            case KeyEvent.VK_ESCAPE:
                salirJuego();
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Menu());
    }
}