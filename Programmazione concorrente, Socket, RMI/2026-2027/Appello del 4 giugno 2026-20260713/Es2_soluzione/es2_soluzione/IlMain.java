import java.io.IOException;

public class IlMain {
	public static void main(String[] args) throws IOException {
		new Produttore(1).start();
		new Consumatore(1).start();
		new Produttore(2).start();
		new Consumatore(2).start();
	}
}
