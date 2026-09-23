import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Gioco {
	private int numGiocatori=0;
	FasiGioco faseCorrente;  // la fase corrente: gioco o lettura risultati
	CyclicBarrier bG, bL;    // barriere per la fase di Gioco e Lettura


	public Gioco(int ng) {
		numGiocatori=ng;
		bG=new CyclicBarrier(numGiocatori);
		bL=new CyclicBarrier(numGiocatori);
		faseCorrente=FasiGioco.Gioco;
	}
	public void giocata(int idGiocatore) {
		try {
			bG.await();
		} catch (InterruptedException | BrokenBarrierException e) {	}
		faseCorrente=FasiGioco.LetturaRisultati;
	}
	public void letturaEsito(int idGiocatore) {
		try {
			bL.await();
		} catch (InterruptedException | BrokenBarrierException e) {	}
		faseCorrente=FasiGioco.LetturaRisultati;
	}
}
