import java.io.*;
import java.net.*;

public class SlaveDeposito extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	Deposito ilDeposito;
	protected SlaveDeposito(Socket s, Deposito dep) throws IOException  {
		ilDeposito=dep;
		socket = s;
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());
		start();
	}
	public void run() {
		String str;
		while(true) {
			try {
				str=(String) in.readObject();
				if(str.equals("end")) {
					break;
				}
				if(str.equals("inserimento")) {
					Roba r=(Roba) in.readObject();
					ilDeposito.inserisci(r);
				}
				if(str.equals("estrazione")) {
					int qual= (int) in.readObject();
					Roba r=ilDeposito.estrazione(qual);
					out.writeObject(r);			
				}
			} catch (IOException | ClassNotFoundException e) {
				// gestione eccezione omessa
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("Socket non chiuso normalmente");
		}
	}
}
