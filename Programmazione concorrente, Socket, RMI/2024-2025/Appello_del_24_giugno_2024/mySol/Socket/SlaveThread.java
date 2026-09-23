import java.io.*;
import java.net.*;

public class SlaveThread extends Thread{
	static int count=0;
	Tavolo ilTavolo;
	Gestore ilGestore;
	int id;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;

	public SlaveThread(Socket s, Gestore g) throws IOException{
		this.id=++count;
		this.socket=s;
		this.ilGestore=g;
		this.setName("Giocatore"+id);
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
	}

	public void run(){
		try {
			while (true) {
				String comando=(String)in.readObject();
				if(comando.equals("fine"))break;
				else if(comando.equals("accesso")){
					String nome=this.getName();
					out.writeObject(nome);
					out.flush();
				}
				else if(comando.equals("lettura")){
					String situazione=ilGestore.leggi();
					out.writeObject(situazione);
					out.flush();
				}
				else if(comando.equals("mossa")){
					String m = (String)in.readObject();
					ilGestore.mossa(m);
					String ack = "OK";
					out.writeObject(ack);
					out.flush();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
