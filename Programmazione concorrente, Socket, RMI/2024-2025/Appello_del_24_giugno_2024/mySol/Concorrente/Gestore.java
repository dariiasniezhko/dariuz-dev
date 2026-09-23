
public class Gestore {
	int numModificheAttive;
	int numLettori;
	Tavolo ilTavolo;

	public Gestore(Tavolo t) {
		numModificheAttive=0;
		numLettori=0;
		ilTavolo=t;
	}

	private synchronized void inizioLettura(){
		while (numModificheAttive!=0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		numLettori++;
	}

	private synchronized void fineLettura(){
		numLettori--;
		notifyAll();
	}

	private synchronized void situazioneThread() {//ANDAVA MESSO SYNCHRONIZED
		System.out.println(" [#modificanti="+numModificheAttive+", #leggenti="+numLettori+"]");
	}

	/*public synchronized String leggi() { */
	/*	numLettori++; */
	/*	System.out.print(Thread.currentThread().getName()+" legge situazione "+ */
	/*			(numModificheAttive>0?" illegalmente ******":"")); */
	/*	situazioneThread(); */
	/*	numLettori--; */
	/*	return ilTavolo.leggi(); */
	/*} */

	public String leggi(){
		inizioLettura();
		System.out.print(Thread.currentThread().getName()+" legge situazione "+ 
		(numModificheAttive>0?" illegalmente ******":"")); 
		situazioneThread();
		String ris = ilTavolo.leggi();
		fineLettura();
		return ris;
	}

	/*public synchronized void mossa(String m) { */
	/*	System.out.print(Thread.currentThread().getName()+" effettua mossa "+ */
	/*			(numModificheAttive>1||numLettori>0?" illegalmente ******":"")); */
	/*	situazioneThread(); */
	/*	numModificheAttive++; */
	/*	// esecuzione della mossa */
	/*	ilTavolo.prendiPedina(); */
	/*	// a questo punto la situazione del tavolo da gioco e` inconsistente */
	/*	ilTavolo.mettiPedina(); */
	/*	numModificheAttive--; */
	/*} */

	public synchronized void mossa(String m){
		while (numModificheAttive!=0 || numLettori!=0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		System.out.print(Thread.currentThread().getName()+" effettua mossa "+ 
		(numModificheAttive>1||numLettori>0?" illegalmente ******":"")); 
		situazioneThread(); 
		numModificheAttive++; 
		// esecuzione della mossa 
		ilTavolo.prendiPedina(); 
		ilTavolo.mettiPedina();
		numModificheAttive--; 
		notifyAll();
	}
}
