import java.io.Serializable;
/**
*
* @author Fantome_Hamidou
* 
*/
public class RequeteTransaction implements Serializable {
	
	public Transaction transaction;
	public int sel;
	
	public RequeteTransaction(Transaction transaction, int sel) {
		super();
		this.transaction = transaction;
		this.sel = sel;
	}
	
}
