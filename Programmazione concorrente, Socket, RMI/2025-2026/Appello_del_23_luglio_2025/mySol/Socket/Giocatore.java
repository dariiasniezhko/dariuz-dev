import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.net.*;

public class Giocatore{
	static final int PORT=1999;
	private int id;
	TavoloGioco ilGestore;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	int NUM_GIOCATORI = 4;

	Giocatore() {
	}

	void myoutput(String s) {
		System.out.println("giocatore_"+id+": "+s);
	}

	int sceltaAttaccato() {
		// irrilevante: scegliamo chi attaccare in modo casuale
		int attaccato=id;
		while(attaccato==id) {
			attaccato=ThreadLocalRandom.current().nextInt(NUM_GIOCATORI);
		}
		return attaccato;
	}
	
	/*public void run() { */
	/*	Mossa m; */
	/*	if((id=ilGestore.aggiuntaGiocatore())<0) { */
	/*		// se il tavolo e` gia` completo non posso giocare, quindi termino. */
	/*		return; */
	/*	} */
	/*	myoutput("attendo inizio"); */
	/*	ilGestore.attendoInizio(); */
	/*	while(true) { */
	/*		myoutput("attendo turno"); */
	/*		ilGestore.attendoTurno(id); */
	/*		if(ilGestore.finita()) { */
	/*			break;  // partita finita */
	/*		} */
	/*		int attaccante=ilGestore.chiMiAttacca(id); */
	/*		// qui il giocatore dovrebbe consultare la situazione: omesso perche' irrilevante */
	/*		if(attaccante<0) { */
	/*			// nessuno mi attacca: tocca a me attaccare */
	/*			m=new Mossa(id, sceltaAttaccato(), "mossa_attacco_"+id); */
	/*		} else { */
	/*			// attaccante mi ha attaccato */
	/*			m=new Mossa(id, attaccante, "mossa_difesa_"+id); */
	/*		} */
	/*		myoutput("eseguo "+m); */
	/*		ilGestore.esecuzioneMossa(m); */
	/*	} */
	/*	myoutput("termino"); */
	/*} */

	public void exec() throws IOException{
		try {
			socket = new Socket("localhost", PORT);
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			String ack;
			myoutput("accedo");
			out.writeObject("accesso");
			out.writeObject(id);
			out.flush();
			ack = (String)in.readObject();
			System.out.println("Server: "+ack);
			myoutput("attendo inizio");
			while (true) {
				myoutput("attendo turno"); 
				out.writeObject("attendo turno");
				out.flush();
				ack = (String)in.readObject();
				if(ack.equals("FINE")) break;
				myoutput("eseguo mossa (quale?)");
				out.writeObject("eseguo mossa");
				out.flush();
				int attaccante=(Integer)in.readObject();
				String tipoMossa;
				if(attaccante<0){
					tipoMossa="attacco";
				}
				else{
					tipoMossa="difesa";
				}
				myoutput("eseguo mossa: "+tipoMossa);
				out.writeObject(tipoMossa);
				out.flush();
				ack = (String)in.readObject();
			}
			myoutput("termino");
			out.writeObject("fine");
			out.flush();
		} catch (IOException|ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			out.close();
			in.close();
			socket.close();
		}
	}

	public static void main(String[] args) throws IOException {
		new Giocatore().exec();
	}
}
