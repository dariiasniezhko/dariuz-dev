import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

class Giocatore  {
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String name;
	Giocatore(){
		long now=System.currentTimeMillis();
		name="Giocatore_"+(now%100000);
	}

	public void exec() {
		try {
			InetAddress addr = InetAddress.getByName(null);
			Socket socket=new Socket(addr, 8999);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("client  "+name+" started");

			for(int j=0; j<10; j++) {
				try {
					Thread.sleep(ThreadLocalRandom.current().nextInt(200,300));
				} catch (InterruptedException e) { }
				//				System.out.println(name+" iterazione "+j);	
				if(ThreadLocalRandom.current().nextBoolean()) {
					// scrive
					String mossa="mossa_"+ThreadLocalRandom.current().nextInt(1,10);
					System.out.println(name+" faccio mossa "+mossa);	
					out.writeObject("mossa");
					out.writeObject(mossa);
					System.out.println(name+" scrittura completata");	
				} else {
					// legge
					out.writeObject("lettura");
					String situazione=(String) in.readObject();
					System.out.println(name+" lettura situazione completata");	
				}
			}
			out.writeObject("end");
			out.close();
			in.close();
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[])  {
		new Giocatore().exec();
		System.out.println("Client: ohibo`, termina main");
	}
}
