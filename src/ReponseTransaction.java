import java.io.Serializable;

public class ReponseTransaction implements Serializable{
	
	public Transaction transaction;
	public int sel;
	public int difficulte;
	
	
	public ReponseTransaction(Transaction transaction, int sel, int difficulte) {
		super();
		this.transaction = transaction;
		this.sel = sel;
		this.difficulte = difficulte;
	}
	
	
}
