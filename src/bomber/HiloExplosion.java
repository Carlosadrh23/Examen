package bomber;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HiloExplosion extends Thread {
    private Field casilla;
    private ArrayList<BufferedImage> spritesExplosion;
    int duracion;
    int contadorSpriteEx =0;
    int i=1;

    public HiloExplosion(Field casilla, ArrayList<BufferedImage> spritesExplosion, int duracion) {
        this.casilla = casilla;
        this.spritesExplosion = spritesExplosion;
        this.duracion = duracion;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < duracion / 100; i++) {
                casilla.setSprite(spritesExplosion.get(contadorSpriteEx));
                contadorSpriteEx = (contadorSpriteEx + 1) % spritesExplosion.size();
                sleep(100);
            
            
            if (casilla.getTipo() == 2) { 
                casilla.setTipo(0); 
                casilla.setSprite(SpriteToBuffer.convertir("/sprites/piso222.png")); 
 
                
                if (Math.random() < 0.1) {
                    BufferedImage spriteHabilidad = SpriteToBuffer.convertir("/sprites/bombaExtra.png"); 
                    casilla.setSprite(spriteHabilidad);
                }
            } else {
                casilla.setSprite(SpriteToBuffer.convertir("/sprites/piso222.png"));
            }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
