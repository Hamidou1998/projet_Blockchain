import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
*
* @author Fantome_Hamidou
* 
*/
public class Etat implements Serializable, Cloneable  {

	//Le tableau de l'argent dont l'indice sont les personnes 
	private Integer[] portefeuille;
		
	//Constructeur
    public Etat(Integer[] tab) {
		this.portefeuille = tab.clone();
	 }
    //construteur en copie 
    Etat(Etat etat) { 
        this(etat.portefeuille.clone());
    }
    
    //la methode clone pour la copie du constructeur
    public Etat clone(){
	    return new Etat(this.portefeuille.clone());
	 }
    
	public Etat Transaction(Transaction t ) {
		
		Integer[] personne = this.portefeuille.clone();
    	personne[t.getPayeur()]=personne[t.getPayeur()]-t.getSomme();
    	personne[t.getReceveur()] =personne[t.getReceveur()] + t.getSomme();
		return new Etat(personne);
	}
	
    @Override
	public int hashCode() {
		return Arrays.hashCode(portefeuille);
	 }
     
    public Integer[] getPortefeuille() {
		return portefeuille;
	}

	public void setPortefeuille(Integer[] portefeuille) {
		this.portefeuille = portefeuille;
	}

	public int getPortefeuille(int personne) {
		return this.portefeuille[personne];
	}
	
	private Integer[] getPersonne() {
		return this.portefeuille;
	}

	
}