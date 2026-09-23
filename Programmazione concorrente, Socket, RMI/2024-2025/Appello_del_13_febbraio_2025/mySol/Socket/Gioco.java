import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Gioco {
	private int numGiocatori=0;
	FasiGioco faseCorrente;  // la fase corrente: gioco o lettura risultati
	boolean[] hannoFatto;    // tiene conto di quanti giocatori hanno giocato (se siamo nella fase di gioco)
	                         // o hanno letto il risultato (se siamo nella fase di lettura dei risultati)	
	//campi barriera
	CyclicBarrier bG, bL;

	public Gioco(int ng) {
		numGiocatori=ng;
		faseCorrente=FasiGioco.Gioco;
		bG=new CyclicBarrier(ng);
		bL=new CyclicBarrier(ng);
	}

	public void giocata(int idGiocatore) {//punto 3
		try {
			bG.await();
		} catch (InterruptedException|BrokenBarrierException e) {
			// TODO: handle exception
			faseCorrente=FasiGioco.LetturaRisultati;
		}
	}

	public void letturaEsito(int idGiocatore) {
		try {
			bL.await();
		} catch (InterruptedException|BrokenBarrierException e) {
			// TODO: handle exception
			faseCorrente=FasiGioco.Gioco;
		}
	}
}
