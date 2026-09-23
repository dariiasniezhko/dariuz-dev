import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

public class Consumatore extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	Consumatore(int id) throws UnknownHostException, IOException{
		this.setName("cons_"+id);
		socket=new Socket("localhost", 8999);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}
	private void dormitina() {
		try {
			sleep(ThreadLocalRandom.current().nextInt(200));
		} catch (InterruptedException e) {	}
	}
	public void run() {
		Roba r=null;
		boolean preso;
		int quality;
		System.out.println(this.getName()+" running");
		try {
			for(int i=0; i<20; i++) {
				preso=false;
				quality=98;
				while(!preso) {
					out.writeObject("estrazione");
					out.writeObject(quality);
					r= (Roba) in.readObject();
					preso=(r!=null);
					quality-=5;
					quality=Math.max(0, quality-5);
				}
				dormitina();  // uso 
			}
			out.writeObject("end");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
