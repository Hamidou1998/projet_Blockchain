import java.io.Serializable;

/**
*
* @author Fantome_Hamidou
* 
*/

public class ReponseTransaction implements Serializable{
	
	public Transaction transaction;
	public int sel;
	public int difficulte;
	public boolean accept;
	

    public ReponseTransaction(boolean accept) {
        this.accept = accept;
    }
	public ReponseTransaction(Transaction transaction, int sel, int difficulte,boolean accpet) {
		super();
		this.transaction = transaction;
		this.sel = sel;
		this.difficulte = difficulte;
		this.accept=accept;
	}
	
	
}
