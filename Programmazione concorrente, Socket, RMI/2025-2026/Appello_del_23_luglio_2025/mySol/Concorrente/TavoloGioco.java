import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class TavoloGioco {
	private int numGiocatori;        // numero giocatori richiesti per giocare
	private int giocatoriPresenti;
	private boolean partitaFinita;
	private Mossa m;
	private CyclicBarrier b;
	private int idAttaccante = 0;
	private int idAttaccato = -1;
	private int turnoCorrente;

	protected TavoloGioco(int n) {
		numGiocatori=n;
		giocatoriPresenti=0;
		partitaFinita=false;
		b=new CyclicBarrier(n);
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
	
	public boolean finita() {
		return partitaFinita;
	}

	public void attendoInizio()  {
		// da fare
		try {
			b.await();
		} catch (InterruptedException|BrokenBarrierException e) {
			// TODO: handle exception
		}
	}

	public synchronized int chiMiAttacca(int id) {
		if(idAttaccante==id){
				return -1;
		}
		// da fare
		while (idAttaccato!=id) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
		return idAttaccante;
	}

	/*public synchronized int chiMiAttacca(int id) {
	// da fare
	while (m.leggiIdAttaccato()!=id) {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	return m.leggiIdAttaccante();
} 
	M MAI INIZIALIZZATO -> NULLPOINTEREXCEPTION*/
	
	public synchronized void attendoTurno(int id) {
		// da fare
		while (!partitaFinita && idAttaccante != id && idAttaccato != id) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public synchronized void esecuzioneMossa(Mossa m) {
		// da fare
		if(m.leggiIdAttaccante()==turnoCorrente){//se tocca a me ed io attacco
			idAttaccato=m.leggiIdAttaccato();
		}
		else{//altrimenti, se la mossa è di difesa
			turnoCorrente=(turnoCorrente+1)%numGiocatori;
			idAttaccante=turnoCorrente;
			idAttaccato=-1;
		}
		// qui andrebbe l'aggiornamento della situazione complessiva in base alla mossa: irrilevante
		// decidiamo se la partita e` finita:
		if(ThreadLocalRandom.current().nextInt(100)<7) {
			partitaFinita=true;
		}
		notifyAll();
	}
}
