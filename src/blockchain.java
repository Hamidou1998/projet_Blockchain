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
	   void init() {
	        // l’initialisation de la répartition initiale  
	        // chaque individue commence avec un solde de 100 monnaie 

	        ArrayList<Integer> individues = new ArrayList<>(100);
	        for (int i = 0; i < individues.size(); i++) {
	            individues.set(i, 100);
	        }

	        Etat etat = new Etat(individues);
	        this.listBlock.add(new Jonction(null, new block(etat, null)));
	    }
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

	public boolean addBlock(block b, int sel)
	{
		if (this.verif(b) == false) {
			return false;
		}
		if (this.inserable(3, sel, b)) {
			return false;
		}
		Jonction j = new Jonction(b, sel, this.getHashNewNode(b, sel));
		this.listBlock.add(j);
		return true;
	}
	
	
}
