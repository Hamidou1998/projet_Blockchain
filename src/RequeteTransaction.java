import java.io.Serializable;

public class RequeteTransaction implements Serializable {
	
	public Transaction transaction;
	public int sel;
	
	public RequeteTransaction(Transaction transaction, int sel) {
		super();
		this.transaction = transaction;
		this.sel = sel;
	}
	
}
