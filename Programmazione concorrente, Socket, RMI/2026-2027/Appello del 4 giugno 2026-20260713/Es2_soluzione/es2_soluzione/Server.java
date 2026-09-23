import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

public class Server {
	Deposito ilDeposito;
	Server(){
		ilDeposito=new Deposito(4);
	}
	public void exec() throws IOException {
		ServerSocket s = new ServerSocket(8999);
		System.out.println("Server inizia");
		while (true) {
			Socket socket = s.accept();
			System.out.println("Server accepted connection");
			new SlaveDeposito(socket, ilDeposito);
		}
//		s.close();
//		System.out.println("Server: chiudo.");
	}
	public static void main(String[] args) throws IOException {
		new Server().exec();
	}
}
