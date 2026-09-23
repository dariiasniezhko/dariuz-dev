
public class IlMain {
	static final int numPeers=4;
	Catalogo ilCatalogo=new Catalogo();
	Thread[] iPeers=new Thread[numPeers];
	
	private void exec() {
		for(int id=0; id<numPeers; id++) {
			iPeers[id]=new Peer(ilCatalogo);
		}
		for(int id=0; id<numPeers; id++) {
			iPeers[id].start();
		}
	}
	
	public static void main(String[] args) {
		new IlMain().exec();
	}
}
