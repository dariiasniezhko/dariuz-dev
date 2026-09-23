import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

public class ClientSocket {
	static final int PORT = 1999;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;

	public ClientSocket() throws IOException{
		InetAddress addr = InetAddress.getByName(null);
		socket = new Socket(addr, PORT);
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
	}

	public void exec() throws IOException, ClassNotFoundException{
		try {
			System.out.println("Provo ad accedere...");
			out.writeObject("accesso");
			out.flush();
			String nome = (String) in.readObject();
			for(int i=0; i<10; i++){
				if(	ThreadLocalRandom.current().nextBoolean()) {
					System.out.println(nome+" vuole leggere");
					// legge
					out.writeObject("lettura");
					out.flush();
					String situazione=(String)in.readObject();
					System.out.println(nome+" ha letto");
				} else {
					// scrive
					System.out.println(nome+" vuole muovere");
					out.writeObject("mossa");
					String miaMossa="mossa_"+ThreadLocalRandom.current().nextInt(1,10);
					out.writeObject(miaMossa);
					out.flush();
					String ack = (String)in.readObject();
					System.out.println(nome+" ha eseguito la mossa");
				}
			}
			System.out.println("Esco");
			out.writeObject("fine");
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			out.close();
			in.close();
			socket.close();
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException{
	new ClientSocket().exec();
	}
}