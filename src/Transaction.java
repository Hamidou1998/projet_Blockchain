
public class Transaction {

	public int somme;
	public int payeur;
	public int receveur;
	

	public Transaction(int somme, int payeur, int receveur) {
		super();
		this.somme = somme;
		this.payeur = payeur;
		this.receveur = receveur;
	}

	public int getSomme() {
		return somme;
	}
	
	public int getPayeur() {
		return payeur;
	}
	
	public int getReceveur() {
		return receveur;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + payeur;
		result = prime * result + receveur;
		result = prime * result + somme;
		return result;
	}

}
