
public class IlMain {
	public static void main(String[] args) {
		Deposito dep=new Deposito(4);
		new Produttore(1, dep).start();
		new Consumatore(1, dep).start();
		new Produttore(2, dep).start();
		new Consumatore(2, dep).start();
	}
}
