import java.util.concurrent.ThreadLocalRandom;

public class Documento {
	String titolo;
	String contenuto;
	boolean occupato;
	Documento(String t, String c){
		titolo=t;
		contenuto=c;
		occupato=false;
	}
	public String leggiTitolo() {
		return titolo;
	}
	public String leggi() {
		return contenuto;
	}
	public void occupa() {
		occupato=true;
	}
	public void usa() {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(200, 400));
		} catch (InterruptedException e) {}
	}
	public void libera() {
		occupato=false;
	}
	public boolean occupato() {
		return occupato;
	}
}
