import java.awt.Color;

import sun.audio.*;

import java.io.*;
import java.awt.event.MouseEvent;

import javazoom.jl.player.Player;
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
 * se trata de el juego clasico "ARKANOID" customizado con el grupo daft punk . Se estructura en tres partes:
 * la presentacion, el juego y o el game over o salvar el mundo.
 */
import acm.util.SoundClip;


public class ArkaniodEntrega extends acm.program.GraphicsProgram{

	private static int ANCHO_PANTALLA=500;
	private static int ALTO_PANTALLA=600;
	private static int ANCHO_NAVE =60;

	//constantes para la pirámide
	private static final int ANCHO_LADRILLO = 35;
	private static final int ALTO_LADRILLO = 15;
	private static final int LADRILLOS_BASE = 12;
	RandomGenerator aleatorio = new RandomGenerator();
	int alto_pelota ; // creamos un int para darle un valor de altura a la pelota
	int segundos; // creamos un int de segundos para que vaya aumentando
	int puntuacion; // creamos un int para cuando choque con un ladrillo aumente 1 la puntuacion
	GLabel marcador  ; // llamamos al marcador	
	GImage fin = new GImage("fin.jpg"); // imagen para el game over
	//GImage clear = new GImage ("stage-clear.gif");
	GImage pelota = new GImage("1448057551_On.png");// imagen en png para la pelota
	double xVelocidad = 3;  //velocidad en el eje x 
	double yVelocidad = -3;  //velocidad en el eje y
	GImage nave = new GImage("nave.jpeg"); // cambiamos el cursor por una nave
	//GRect nave = new GRect(70,10);
	GImage fondo = new GImage("fondo.gif"); // hacemos un gif de fondo
	GImage presentacion = new GImage("presentacion.gif");// opnemos una presentacion
	GImage start = new GImage("start.png");
	boolean gameOver= false; // game over es falso para que no aparazeca durante que juegas
	public void init(){
		alto_pelota = (int) pelota.getHeight();
		setSize(ANCHO_PANTALLA, ALTO_PANTALLA);

		addMouseListeners();
	}

	public void run(){
		SoundClip miSonido = new SoundClip("cancion.wav");
		miSonido.setVolume(1);
		miSonido.play();
		presentacion.setSize(500, 600);
		pause(1000);
	add(presentacion,0,0);
	//waitForClick();
		pause(8000);
		remove(presentacion);
		
		
		add(fondo,0,0);//añado fondo
		
		nave.setColor(Color.ORANGE);
		add(nave, 200 ,ALTO_PANTALLA-100 );//añado nave
		add (pelota ,200, ALTO_PANTALLA-130);
		
		//creo el marcador 
		marcador = new GLabel("puntos  ");
		marcador.setFont("Times New Roman-16");
		marcador.setColor(Color.orange);
		add (marcador,400,550);
		pintaPiramide();
		add (start,100,400);
		waitForClick();
		remove(start);
		while(!gameOver){
			// velocidad de la pelota
			pelota.move(xVelocidad, yVelocidad);
			chequeaColision();
			pause(20);
			if (pelota.getY() >= 599){
				add(fin);
				gameOver=true;
				
				}
		}
			
	 
		}
	
//dibujamos una piramide de ladrillos
	private void pintaPiramide(){
		int x= -(ANCHO_PANTALLA - LADRILLOS_BASE*ANCHO_LADRILLO) /2;
		int y= 0;
		RandomGenerator random = new RandomGenerator();
		for (int j=0; j<LADRILLOS_BASE; j++){
			for (int i=j; i<LADRILLOS_BASE; i++){
				GRect ladrillo = new GRect (ANCHO_LADRILLO,ALTO_LADRILLO);
				add (ladrillo,i*ANCHO_LADRILLO-x,y+j*ALTO_LADRILLO);
				ladrillo.setFilled(true);
				ladrillo.setFillColor(Color.cyan);
				GRect ladrillo2 = new GRect (ANCHO_LADRILLO,ALTO_LADRILLO);//el ladrillo2 es un ladrillo especial que supuestamente 
				// no se rompe pero va aumentado en una por columna (serendipia, lo he sacado de pura chiripa.)
				add (ladrillo2,i*ANCHO_LADRILLO-LADRILLOS_BASE+5,200);
				ladrillo2.setFilled(true);
				ladrillo2.setFillColor(Color.red);

			}
			x = x+ANCHO_LADRILLO/2;
		}
	}
	//chequeamos donde choca la pelota

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
			yVelocidad =aleatorio.nextDouble(-1,1) -yVelocidad;
			auxiliar = false;
		}

		//si toca la pared derecha
		if (pelota.getX() >= ANCHO_PANTALLA - alto_pelota){
			xVelocidad = -xVelocidad;
			auxiliar = false;
		}

		//si toca la pared izquierda
		if (pelota.getX() <= 0){
			xVelocidad = aleatorio.nextDouble(-1,1)-xVelocidad;
			auxiliar = false;
		}
		return auxiliar;
	}


	//chequeaCursor devolverá true si ha chocado el cursor con la pelota
	// y false si no ha chocado.
	private boolean chequeaCursor(){
		if (getElementAt(pelota.getX(), pelota.getY()+pelota.getHeight())==nave){
			yVelocidad = aleatorio.nextDouble(-1,1)-yVelocidad;	
		}
		else if (getElementAt(pelota.getX()+alto_pelota+1, pelota.getY()+alto_pelota+1)==nave){
			yVelocidad = aleatorio.nextDouble(-1,1)-yVelocidad;	
		}
		else if (getElementAt(pelota.getX(), pelota.getY()-1)==nave){
			xVelocidad = aleatorio.nextDouble(-1,1)-xVelocidad;	
		}
		else if (getElementAt(pelota.getX()+alto_pelota+1, pelota.getY())==nave){
			xVelocidad = aleatorio.nextDouble(-1,1)-xVelocidad;	
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
				if( !chequeaPosicion(pelotaX+1, pelotaY+lado,'x')){
					if( !chequeaPosicion(pelotaX, pelotaY+2*lado,'y')){
						if( !chequeaPosicion(pelotaX+lado, pelotaY+lado,'y')){
							
						}						
					}
				}
			}
		}
		
	}
	//chequeamos la posicion
	private boolean chequeaPosicion(int posX, int posY, char direccion) {

		GObject auxiliar;
		boolean choque = false;
		auxiliar = getElementAt(posX, posY);

		// Chequeamos los ladrillos
		if ((auxiliar != pelota) && (auxiliar != nave) && (auxiliar !=fondo) && (auxiliar != null)&&  (auxiliar != marcador)) {
			remove(auxiliar);
			if (direccion == 'y') {
				yVelocidad =aleatorio.nextDouble(-1,1) -yVelocidad;
			
				xVelocidad =aleatorio.nextDouble(-1,1)-xVelocidad;
			
			
				
					
				
			choque = true;
		}
			// aumentamos el tiempo de juego
			
				
			
			//aumentamos la puntuacion
			if (choque){
				puntuacion++;
			marcador.setLabel("puntos  "+ puntuacion);
			}
		// devolvemos el valor de choque
		return (choque);
	}

return false;
	}


	//mueve el cursor siguiendo la posición del ratón
	public void mouseMoved (MouseEvent evento){
		if( (evento.getX()+ANCHO_NAVE) <= ANCHO_PANTALLA){
			nave.setLocation(evento.getX(),ALTO_PANTALLA-100);
		}
		


	}

}