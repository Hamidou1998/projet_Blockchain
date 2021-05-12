/**
*
* @author Fantome_Hamidou
* 
*/
public class block {

	private Etat etatFinal;
	private Transaction transactionsEffectuee;
	
	
	public block(Etat etatFinal, Transaction transactionsEffectuee) {
		super();
		this.etatFinal = etatFinal;
		this.transactionsEffectuee = transactionsEffectuee;
	}
	
	public Etat getEtatFinal() {
		return etatFinal;
	}
	public Transaction getTransactionsEffectuee() {
		return transactionsEffectuee;
	}
	@Override
    public int hashCode() {
        return this.etatFinal.hashCode() * 31 + this.transactionsEffectuee.hashCode() * 31 * 31;
    }
}
