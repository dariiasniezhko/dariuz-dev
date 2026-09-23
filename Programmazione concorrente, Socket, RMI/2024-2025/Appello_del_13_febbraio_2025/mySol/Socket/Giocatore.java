import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.*;

public class Giocatore {
	static final int PORT = 1999;
	int mioId=1;
	String mioNome;
	Socket socket;
	Giocatore ilGiocatore;
	ObjectInputStream in;
	ObjectOutputStream out;

	public Giocatore () throws IOException{
		InetAddress addr = InetAddress.getByName(null);
		socket = new Socket(addr, PORT);
		mioNome="giocatore_"+mioId;
		out=new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in=new ObjectInputStream(socket.getInputStream());
	}

	private void dormitina() {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(100, 200));
		} catch (InterruptedException e) {	}
	}

	public void exec() throws IOException{
		for(int it=0;it<10;it++){
			dormitina();
			System.out.println(mioNome+": "+"gioco");
			out.writeObject("gioco");
			out.writeObject(mioId);
			out.flush();
			dormitina();
			System.out.println(mioNome+"["+it+"]: "+"leggo");
			out.writeObject("lettura");
			out.writeObject(mioId);
			out.flush();
		}
		out.writeObject("fine");
		out.flush();
		System.out.println(mioNome+": Fine");
	}

	public static void main(String[] args) throws IOException{
		new Giocatore().exec();
	}
}
