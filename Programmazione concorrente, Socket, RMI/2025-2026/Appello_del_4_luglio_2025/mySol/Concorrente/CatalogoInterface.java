
public interface CatalogoInterface  {
	public void inserimento(String tit, DepositoDocumenti p);
	public DepositoDocumenti trovaDeposito(String titolo);
	public int newPeerId();
}
