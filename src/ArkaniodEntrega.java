import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.*;
import acm.util.RandomGenerator;


public class ArkaniodEntrega extends acm.program.GraphicsProgram{

	private static int ANCHO_PANTALLA=500;
	private static int ALTO_PANTALLA=600;
	private static int ANCHO_CURSOR =60;

	//constantes para la pirámide
	private static final int ANCHO_LADRILLO = 25;
	private static final int ALTO_LADRILLO = 12;
	private static final int LADRILLOS_BASE = 18;

	int alto_pelota ;
	GLabel marcador;
	GLabel vidas;
	GRect cursor;
	GImage pelota = new GImage("1448057551_On.png");
	double xVelocidad = 3;  //velocidad en el eje x
	double yVelocidad = -3;  //velocidad en el eje y
	GImage cursor2 = new GImage("dj-256.png");
	GImage fondo = new GImage("fondo.gif");
	public void init(){
		alto_pelota = (int) pelota.getHeight();
		setSize(ANCHO_PANTALLA, ALTO_PANTALLA);

		addMouseListeners();
	}

	public void run(){
		add(fondo,0,0);
		add(cursor2, 250 ,ALTO_PANTALLA-100 );
		add (pelota ,250, ALTO_PANTALLA-127);

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
				pause(20);
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
		if (getElementAt(pelota.getX(), pelota.getY()+pelota.getHeight())==cursor2){
			yVelocidad = -yVelocidad;	
		}
		else if (getElementAt(pelota.getX()+alto_pelota, pelota.getY()+alto_pelota)==cursor2){
			yVelocidad = -yVelocidad;	
		}
		else if (getElementAt(pelota.getX(), pelota.getY())==cursor2){
			xVelocidad = -xVelocidad;	
		}
		else if (getElementAt(pelota.getX()+alto_pelota, pelota.getY())==cursor2){
			xVelocidad = -xVelocidad;	
		}else {
			return false;
		} 
		return true;
	}
	// chequea ladrillos devolvera true si la pelota choca con los ladrillos y false si no lo hace
	private boolean chequeaLadrillos(){
		//		if(getElementAt(pelota.getX(), pelota.getY()+alto_pelota)==){
		//			yVelocidad = -yVelocidad;
		//		}

		return true;
	}






	//mueve el cursor siguiendo la posición del ratón
	public void mouseMoved (MouseEvent evento){
		if( (evento.getX()+ANCHO_CURSOR) <= ANCHO_PANTALLA){
			cursor2.setLocation(evento.getX(),ALTO_PANTALLA-100);
		}
	}






}