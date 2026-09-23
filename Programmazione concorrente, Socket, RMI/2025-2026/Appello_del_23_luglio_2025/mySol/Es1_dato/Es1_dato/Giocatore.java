import java.util.concurrent.ThreadLocalRandom;

public class Giocatore extends Thread {
	private int id;
	TavoloGioco ilGestore;

	Giocatore(TavoloGioco g) {
		ilGestore=g;
		start();
	}

	void myoutput(String s) {
		System.out.println("giocatore_"+id+": "+s);
	}

	int sceltaAttaccato() {
		// irrilevante: scegliamo chi attaccare in modo casuale
		int attaccato=id;
		while(attaccato==id) {
			attaccato=ThreadLocalRandom.current().nextInt(IlMain.NUM_GIOCATORI);
		}
		return attaccato;
	}
	public void run() {
		Mossa m;
		if((id=ilGestore.aggiuntaGiocatore())<0) {
			// se il tavolo e` gia` completo non posso giocare, quindi termino.
			return;
		}
		myoutput("attendo inizio");
		ilGestore.attendoInizio();
		while(true) {
			myoutput("attendo turno");
			ilGestore.attendoTurno(id);
			if(ilGestore.finita()) {
				break;  // partita finita
			}
			int attaccante=ilGestore.chiMiAttacca(id);
			// qui il giocatore dovrebbe consultare la situazione: omesso perche' irrilevante
			if(attaccante<0) {
				// nessuno mi attacca: tocca a me attaccare
				m=new Mossa(id, sceltaAttaccato(), "mossa_attacco_"+id);
			} else {
				// attaccante mi ha attaccato
				m=new Mossa(id, attaccante, "mossa_difesa_"+id);
			}
			myoutput("eseguo "+m);
			ilGestore.esecuzioneMossa(m);
		}
		myoutput("termino");
	}
}
