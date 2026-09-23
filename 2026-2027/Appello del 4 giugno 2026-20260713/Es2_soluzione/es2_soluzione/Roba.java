import java.io.Serializable;

public class Roba implements Serializable{
	private static final long serialVersionUID = 1L;
	private String descrizione;
	private int qualita;
	Roba(String s, int q){
		descrizione=s;
		qualita=q;
	}
	public String leggiDescr() {
		return descrizione;
	}
	public int leggiQualita() {
		return qualita;
	}
	public String toString() {
		return("<r_"+descrizione+"_"+qualita+">");
	}
	public Roba copia() {
		return new Roba(this.leggiDescr(), this.leggiQualita());
	}
}
