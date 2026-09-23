import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class Produttore extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	Produttore(int id) throws IOException{
		this.setName("prod_"+id);
		Socket socket=new Socket("localhost", 8999);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}
	private void dormitina() {
		try {
			sleep(ThreadLocalRandom.current().nextInt(200));
		} catch (InterruptedException e) {	}
	}
	public void run() {
		Roba r;
		System.out.println(this.getName()+" running");
		try {
			for(int i=0; i<20; i++) {
				dormitina();
				r=new Roba("descr",ThreadLocalRandom.current().nextInt(100));
				out.writeObject("inserimento");
				out.writeObject(r);
				out.flush();
			}
			out.writeObject("end");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
