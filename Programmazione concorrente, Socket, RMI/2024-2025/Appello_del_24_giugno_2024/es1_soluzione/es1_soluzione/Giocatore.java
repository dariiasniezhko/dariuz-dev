import java.util.concurrent.ThreadLocalRandom;

public class Giocatore extends Thread {
	Gestore ilGestore;
	Giocatore(int id, Gestore g){
		ilGestore=g;
		this.setName("Giocatore_"+id);
	}
	void mySleep(int a, int b) {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(a, b));
		} catch (InterruptedException e) {	}
	}
	public void run() {
		String situazione;
		while(true) {
			if(	ThreadLocalRandom.current().nextBoolean()) {
				System.out.println(this.getName()+" vuole leggere");
				// legge
				situazione=ilGestore.leggi();
				// mySleep(2, 5);
			} else {
				// scrive
				System.out.println(this.getName()+" vuole muovere");
				// mySleep(2, 5);
				String miaMossa="mossa_"+ThreadLocalRandom.current().nextInt(1,10);
				ilGestore.mossa(miaMossa);
			}
		}
	}
}
