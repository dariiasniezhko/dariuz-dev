import java.io.*;
import java.net.*;

public class Server{
	static final int PORT = 1999;
	Gestore g;
	Tavolo t;

	public Server(){
		t = new Tavolo();
		g = new Gestore(t);
	}

	public void exec() throws IOException{
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server ON");
		try {
			while (true) {
				Socket socket = s.accept();
				new SlaveThread(socket, g).start();
			}
		}
		finally{
			s.close();
		}
	}

	public static void main(String[] args) throws IOException{
		new Server().exec();
	}
}