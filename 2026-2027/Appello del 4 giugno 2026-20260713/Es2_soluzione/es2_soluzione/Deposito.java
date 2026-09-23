
public class Deposito {
	final int BUFFERSIZE;
	private int numItems = 0;
	private Roba[] valori;
	final long T=200;
	Deposito(int size){
		BUFFERSIZE=size;
		valori=new Roba[BUFFERSIZE];
		numItems = 0;
	}
	private void mostra() {
		System.out.print("[");
		for (int i=0; i<numItems; i++) {
			System.out.print(valori[i]);
		}
		System.out.println("]");		
	}

	private int trova(int q) {
		for(int i=0; i<numItems; i++) {
			if(valori[i].leggiQualita()>=q) {
				return i;
			}
		}
		return -1;
	}
	public synchronized Roba estrazione(int quality){
		Roba tmp=null;
		int idx;
		System.out.println("estrazione qual="+quality+" per "+Thread.currentThread().getName());
		mostra();
		long time0=System.currentTimeMillis();
		while((idx=trova(quality))<0 && System.currentTimeMillis()-time0<T) {
			long toWait=time0+T-System.currentTimeMillis();
			try {
				wait(toWait);
			} catch (InterruptedException e) { }
		}
		if(idx>=0) {
			tmp=valori[idx].copia();
			while(idx+1<numItems) {
				valori[idx]=valori[idx+1];
				idx++;
			}
			numItems--;
			System.out.println("estratto "+tmp+" per "+Thread.currentThread().getName());
			mostra();
			notifyAll();
			return tmp;			
		}
		System.out.println("estrazione fallita per "+Thread.currentThread().getName());
		return null;
	}

	public synchronized void inserisci(Roba v) {
		System.out.println("inserimento "+v+" per "+Thread.currentThread().getName());
		while (numItems==BUFFERSIZE){
			try {
				wait();
			} catch (InterruptedException e) { }
		}
		valori[numItems++]=v;
		mostra();
		notifyAll();
	}
}

