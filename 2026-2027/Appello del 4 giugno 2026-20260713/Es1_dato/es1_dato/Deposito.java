
public class Deposito {
	final int BUFFERSIZE;
	private int numItems = 0;
	private Roba[] valori;
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
	public Roba estrazione(int quality){
		Roba tmp=null;
		System.out.println("estrazione qual="+quality+" per "+Thread.currentThread().getName());
		mostra();
		int i=trova(quality);
		if(i>=0) {
			tmp=valori[i].copia();
			while(i+1<numItems) {
				valori[i]=valori[i+1];
				i++;
			}
			numItems--;
			System.out.println("estrazione eseguita:"+tmp+" per "+Thread.currentThread().getName());
			mostra();
			return tmp;
		}
		System.out.println("estrazione fallita per "+Thread.currentThread().getName());
		return tmp;
	}
	public boolean inserisci(Roba v) {
		System.out.println("inserimento "+v+" per "+Thread.currentThread().getName());
		if (numItems==BUFFERSIZE){
			return false;
		}
		valori[numItems++]=v;
		mostra();
		return true;
	}
}

