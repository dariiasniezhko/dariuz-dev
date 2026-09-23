import java.io.*;
import java.net.*;

public class Server {
	static final int PORT = 1999;
	int numGiocatori=3;
	Gioco g;

	public Server(){
		g = new Gioco(numGiocatori);
	}

	public void exec() throws IOException{
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server ON...");
		try{
			Socket socket = s.accept();
			new SlaveThread(socket, g).start();
		}
		finally{
			s.close();
		}
	}

	public static void main(String[] args) throws IOException{
		new Server().exec();
	}
}
