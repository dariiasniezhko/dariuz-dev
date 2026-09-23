import java.io.*;
import java.net.Socket;

public class SlaveThread extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	Gestore ilGestoreDelGioco;
	protected SlaveThread(Socket s, Gestore g) throws IOException  {
		socket = s;
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());
		ilGestoreDelGioco=g;
	}

	public void run() {
		String command;
		try {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				command = (String) in.readObject();
				if(command.equals("end")) {
					break;
				}
				if(command.equals("mossa")) {
					String mossa=(String)in.readObject();
					ilGestoreDelGioco.mossa(mossa);
				}
				if(command.equals("lettura")) {
					String situazione = ilGestoreDelGioco.leggi();
					out.writeObject(situazione);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("IO Exception ");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}
	}
}
