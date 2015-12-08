

import acm.util.SoundClip;

public class mp3 extends acm.program.GraphicsProgram {


	
	
	public void run() {

		SoundClip miSonido = new SoundClip("cancion.wav");
		miSonido.setVolume(1);
		miSonido.play();
		

	}
	

}