import java.util.LinkedList;
import java.util.List;

public class DepositoDocumenti {
	List<Documento> ilDeposito;
	int id;
	DepositoDocumenti(int n){
		ilDeposito=new LinkedList<Documento>();
		id=n;
	}
	public void inserimento(Documento d) {
		if(!ilDeposito.contains(d)) {
			ilDeposito.add(d);
		}
	}
	public Documento trova(String titolo) {
		for(Documento d: ilDeposito) {
			if(titolo.equals(d.leggiTitolo())) {
				return d;
			}
		}
		return null;
	}
	public int mioId() {
		return id;
	}
}