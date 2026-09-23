public class Mossa {
	String descrizione;  // i dettagli della mossa sono irrilevanti
	int idGiocatoreAttaccante;
	int idGiocatoreAttaccato;
	
	Mossa(int g1, int g2, String d){
		descrizione=d;
		idGiocatoreAttaccante=g1;
		idGiocatoreAttaccato=g2;
	}
	int leggiIdAttaccante() {
		return idGiocatoreAttaccante;
	}
	int leggiIdAttaccato() {
		return idGiocatoreAttaccato;
	}
	public String toString() {
		return descrizione;
	}
}
