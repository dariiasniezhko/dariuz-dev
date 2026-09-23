import java.io.*;
import java.net.*;

public class SlaveThread extends Thread{
	Socket socket;
	Gioco ilGioco;
	Giocatore ilGiocatore;
	ObjectInputStream in;
	ObjectOutputStream out;

	public SlaveThread(Socket s, Gioco g) throws IOException{
		this.socket=s;
		this.ilGioco=g;
		out=new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in=new ObjectInputStream(socket.getInputStream());
	}

	public void run(){
		try {
			int id;
			while (true) {
				String cmd = (String)in.readObject();
				if(cmd.equals("fine")) break;
				else if(cmd.equals("gioco")){
					id=(Integer)in.readObject();
					ilGioco.giocata(id);
				}
				else if(cmd.equals("lettura")){
					id=(Integer)in.readObject();
					ilGioco.letturaEsito(id);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
