import java.io.*;
import java.net.*;

public class SlaveThread extends Thread{
	TavoloGioco ilTavoloGioco;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;

	public SlaveThread(Socket socket, TavoloGioco ilTavoloGioco) throws IOException{
		this.socket = socket;
		this.ilTavoloGioco = ilTavoloGioco;
		out=new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in=new ObjectInputStream(socket.getInputStream());
	}

	public void run(){
		try{
			int id = 0;
			while(true){
				String cmd = (String) in.readObject();
				if(cmd.equals("fine")) break;
				else if(cmd.equals("accedo")){
					id=(Integer) in.readObject();
					String ack;
					if((id=ilTavoloGioco.aggiuntaGiocatore())<0) {
						ack="accesso negato";
					}
					else{
						ack="accesso consentito";
					}
					out.writeObject(ack);
					out.flush();
				}
				else if(cmd.equals("attendo inizio")){
					ilTavoloGioco.attendoInizio();
				}
				else if(cmd.equals("attendo turno")){
					ilTavoloGioco.attendoTurno(id);
					String ack = ilTavoloGioco.finita() ? "FINE" : "OK";
					out.writeObject(ack);
					out.flush();
				}
				else if(cmd.equals("eseguo mossa")){
					int attaccante = ilTavoloGioco.chiMiAttacca(id);
					out.writeObject(attaccante);
					out.flush();
					String tipoMossa = (String) in.readObject();
					Mossa m = new Mossa(id, attaccante, tipoMossa);	
					ilTavoloGioco.esecuzioneMossa(m);
					String ack = ilTavoloGioco.finita() ? "FINE" : "OK";
					out.writeObject(ack);
					out.flush();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
