
package bomber;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloBomba extends Thread {
	Bomba bomba;

	public HiloBomba(Bomba bomba) {
		this.bomba = bomba;
	}

	@Override
	public void run() {
	    for (int i = 0; i < 10; i++) {
	        bomba.setBandera(!bomba.isBandera());
	        try {
	            sleep(250);
	        } catch (InterruptedException ex) {
	            Logger.getLogger(HiloBomba.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    new ExplosionBomba(bomba);
	    bomba.getJugador().getListaBombas().remove(bomba);
	}

}
