
public class Gestore {
	int numModificheAttive;
	int numLettori;
	Tavolo ilTavolo;

	public Gestore(Tavolo t) {
		numModificheAttive=0;
		numLettori=0;
		ilTavolo=t;
	}
	private void situazioneThread() {
		System.out.println(" [#modificanti="+numModificheAttive+", #leggenti="+numLettori+"]");
	}
	public synchronized void attesaAccessoLettura() {
		while(numModificheAttive>0) {
			try {
				wait();
			} catch (InterruptedException e) { 	}
		}
		numLettori++;
	}

	public synchronized void fineLettura() {
		numLettori--;
		notifyAll();
	}

	public String leggi() {
		attesaAccessoLettura();
		System.out.print(Thread.currentThread().getName()+" legge situazione "+
				(numModificheAttive>0?" illegalmente ******":""));
		situazioneThread();
		String situazione=ilTavolo.leggi();
		fineLettura();
		return situazione;
	}

	public void mossa(String m) {
		attesaAccessoMossa();
		System.out.print(Thread.currentThread().getName()+" effettua mossa "+
				(numModificheAttive>1||numLettori>0?" illegalmente ******":""));
		situazioneThread();
		// esecuzione della mossa
		ilTavolo.prendiPedina();
		// a questo punto la situazione del tavolo da gioco e` inconsistente
		ilTavolo.mettiPedina();
		fineMossa();
	}

	public synchronized void attesaAccessoMossa() {
		while(numLettori>0 || numModificheAttive>0) {
			try {
				wait();
			} catch (InterruptedException e) { 	}
		}
		numModificheAttive++;
	}

	public synchronized void fineMossa() {
		numModificheAttive--;
		notifyAll();
	}

}
