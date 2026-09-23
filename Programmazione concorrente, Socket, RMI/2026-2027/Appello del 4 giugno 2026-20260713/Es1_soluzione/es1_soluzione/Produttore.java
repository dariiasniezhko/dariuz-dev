import java.util.concurrent.ThreadLocalRandom;

public class Produttore extends Thread {
	Deposito ilDeposito;
	Produttore(int id, Deposito dep){
		this.setName("prod_"+id);
		ilDeposito=dep;
	}
	private void dormitina() {
		try {
			sleep(ThreadLocalRandom.current().nextInt(200));
		} catch (InterruptedException e) {	}
	}
	public void run() {
		Roba r;
		while(true) {
			dormitina();
			r=new Roba("descr",ThreadLocalRandom.current().nextInt(100));
			ilDeposito.inserisci(r);
		}
	}
}
