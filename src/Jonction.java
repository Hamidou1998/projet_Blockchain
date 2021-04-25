import java.io.Serializable;

/**
*
* @author Fantome_Hamidou
* 
*/
public class Jonction implements Serializable {

	public block block;
	private int hash=0;
	private int sel;
	
		public Jonction(block b, int hash, int sel) {
			super();
			this.block=b;
			this.hash = hash;
			this.sel = sel;
		}
		public int getHash() {
		 if(this.hash==0) {
		 	this.calculHash();
		 }
		return hash;
		}

		public void setHash(int hash) {
			this.hash = hash;
		}

		public int getSel() {
			return sel;
		}
		
		public void setSel(int sel) {
			this.sel = sel;
		}
		
	    public void calculHash() {
	        if (this.block == null) {
	        	this.hash = 0;
	        }
	        this.hash = block.hashCode() * 31 + block.hashCode() * 31 * 31 + sel * 31 * 31 * 31;
	    }	 
	    @Override
	    public int hashCode() {
	        return this.getHash();
	    }
}
