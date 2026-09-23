import java.util.Hashtable;

public class Catalogo implements CatalogoInterface{
	int numPeers=0;
	Hashtable<String, DepositoDocumenti> ilCatalogo;
	
	Catalogo(){
		numPeers=0;
		ilCatalogo=new Hashtable<String, DepositoDocumenti>();
	}
	
	public synchronized void inserimento(String tit, DepositoDocumenti dd) {
		ilCatalogo.put(tit, dd);
	}
	
	public synchronized DepositoDocumenti trovaDeposito(String titolo) {
		return ilCatalogo.get(titolo);
	}

	public int newPeerId() {
		return numPeers++;
	}

}
