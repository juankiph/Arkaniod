import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.*;
import acm.util.RandomGenerator;
/*
 * autor: Juan Carlos Sanchez Coll
 * 
 * se trata de el juego clasico "ARKANOID" y las premisas son:
 * -que los ladrillos se rompan, haya marcador, la bola rebote 
 */


public class ArkaniodEntrega extends acm.program.GraphicsProgram{

	private static int ANCHO_PANTALLA=500;
	private static int ALTO_PANTALLA=600;
	private static int ANCHO_NAVE =60;

	//constantes para la pirámide
	private static final int ANCHO_LADRILLO = 25;
	private static final int ALTO_LADRILLO = 12;
	private static final int LADRILLOS_BASE = 18;

	int alto_pelota ;
	GLabel marcador;
	GLabel vidas;
	//GRect cursor;
	GImage pelota = new GImage("1448057551_On.png");
	double xVelocidad = 3;  //velocidad en el eje x
	double yVelocidad = -3;  //velocidad en el eje y
	GImage nave = new GImage("nave.png");
	GImage fondo = new GImage("fondo.gif");
	public void init(){
		alto_pelota = (int) pelota.getHeight();
		setSize(ANCHO_PANTALLA, ALTO_PANTALLA);

		addMouseListeners();
	}

	public void run(){
		add(fondo,0,0);
		add(nave, 250 ,ALTO_PANTALLA-100 );
		add (pelota ,250, ALTO_PANTALLA/2);

		//creo el marcador 
		marcador = new GLabel("puntos");
		marcador.setFont("Times New Roman-16");
		marcador.setColor(Color.orange);
		add (marcador,400,550);
		vidas = new GLabel("tiempo");
		vidas.setFont("Times New Roman-16");
		vidas.setColor(Color.orange);
		add (vidas,50,550);
		pintaPiramide();
		while(true){
			pelota.move(xVelocidad, yVelocidad);
			chequeaColision();
			pause(20);
		}
	}



	private void pintaPiramide(){
		int x= -(ANCHO_PANTALLA - LADRILLOS_BASE*ANCHO_LADRILLO) /2;
		int y= 0;
		RandomGenerator random = new RandomGenerator();
		for (int j=0; j<LADRILLOS_BASE; j++){
			for (int i=j; i<LADRILLOS_BASE; i++){
				GRect ladrillo = new GRect (ANCHO_LADRILLO,ALTO_LADRILLO);
				add (ladrillo,i*ANCHO_LADRILLO-x,y+j*ALTO_LADRILLO);
				ladrillo.setFilled(true);
				ladrillo.setFillColor(Color.LIGHT_GRAY);

			}
			x = x+ANCHO_LADRILLO/2;
		}
	}


	private void chequeaColision(){
		if (chequeaPared()){
			//chequeo si toca con el cursor
			if(!chequeaCursor()){
				chequeaLadrillos();
			}
		}

	}

	private boolean chequeaPared(){
		boolean auxiliar = true;
		//si toca el techo
		if (pelota.getY() <= 0){
			yVelocidad = -yVelocidad;
			auxiliar = false;
		}

		//si toca la pared derecha
		if (pelota.getX() >= ANCHO_PANTALLA - alto_pelota){
			xVelocidad = -xVelocidad;
			auxiliar = false;
		}

		//si toca la pared izquierda
		if (pelota.getX() <= 0){
			xVelocidad = -xVelocidad;
			auxiliar = false;
		}
		return auxiliar;
	}


	//chequeaCursor devolverá true si ha chocado el cursor con la pelota
	// y false si no ha chocado.
	private boolean chequeaCursor(){
		if (getElementAt(pelota.getX(), pelota.getY()+pelota.getHeight()+1)==nave){
			yVelocidad = -yVelocidad;	
		}
		else if (getElementAt(pelota.getX()+alto_pelota, pelota.getY()+alto_pelota+1)==nave){
			yVelocidad = -yVelocidad;	
		}
		else if (getElementAt(pelota.getX(), pelota.getY()-1)==nave){
			xVelocidad = -xVelocidad;	
		}
		else if (getElementAt(pelota.getX()+alto_pelota, pelota.getY()-1)==nave){
			xVelocidad = -xVelocidad;	
		}else {
			return false;
		} 
		return true;
	}
	// chequea ladrillos devolvera true si la pelota choca con los ladrillos y false si no lo hace		 
	private void chequeaLadrillos() {

		int pelotaX = (int) pelota.getX();
		int pelotaY = (int) pelota.getY();
		int lado = alto_pelota;

		// si chequea posicion devuelve false sigue mirando el resto de puntos
		//de la pelota

		if( !chequeaPosicion(pelotaX, pelotaY,'y')){
			if( !chequeaPosicion(pelotaX+lado, pelotaY-1,'y')){
				if( !chequeaPosicion(pelotaX-1, pelotaY+lado,'x')){
					if( !chequeaPosicion(pelotaX, pelotaY+2*lado,'y')){
						if( !chequeaPosicion(pelotaX+lado, pelotaY+lado,'y')){
						}						
					}
				}
			}
		}
	}


	private boolean chequeaPosicion(int posX, int posY, char direccion) {

		GObject auxiliar;
		boolean choque = false;
		auxiliar = getElementAt(posX, posY);

		// Chequeamos los ladrillos
		if ((auxiliar != pelota) && (auxiliar != nave) && (auxiliar !=fondo) && (auxiliar != null)&& (auxiliar != vidas)&& (auxiliar != marcador)) {
			remove(auxiliar);
			if (direccion == 'y') {
				yVelocidad = -yVelocidad;
			} else {
				xVelocidad = -xVelocidad;
			}
			//puntuacion++;// aumentamos la puntuacion en uno
			choque = true;
		}


		// devolvemos el valor de choque
		return (choque);
	}





	//mueve el cursor siguiendo la posición del ratón
	public void mouseMoved (MouseEvent evento){
		if( (evento.getX()+ANCHO_NAVE) <= ANCHO_PANTALLA){
			nave.setLocation(evento.getX(),ALTO_PANTALLA-100);
		}
	}






}