package bomber;

import java.awt.image.BufferedImage;

public class Field {

    int tipo = 0; // tipo 0: piso, 1: pared, 2: obstáculo
    private BufferedImage sprite;
    private int tam = 0; 
    private int x; 
    private int y; 
    private Field arriba, abajo, izquierda, derecha; 
    

    // Constructor
    public Field(int tipo) {
        this.tipo = tipo;
        switch (tipo) {
            case 0:
                sprite = SpriteToBuffer.convertir("/sprites/piso222.png");
                break;
            case 1:
                sprite = SpriteToBuffer.convertir("/sprites/pared12.png");
                break;
            case 2:
                sprite = SpriteToBuffer.convertir("/sprites/obstaculo333.png"); 
                break;
            default:
                throw new AssertionError("Tipo no válido: " + tipo);
        }
    }
    
    public void azulejo() {
    	this.tipo = tipo;
        switch (tipo) {
            case 0:
                sprite = SpriteToBuffer.convertir("/sprites/piso222.png");
                break;
            case 1:
                sprite = SpriteToBuffer.convertir("/sprites/pared12.png");
                break;
            case 2:
                sprite = SpriteToBuffer.convertir("/sprites/obstaculo333.png"); 
                break;
            default:
                throw new AssertionError("Tipo no válido: " + tipo);
        }
    }
    
    // Métodos Getter y Setter
    
    
    public int getTipo() {
        return tipo;
    }


	public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
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

    public Field getArriba() {
        return arriba;
    }

    public void setArriba(Field arriba) {
        this.arriba = arriba;
    }

    public Field getAbajo() {
        return abajo;
    }

    public void setAbajo(Field abajo) {
        this.abajo = abajo;
    }

    public Field getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Field izquierda) {
        this.izquierda = izquierda;
    }

    public Field getDerecha() {
        return derecha;
    }

    public void setDerecha(Field derecha) {
        this.derecha = derecha;
    }
}
