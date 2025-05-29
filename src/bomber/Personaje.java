package bomber;

// Importar clases necesarias
import java.util.HashMap;

import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Personaje {
	int canitadDeBombas = 2;
	int velociad = 100;
	private int vidas;
	int nivelBomba = 1;
	int x;
	int y;
	boolean enMovimiento = false;
	int estadoSprite = 0;
	int estadoSprite2 = 0;
	public String direccion;

	Field parado;
	HashMap<String, BufferedImage> bancoSprites;
	BufferedImage sprite;
	ArrayList<Bomba> listaBombas;

	HiloJuego hiloJuego;

	public Personaje(Field parado, HiloJuego hiloJuego) {
		this.vidas = 3;
		this.parado = parado;
		this.hiloJuego = hiloJuego;
		bancoSprites = new HashMap<>();
		listaBombas = new ArrayList<>();
		this.direccion = "parado";
		this.enMovimiento = false;
		this.estadoSprite = 0;

		bancoSprites.put("parado", SpriteToBuffer.convertir("/sprites/parado.png"));
		bancoSprites.put("paradoB", SpriteToBuffer.convertir("/sprites/paradoB.png"));

		bancoSprites.put("paradoDerecha", SpriteToBuffer.convertir("/sprites/paradoDerecha.png"));
		bancoSprites.put("paradoDerechaB", SpriteToBuffer.convertir("/sprites/paradoDerechaB.png"));
		bancoSprites.put("caminandoDerecha1", SpriteToBuffer.convertir("/sprites/caminandoDerecha1.png"));
		bancoSprites.put("caminandoDerecha1B", SpriteToBuffer.convertir("/sprites/caminandoDerecha1B.png"));
		bancoSprites.put("caminandoDerecha2", SpriteToBuffer.convertir("/sprites/caminandoDerecha2.png"));
		bancoSprites.put("caminandoDerecha2B", SpriteToBuffer.convertir("/sprites/caminandoDerecha2B.png"));

		bancoSprites.put("paradoIzquierda", SpriteToBuffer.convertir("/sprites/paradoIzquierda.png"));
		bancoSprites.put("paradoIzquierdaB", SpriteToBuffer.convertir("/sprites/paradoIzquierdaB.png"));
		bancoSprites.put("caminandoIzquierda1", SpriteToBuffer.convertir("/sprites/caminandoIzquierda1.png"));
		bancoSprites.put("caminandoIzquierda1B", SpriteToBuffer.convertir("/sprites/caminandoIzquierda1B.png"));
		bancoSprites.put("caminandoIzquierda2", SpriteToBuffer.convertir("/sprites/caminandoIzquierda2.png"));
		bancoSprites.put("caminandoIzquierda2B", SpriteToBuffer.convertir("/sprites/caminandoIzquierda2B.png"));

		bancoSprites.put("paradoArriba", SpriteToBuffer.convertir("/sprites/paradoArriba.png"));
		bancoSprites.put("paradoArribaB", SpriteToBuffer.convertir("/sprites/paradoArribaB.png"));
		bancoSprites.put("caminandoArriba1", SpriteToBuffer.convertir("/sprites/caminandoArriba1.png"));
		bancoSprites.put("caminandoArriba1B", SpriteToBuffer.convertir("/sprites/caminandoArriba1B.png"));
		bancoSprites.put("caminandoArriba2", SpriteToBuffer.convertir("/sprites/caminandoArriba2.png"));
		bancoSprites.put("caminandoArriba2B", SpriteToBuffer.convertir("/sprites/caminandoArriba2B.png"));

		bancoSprites.put("paradoAbajo", SpriteToBuffer.convertir("/sprites/parado.png"));
		bancoSprites.put("paradoAbajoB", SpriteToBuffer.convertir("/sprites/paradoB.png"));
		bancoSprites.put("caminandoAbajo1", SpriteToBuffer.convertir("/sprites/caminandoAbajo1.png"));
		bancoSprites.put("caminandoAbajo1B", SpriteToBuffer.convertir("/sprites/caminandoAbajo1B.png"));
		bancoSprites.put("caminandoAbajo2", SpriteToBuffer.convertir("/sprites/caminandoAbajo2.png"));
		bancoSprites.put("caminandoAbajo2B", SpriteToBuffer.convertir("/sprites/caminandoAbajo2B.png"));

		x = this.parado.getX();
		y = this.parado.getY();
	}


	public void agregarBomba() {
		boolean bombaExistente = false;
		for (Bomba b : listaBombas) {
			if (b.getX() == x && b.getY() == y) {
				bombaExistente = true;
				break;
			}
		}

		if (!bombaExistente && listaBombas.size() < canitadDeBombas) {
			Bomba b = new Bomba(this, x, y);
			listaBombas.add(b);

			HiloBomba hiloBomba = new HiloBomba(b);
			hiloBomba.start();
		}
	}

	public void perderVida() {
		this.vidas--;
		if (vidas <= 0) {
			System.out.println("El personaje ha muerto");
			String mensaje = "El personaje ha muerto. ¿Quieres reiniciar el juego o salir?";
			int opcion = JOptionPane.showOptionDialog(null, mensaje, "Fin del Juego", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Reiniciar", "Salir" }, "Reiniciar");
			if (opcion == JOptionPane.YES_OPTION) {
				hiloJuego.reiniciarJuego();
			} else {
				System.exit(0);
			}
		}
	}
	
	public void reiniciar() {
		vidas = 3;
		canitadDeBombas = 2;
		direccion = "parado";
		enMovimiento = false;
		estadoSprite = 0;
		estadoSprite2 = 0;
		listaBombas.clear();
	}

	public HiloJuego getHiloJuego() {
		return hiloJuego;
	}

	public void setHiloJuego(HiloJuego hiloJuego) {
		this.hiloJuego = hiloJuego;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		if (direccion != null) {
			this.direccion = direccion;
		}
	}

	public int getCanitadDeBombas() {
		return canitadDeBombas;
	}

	public void setCanitadDeBombas(int canitadDeBombas) {
		this.canitadDeBombas = canitadDeBombas;
	}

	public int getVelociad() {
		return velociad;
	}

	public void setVelociad(int velociad) {
		this.velociad = velociad;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public int getNivelBomba() {
		return nivelBomba;
	}

	public void setNivelBomba(int nivelBomba) {
		this.nivelBomba = nivelBomba;
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

	public Field getParado() {
		return parado;
	}

	public void setParado(Field parado) {
		this.parado = parado;
		x = this.parado.getX();
		y = this.parado.getY();
	}

	public HashMap<String, BufferedImage> getBancoSprites() {
		return bancoSprites;
	}

	public void setBancoSprites(HashMap<String, BufferedImage> bancoSprites) {
		this.bancoSprites = bancoSprites;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public boolean isEnMovimiento() {
		return enMovimiento;
	}

	public void setEnMovimiento(boolean enMovimiento) {
		this.enMovimiento = enMovimiento;
	}

	public ArrayList<Bomba> getListaBombas() {
		return listaBombas;
	}

	public void setListaBombas(ArrayList<Bomba> listaBombas) {
		this.listaBombas = listaBombas;
	}

}
