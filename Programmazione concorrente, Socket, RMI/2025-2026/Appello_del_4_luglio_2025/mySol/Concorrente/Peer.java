import java.util.concurrent.ThreadLocalRandom;

public class Peer extends Thread {
	final int numIterazioni=10;
	final int numDocumenti=8;
	private int id;
	private String nome;
	private DepositoDocumenti ilMioDeposito;
	private CatalogoInterface ilCatalogo;

	Peer(Catalogo c) {
		ilCatalogo=c;
		id=ilCatalogo.newPeerId();  // riceve id da catalogo
		nome="peer_"+id;
		Thread.currentThread().setName(nome);
		ilMioDeposito=new DepositoDocumenti(id);  // crea il proprio deposito documenti
		for(int i=0; i<numDocumenti; i++) {
			// crea titolo
			String titolo="titolo_"+100*id+i;
			// inserisce nuovo documento nel deposito locale
			ilMioDeposito.inserimento(new Documento(titolo, "boh"));  // il contenuto del documento e` irrilevante
			// informa il catalogo
			ilCatalogo.inserimento(titolo, ilMioDeposito);
		}
	}

	public void run() {
		String titolo=" ";
		DepositoDocumenti dep=null;
		Documento doc;
		for(int j=0; j<numIterazioni; j++) {
			boolean trovato=false;
			while (!trovato) {
				// determina casualmente il titolo del documento da usare
				titolo="titolo_"+100*ThreadLocalRandom.current().nextInt(IlMain.numPeers)+
						ThreadLocalRandom.current().nextInt(numDocumenti);
				dep=ilCatalogo.trovaDeposito(titolo);  // cerca il titolo nel catalogo
				trovato=(dep!=null);
			};
			System.out.println(nome+": mi interessa "+titolo);
			// dep potrebbe essere il deposito locale
			System.out.println(nome+": trovato "+titolo+" su Peer "+dep.mioId());
			doc=dep.trova(titolo);
			doc.occupa();
			System.out.println(nome+": uso "+titolo);
			doc.usa();
			doc.libera();
		}
		System.out.println(nome+": termino ");
	}

}
