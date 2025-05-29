package bomber;

import java.awt.image.BufferedImage;

public class Bomba {
    BufferedImage sprite1;
    BufferedImage sprite2;
    Personaje jugador;
    Field casilla;
    int x;
    int y;
    boolean bandera = true;
    
    public Bomba(Personaje jugador, int x, int y) {
        this.jugador = jugador;
        this.casilla = jugador.getParado();
        this.x = this.casilla.getX();
        this.y = this.casilla.getY();
        sprite1 = SpriteToBuffer.convertir("/sprites/bomba1.png");
        sprite2 = SpriteToBuffer.convertir("/sprites/bomba2.png");
        
//        System.out.println("Bomba en (" + this.casilla.getX() + ", " + this.casilla.getY() + ")");
    }

    public BufferedImage getSprite1() {
        return bandera ? sprite1 : sprite2;
    }

    public void setSprite1(BufferedImage sprite1) {
        this.sprite1 = sprite1;
    }

    public BufferedImage getSprite2() {
        return sprite2;
    }

    public void setSprite2(BufferedImage sprite2) {
        this.sprite2 = sprite2;
    }

    public Personaje getJugador() {
        return jugador;
    }

    public void setJugador(Personaje jugador) {
        this.jugador = jugador;
    }

    public Field getCasilla() {
        return casilla;
    }

    public void setCasilla(Field casilla) {
        this.casilla = casilla;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
}
