
public class IlMain {
	public final static int NUM_GIOCATORI=4;
	void exec() {
		TavoloGioco ilGioco=new TavoloGioco(NUM_GIOCATORI);
		for(int i=0; i<NUM_GIOCATORI; i++) {
			new Giocatore(ilGioco);
		}
	}
	public static void main(String[] args) {
		new IlMain().exec();
	}
}
