/**
*
* @author Fantome_Hamidou
* 
*/
public class block {

	private Etat etatFinal;
	private Transaction transactionsEffectuee;
	
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
