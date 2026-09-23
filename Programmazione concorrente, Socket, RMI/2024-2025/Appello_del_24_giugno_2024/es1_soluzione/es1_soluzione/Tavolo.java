import java.util.concurrent.ThreadLocalRandom;

public class Tavolo {
	int numModificheAttive;
	int numLettori;
	Tavolo() {
		// inizializzazione (irrilevante)
		numModificheAttive=0;
		numLettori=0;
	}

	private void attivita() {
		int a=300;
		int b=700;
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(a, b));
		} catch (InterruptedException e) {	}
	}
	private void attivitaBis() {
		int a=0;
		for(int i=0; i<100000; i++) {
			for(int j=0; j<1000000; j++) {
				a=1-a;
			}
		}
	}
	public void prendiPedina() {
		// prima parte della mossa
		// qui si modifica la situazione del tavolo: come avviene e` irrilevante
		attivita();
	}
	public void mettiPedina() {
		// seconda parte della mossa		
		// qui si modifica la situazione del tavolo: come avviene e` irrilevante
		attivita();
	}

	public String leggi() {
		// codifica la situazione del tavolo in una stringa, mettendoci un po' di tempo
		attivita();
		return "codifica della situazione";
	}


}
