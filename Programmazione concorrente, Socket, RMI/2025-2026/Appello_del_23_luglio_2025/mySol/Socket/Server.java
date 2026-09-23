import java.io.*;
import java.net.*;

public class Server{
	static final int PORT=1999;
	TavoloGioco ilTavoloGioco;
	int NUM_GIOCATORI = 4;

	public Server(){
		ilTavoloGioco=new TavoloGioco(NUM_GIOCATORI);
	}

	public void exec() throws IOException{
		ServerSocket s = new ServerSocket(PORT);
		try{
			System.out.println("Server ON");
			while (true) {
				Socket socket = s.accept();
				new SlaveThread(socket, ilTavoloGioco).start();
			}
		}
		finally{
			s.close();
		}
	}

	public static void main(String[] args) throws IOException {
		new Server().exec();
	}
}