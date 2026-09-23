import java.net.*;
import java.io.*;

public class Server {
	Tavolo tavolo;
	Gestore ilGestore;
	Server(){
		tavolo= new Tavolo();
		ilGestore=new Gestore(tavolo);
	}
	public void exec() throws IOException {
		ServerSocket s = new ServerSocket(8999);
		System.out.println("Server inizia");
		try {
			while (true) {
				Socket socket = s.accept();
				System.out.println("Server accepted connection");
				new SlaveThread(socket, ilGestore).start();
			}
		} finally {
			s.close();
		}
	}
	public static void main(String[] args) throws IOException {
		new Server().exec();
	}
}


