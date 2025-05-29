package bomber;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ExplosionBomba {

	private Field[][] matrizFields; // Matriz de casillas
	private Bomba bomba;
	private ArrayList<BufferedImage> spritesExplosion;

	public ExplosionBomba(Bomba bomba) {
		this.bomba = bomba;
		this.matrizFields = bomba.getJugador().getHiloJuego().getMatrizFields();

		this.spritesExplosion = new ArrayList<>();
		spritesExplosion.add(SpriteToBuffer.convertir("/sprites/explosion1.png"));
		spritesExplosion.add(SpriteToBuffer.convertir("/sprites/explosion2.png"));
		spritesExplosion.add(SpriteToBuffer.convertir("/sprites/explosion3.png"));
		spritesExplosion.add(SpriteToBuffer.convertir("/sprites/explosion44.png"));

		hacerExplosion();
	}

	private void hacerExplosion() {
		int x = bomba.getCasilla().getX() / 50; 
		int y = bomba.getCasilla().getY() / 50; 

		// System.out.println("aqui afecta(" + x + ", " + y + ")");

		afectarCasilla(y, x); // Invertir x e y

		afectarCasilla(y + 1, x); // Derecha
		afectarCasilla(y - 1, x); // Izquierda
		afectarCasilla(y, x + 1); // Abajo
		afectarCasilla(y, x - 1); // Arriba
		
//		afectarCasilla(y + 2, x); 
//		afectarCasilla(y - 2, x); 
//		afectarCasilla(y, x + 2); 
//		afectarCasilla(y, x - 2); 
	}

	private void afectarCasilla(int x, int y) {
		// Verifica que las coordenadas estén dentro de la matriz jugable
		if (x >= 1 && x <= 12 && y >= 1 && y <= 12) {
			Field casilla = matrizFields[x][y];
			// System.out.println("Afectando casilla en (" + x + ", " + y + ")");

			if (casilla.getTipo() != 1) {
				new HiloExplosion(casilla, spritesExplosion, 500).start();
			}

			if (bomba.getJugador().getHiloJuego().getJugador1().getParado().equals(casilla)) {
				bomba.getJugador().getHiloJuego().getJugador1().perderVida();
			}
			if (bomba.getJugador().getHiloJuego().getJugador2().getParado().equals(casilla)) {
				bomba.getJugador().getHiloJuego().getJugador2().perderVida();
			}


		}else {
			// System.out.println("fuera(" + x + ", " + y + ")");
		}
	}
}
