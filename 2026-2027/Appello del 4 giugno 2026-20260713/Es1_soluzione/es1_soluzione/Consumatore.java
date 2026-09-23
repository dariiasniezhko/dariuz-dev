import java.util.concurrent.ThreadLocalRandom;

public class Consumatore extends Thread {
	Deposito ilDeposito;
	Consumatore(int id, Deposito dep){
		this.setName("cons_"+id);
		ilDeposito=dep;
	}
	private void dormitina() {
		try {
			sleep(ThreadLocalRandom.current().nextInt(200));
		} catch (InterruptedException e) {	}
	}
	public void run() {
		Roba r=null;
		boolean preso;
		int quality;
		while(true) {
			preso=false;
			quality=98;
			while(!preso) {
				r=ilDeposito.estrazione(quality);
				preso=(r!=null);
				quality-=5;
				quality=Math.max(0, quality-5);
			}
			dormitina();  // uso 
		}
	}
}
