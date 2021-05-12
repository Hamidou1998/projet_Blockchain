import java.util.ArrayList;

/**
*
* @author Fantome_Hamidou
* 
*/

public class blockchain {

	public ArrayList<Jonction> listBlock ;

	public blockchain(ArrayList<Jonction> block) {
		super();
		this.listBlock = block;
	}
	public blockchain() {
		listBlock = new ArrayList<>();
	}
	//methode qui permet la repartision des monaies 
	//ou tout les personne a l'etat initiale ont une somme de 200
	 public void initialisation() {
		   Integer[] personne=new Integer[20];
	        for (int i = 0; i < personne.length; i++) {
	            personne[i]=200;
	        }
	        Etat etat = new Etat(personne);
	        //this.listBlock.add(new Jonction(null, new block(etat, null)));
	    }
	 //On prends le dernier block 
	public Jonction dernierBlock() {
		return this.listBlock.get(this.listBlock.size() - 1);
	}
	public boolean RangeVerif(int i) {
	        return i >= 0 && i < this.dernierBlock().block.getEtatFinal().getPortefeuille().length;
	  }
	public boolean verif(block block) {
		
		if (!RangeVerif(block.getTransactionsEffectuee().payeur)) {
            return false;
        }
        if (!RangeVerif(block.getTransactionsEffectuee().receveur)) {
            return false;
        }       
		if(this.listBlock.isEmpty())
		{
			return true;
		}
		return this.dernierBlock().block.getEtatFinal().Transaction(block.getTransactionsEffectuee())==block.getEtatFinal();		
	}   
	
	public int getHashNewNode(block bloc, int sel) {
		return (new int[] {bloc.hashCode(), sel, this.dernierBlock().getHash()}).hashCode();
	}
	
	public boolean inserable(int difficulty, int sel, block block) {
		String hash = Integer.toBinaryString(
				this.getHashNewNode(block, sel)
				);
		return Integer.parseInt(hash.substring(0, difficulty)) == 0;
	  
	}

	public boolean addBlock(Transaction transaction, int sel,int difficulte)
	{
		
		 Etat etat = new Etat(this.dernierBlock().block.getEtatFinal());
	     block b = new block(etat, transaction);
	     
		if (this.verif(b) == false) {
			return false;
		}
		if (this.inserable(difficulte, sel, b)) {
			return false;
		}
		Jonction j = new Jonction(b, sel, this.getHashNewNode(b, sel));
		this.listBlock.add(j);
		return true;
	}
	
}
