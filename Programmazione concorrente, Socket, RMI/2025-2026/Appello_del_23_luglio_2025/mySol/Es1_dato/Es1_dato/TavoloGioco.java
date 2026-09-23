import java.util.concurrent.ThreadLocalRandom;

public class TavoloGioco {
	private int numGiocatori;        // numero giocatori richiesti per giocare
	private int giocatoriPresenti;
	private boolean partitaFinita;
	protected TavoloGioco(int n) {
		numGiocatori=n;
		giocatoriPresenti=0;
		partitaFinita=false;
	}

	private void myoutput(String s) {
		System.out.println("gestore: "+s);
	}

	public int aggiuntaGiocatore() {
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
	}

	public int chiMiAttacca(int id) {
		// da fare
	}
	
	public void attendoTurno(int id) {
		// da fare
	}

	public void esecuzioneMossa(Mossa m) {
		// da fare
		// qui andrebbe l'aggiornamento della situazione complessiva in base alla mossa: irrilevante
		// decidiamo se la partita e` finita:
		if(ThreadLocalRandom.current().nextInt(100)<7) {
			partitaFinita=true;
		}
	}
}
