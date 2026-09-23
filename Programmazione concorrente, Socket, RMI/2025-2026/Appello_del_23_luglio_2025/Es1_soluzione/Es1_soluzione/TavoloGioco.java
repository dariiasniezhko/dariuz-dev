import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class TavoloGioco {
	private int numGiocatori;        // numero giocatori richiesti per giocare
	private int giocatoriPresenti;
	private int giocatoreDiTurno;
	private int giocatoreAttaccato;
	private CyclicBarrier bar;
	private boolean partitaFinita;
	protected TavoloGioco(int n) {
		numGiocatori=n;
		giocatoreDiTurno=0;
		giocatoreAttaccato=-1; // negativo indica indefinito *nessuno sotto attacco)
		giocatoriPresenti=0;
		partitaFinita=false;
		bar=new CyclicBarrier(numGiocatori);
	}

	private void myoutput(String s) {
		System.out.println("gestore: "+s);
	}

	public synchronized int aggiuntaGiocatore() {
		myoutput("aggiunta giocatore "+giocatoriPresenti);
		if(giocatoriPresenti==numGiocatori) {
			return -99;
		} else {
			return giocatoriPresenti++;
		}
	}
	public synchronized boolean finita() {
		return partitaFinita;
	}
	public void attendoInizio()  {
		try {
			bar.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// gestione eccezione omessa
		}
	}

	public synchronized int chiMiAttacca(int id) {
		if(giocatoreDiTurno==id) {
			return -1;
		} else {
			if(giocatoreAttaccato!=id) {
				myoutput("sincronia saltata!");
				System.exit(0);
			}
			return giocatoreDiTurno;
		}
	}
	public synchronized void attendoTurno(int id) {
		while(true) {
			if(partitaFinita) {
				break;  // non c'e` piu` da aspettare
			}
			if(giocatoreDiTurno==id && giocatoreAttaccato<0) {
				break;  // id e` di turno per attaccere
			}
			if(giocatoreAttaccato==id) {
				break;  // id e` attaccato
			}
			try {
				wait();
			} catch (InterruptedException e) {	}
		}
	}

	public synchronized void esecuzioneMossa(Mossa m) {
		if(m.leggiIdAttaccante()==giocatoreDiTurno) {
			giocatoreAttaccato=m.leggiIdAttaccato();
		} else {
			giocatoreDiTurno=(giocatoreDiTurno+1)%numGiocatori;
			giocatoreAttaccato=-1;
		}
		// qui andrebbe l'aggiornamento della situazione complessiva in base alla mossa: irrilevante
		// decidiamo se la partita e` finita:
		if(ThreadLocalRandom.current().nextInt(100)<7) {
			partitaFinita=true;
		}
		notifyAll();
	}
}
