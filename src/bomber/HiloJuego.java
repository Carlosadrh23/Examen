package bomber;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloJuego extends Thread {

	Canvas canvas;
	BufferStrategy bufferDibujado;
	BufferStrategy bufferDibujadoFondo;
	Graphics lapiz;

	int x = 0;
	boolean derecha = true;
	long contadorControl = 0;
	boolean bandera = true;

	Personaje jugador1;
	Personaje jugador2;

	int[][] matrizDibujo = { 
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0, 1 }, 
			{ 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1 },
			{ 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0, 1 }, 
			{ 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1 },
			{ 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0, 1 },
			{ 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1 },
			{ 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1 }, 
			{ 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{ 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0, 1 },
			{ 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	Field[][] matrizFields = new Field[13][13];

	// BufferedImage spriteBomberman;
	BufferedImage spriteFondo;

	public HiloJuego(Canvas canvas) {
		this.canvas = canvas;
		
		spriteFondo = SpriteToBuffer.convertir("/sprites/fondo.jpg");
		crearMatriz();
		asignarVecinas();

		jugador1 = new Personaje(matrizFields[1][1], this);
		jugador2 = new Personaje(matrizFields[11][11], this);

	}

	public void asignarVecinas() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				Field recorrido = matrizFields[i][j];
				try {
					recorrido.setArriba(matrizFields[i - 1][j]);
				} catch (IndexOutOfBoundsException ex) {
				}
				try {
					recorrido.setAbajo(matrizFields[i + 1][j]);
				} catch (IndexOutOfBoundsException ex) {
				}
				try {
					recorrido.setDerecha(matrizFields[i][j + 1]);
				} catch (IndexOutOfBoundsException ex) {
				}
				try {
					recorrido.setIzquierda(matrizFields[i][j - 1]);
				} catch (IndexOutOfBoundsException ex) {
				}

			}
		}
	}

	public int[][] matrizDibujoOriginal; 

	public void crearMatriz() {
	    matrizDibujoOriginal = new int[13][13]; 

	    for (int i = 0; i < 13; i++) {
	        for (int j = 0; j < 13; j++) {
	            matrizDibujoOriginal[i][j] = matrizDibujo[i][j];  

	            matrizFields[i][j] = new Field(matrizDibujo[i][j]); 
	            matrizFields[i][j].setTam(50);
	            matrizFields[i][j].setX(j * 50);
	            matrizFields[i][j].setY(i * 50);
	        }
	    }
	}
	
	
    
	public void reiniciarJuego() {
	    jugador1.reiniciar();
	    jugador2.reiniciar();
	    jugador1 = new Personaje(matrizFields[1][1], this);
		jugador2 = new Personaje(matrizFields[11][11], this);
	    for (int i = 0; i < 13; i++) {
	        for (int j = 0; j < 13; j++) {
	            matrizFields[i][j].setTipo(matrizDibujoOriginal[i][j]);
	            matrizFields[i][j].azulejo();
	        }
	    }
	    dibuja();
	}

	public void actualiza() {
		contadorControl++;
		Field posJugador1 = jugador1.getParado();
		Field posjugador2 = jugador2.getParado();

		if (jugador1.isEnMovimiento()) {
			if (jugador1.getDireccion() != null) {
				switch (jugador1.getDireccion()) {
				case "derecha":
					if (jugador1.estadoSprite == 0) {
						jugador1.setSprite(jugador1.getBancoSprites().get("paradoDerecha"));
					} else if (jugador1.estadoSprite == 1) {
						jugador1.setSprite(jugador1.getBancoSprites().get("caminandoDerecha1"));
					} else if (jugador1.estadoSprite == 2) {
						jugador1.setSprite(jugador1.getBancoSprites().get("paradoDerecha"));
					} else if (jugador1.estadoSprite == 3) {
						jugador1.setSprite(jugador1.getBancoSprites().get("caminandoDerecha2"));
					}
					// Avanza al siguiente estado
					jugador1.estadoSprite = (jugador1.estadoSprite + 1) % 4;
					break;

				case "izquierda":
					if (jugador1.estadoSprite == 0) {
						jugador1.setSprite(jugador1.getBancoSprites().get("paradoIzquierda"));
					} else if (jugador1.estadoSprite == 1) {
						jugador1.setSprite(jugador1.getBancoSprites().get("caminandoIzquierda1"));
					} else if (jugador1.estadoSprite == 2) {
						jugador1.setSprite(jugador1.getBancoSprites().get("paradoIzquierda"));
					} else if (jugador1.estadoSprite == 3) {
						jugador1.setSprite(jugador1.getBancoSprites().get("caminandoIzquierda2"));
					}
					// Avanza al siguiente estado
					jugador1.estadoSprite = (jugador1.estadoSprite + 1) % 4;
					break;

				case "arriba":
					if (jugador1.estadoSprite == 0) {
						jugador1.setSprite(jugador1.getBancoSprites().get("paradoArriba"));
					} else if (jugador1.estadoSprite == 1) {
						jugador1.setSprite(jugador1.getBancoSprites().get("caminandoArriba1"));
					} else if (jugador1.estadoSprite == 2) {
						jugador1.setSprite(jugador1.getBancoSprites().get("paradoArriba"));
					} else if (jugador1.estadoSprite == 3) {
						jugador1.setSprite(jugador1.getBancoSprites().get("caminandoArriba2"));
					}
					// Avanza al siguiente estado
					jugador1.estadoSprite = (jugador1.estadoSprite + 1) % 4;
					break;

				case "abajo":
					if (jugador1.estadoSprite == 0) {
						jugador1.setSprite(jugador1.getBancoSprites().get("paradoAbajo"));
					} else if (jugador1.estadoSprite == 1) {
						jugador1.setSprite(jugador1.getBancoSprites().get("caminandoAbajo1"));
					} else if (jugador1.estadoSprite == 2) {
						jugador1.setSprite(jugador1.getBancoSprites().get("paradoAbajo"));
					} else if (jugador1.estadoSprite == 3) {
						jugador1.setSprite(jugador1.getBancoSprites().get("caminandoAbajo2"));
					}
					// Avanza al siguiente estado
					jugador1.estadoSprite = (jugador1.estadoSprite + 1) % 4;
					break;

				default:
					// Si la dirección es diferente, coloca al jugador como parado
					jugador1.setDireccion("parado");
					break;
				}
			}
		} else {
			jugador1.estadoSprite = 0;
			if (jugador1.getDireccion() != null) {
				switch (jugador1.getDireccion()) {
				case "parado":
					jugador1.setSprite(jugador1.getBancoSprites().get("parado")); 
					break;
				}
			}
		}
		// Movimiento hacia la derecha
		if (Ventana.bufferTeclas.containsKey(39) && !jugador1.isEnMovimiento()) {
			if (posJugador1.getDerecha() != null && posJugador1.getDerecha().getTipo() == 0) {
				jugador1.setDireccion("derecha"); // Actualiza la dirección
				HiloMovimiento hm = new HiloMovimiento(jugador1, posJugador1.getDerecha());
				hm.start();
			}
		}
		// Movimiento hacia la izquierda
		else if (Ventana.bufferTeclas.containsKey(37) && !jugador1.isEnMovimiento()) {
			if (posJugador1.getIzquierda() != null && posJugador1.getIzquierda().getTipo() == 0) {
				jugador1.setDireccion("izquierda"); // Actualiza la dirección
				HiloMovimiento hm = new HiloMovimiento(jugador1, posJugador1.getIzquierda());
				hm.start();
			}
		}
		// Movimiento hacia abajo
		else if (Ventana.bufferTeclas.containsKey(40) && !jugador1.isEnMovimiento()) {
			if (posJugador1.getAbajo() != null && posJugador1.getAbajo().getTipo() == 0) {
				jugador1.setDireccion("abajo");
				HiloMovimiento hm = new HiloMovimiento(jugador1, posJugador1.getAbajo());
				hm.start();
			}
		}
		// Movimiento hacia arriba
		else if (Ventana.bufferTeclas.containsKey(38) && !jugador1.isEnMovimiento()) {
			if (posJugador1.getArriba() != null && posJugador1.getArriba().getTipo() == 0) {
				jugador1.setDireccion("arriba");
				HiloMovimiento hm = new HiloMovimiento(jugador1, posJugador1.getArriba());
				hm.start();
			}
		}

	
		////////////////////////////////////////////// 7777
		//////////////////////////////////////////////////
		////////////////////////////////////////////// 7777
		if (jugador2.isEnMovimiento()) {
			if (jugador2.getDireccion() != null) {
				switch (jugador2.getDireccion()) {
				case "derecha":
					if (jugador2.estadoSprite2 == 0) {
						jugador2.setSprite(jugador2.getBancoSprites().get("paradoDerechaB"));
					} else if (jugador2.estadoSprite2 == 1) {
						jugador2.setSprite(jugador2.getBancoSprites().get("caminandoDerecha1B"));
					} else if (jugador2.estadoSprite2 == 2) {
						jugador2.setSprite(jugador2.getBancoSprites().get("paradoDerechaB"));
					} else if (jugador2.estadoSprite2 == 3) {
						jugador2.setSprite(jugador2.getBancoSprites().get("caminandoDerecha2B"));
					}
					// Avanza al siguiente estado
					jugador2.estadoSprite2 = (jugador2.estadoSprite2 + 1) % 4;
					break;

				case "izquierda":
					if (jugador2.estadoSprite2 == 0) {
						jugador2.setSprite(jugador2.getBancoSprites().get("paradoIzquierdaB"));
					} else if (jugador2.estadoSprite2 == 1) {
						jugador2.setSprite(jugador2.getBancoSprites().get("caminandoIzquierda1B"));
					} else if (jugador2.estadoSprite2 == 2) {
						jugador2.setSprite(jugador2.getBancoSprites().get("paradoIzquierdaB"));
					} else if (jugador2.estadoSprite2 == 3) {
						jugador2.setSprite(jugador2.getBancoSprites().get("caminandoIzquierda2B"));
					}
					// Avanza al siguiente estado
					jugador2.estadoSprite2 = (jugador2.estadoSprite2 + 1) % 4;
					break;

				case "arriba":
					if (jugador2.estadoSprite2 == 0) {
						jugador2.setSprite(jugador2.getBancoSprites().get("paradoArribaB"));
					} else if (jugador2.estadoSprite2 == 1) {
						jugador2.setSprite(jugador2.getBancoSprites().get("caminandoArriba1B"));
					} else if (jugador2.estadoSprite2 == 2) {
						jugador2.setSprite(jugador2.getBancoSprites().get("paradoArribaB"));
					} else if (jugador2.estadoSprite2 == 3) {
						jugador2.setSprite(jugador2.getBancoSprites().get("caminandoArriba2B"));
					}
					// Avanza al siguiente estado
					jugador2.estadoSprite2 = (jugador2.estadoSprite2 + 1) % 4;
					break;

				case "abajo":
					if (jugador2.estadoSprite2 == 0) {
						jugador2.setSprite(jugador2.getBancoSprites().get("paradoAbajoB"));
					} else if (jugador2.estadoSprite2 == 1) {
						jugador2.setSprite(jugador2.getBancoSprites().get("caminandoAbajo1B"));
					} else if (jugador2.estadoSprite2 == 2) {
						jugador2.setSprite(jugador2.getBancoSprites().get("paradoAbajoB"));
					} else if (jugador2.estadoSprite2 == 3) {
						jugador2.setSprite(jugador2.getBancoSprites().get("caminandoAbajo2B"));
					}
					// Avanza al siguiente estado
					jugador2.estadoSprite2 = (jugador2.estadoSprite2 + 1) % 4;
					break;

				default:
					jugador2.setDireccion("paradoB");
					break;
				}
			}
		} else {
			
			if (jugador2.getDireccion() != null) {
				switch (jugador2.getDireccion()) {
				case "parado":
					jugador2.setSprite(jugador2.getBancoSprites().get("paradoB")); // O cualquier otro sprite de "parado"
					break;
				}
			}
		}

		// Movimiento hacia la derecha (tecla D)
		if (Ventana.bufferTeclas.containsKey(68) && !jugador2.isEnMovimiento()) {
			if (posjugador2.getDerecha() != null && posjugador2.getDerecha().getTipo() == 0) {
				jugador2.setDireccion("derecha"); // Actualiza la dirección
				HiloMovimiento hm = new HiloMovimiento(jugador2, posjugador2.getDerecha());
				hm.start();
			}
		}
		// Movimiento hacia la izquierda (tecla A)
		else if (Ventana.bufferTeclas.containsKey(65) && !jugador2.isEnMovimiento()) {
			if (posjugador2.getIzquierda() != null && posjugador2.getIzquierda().getTipo() == 0) {
				jugador2.setDireccion("izquierda"); // Actualiza la dirección
				HiloMovimiento hm = new HiloMovimiento(jugador2, posjugador2.getIzquierda());
				hm.start();
			}
		}
		// Movimiento hacia abajo (tecla S)
		else if (Ventana.bufferTeclas.containsKey(83) && !jugador2.isEnMovimiento()) {
			if (posjugador2.getAbajo() != null && posjugador2.getAbajo().getTipo() == 0) {
				jugador2.setDireccion("abajo");
				HiloMovimiento hm = new HiloMovimiento(jugador2, posjugador2.getAbajo());
				hm.start();
			}
		}
		// Movimiento hacia arriba (tecla W)
		else if (Ventana.bufferTeclas.containsKey(87) && !jugador2.isEnMovimiento()) {
			if (posjugador2.getArriba() != null && posjugador2.getArriba().getTipo() == 0) {
				jugador2.setDireccion("arriba");
				HiloMovimiento hm = new HiloMovimiento(jugador2, posjugador2.getArriba());
				hm.start();
			}
		}

	}

	public void dibuja() {
		bufferDibujado = canvas.getBufferStrategy();

		if (bufferDibujado == null) {
			canvas.createBufferStrategy(4);
			return;
		}
		lapiz = bufferDibujado.getDrawGraphics();

		lapiz.clearRect(0, 0, 800, 800);

		lapiz.drawImage(spriteFondo, 0, 0, 800, 800, null);

		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				lapiz.drawImage(matrizFields[i][j].getSprite(), matrizFields[i][j].getX(), matrizFields[i][j].getY(),
						50, 50, null);
			}
		}


		lapiz.drawImage(jugador1.getSprite(), jugador1.getX(), jugador1.getY(), 50, 50, null);
		lapiz.drawImage(jugador2.getSprite(), jugador2.getX(), jugador2.getY(), 50, 50, null);

		//Bomaba 1
		for (Bomba b : jugador1.getListaBombas()) {
			lapiz.drawImage(b.getSprite1(), b.getX(), b.getY(), 50, 50, null);
		}
        //Bomba 2
		for (Bomba b : jugador2.getListaBombas()) {
			lapiz.drawImage(b.getSprite1(), b.getX(), b.getY(), 50, 50, null);
		}
		

		lapiz.setFont(new Font("Arial", Font.BOLD, 20));
		lapiz.setColor(Color.BLUE);
		lapiz.drawString("Vidas Jugador 1: " + jugador1.getVidas(), 10, 670);

		lapiz.setColor(Color.RED);
		lapiz.drawString("Vidas Jugador 2: " + jugador2.getVidas(), 10, 710);

		lapiz.dispose();
		bufferDibujado.show();
	}

	@Override
	public void run() {

		try {
			sleep(500);
		} catch (InterruptedException ex) {

		}
		while (true) {
			try {
				actualiza();
				dibuja();

				this.sleep(85);
			} catch (InterruptedException ex) {
			}
		}
	}

	public Field[][] getMatrizFields() {
		return matrizFields;
	}

	public void setMatrizFields(Field[][] matrizFields) {
		this.matrizFields = matrizFields;
	}

	public Personaje getJugador1() {
		return jugador1;
	}

	public void setJugador1(Personaje jugador1) {
		this.jugador1 = jugador1;
	}

	public Personaje getJugador2() {
		return jugador2;
	}

	public void setJugador2(Personaje jugador2) {
		this.jugador2 = jugador2;
	}

}
