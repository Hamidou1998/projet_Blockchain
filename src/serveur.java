import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
*
* @author Fantome_Hamidou
* 
*/

public class serveur extends Thread {
	
	
	volatile static int difficulte = 3;
	volatile static blockchain blockchaine;
	ServerSocket transactionServeur;
	
	public ServerSocket initSocket;
	private final int portInit=5051;
	
	public ServerSocket TransactionSocket;
	private final int portTransaction=5052;
	
	 private final int portmulticast = 5053;
	 volatile static ArrayList<Socket> mineur = new ArrayList<>();
	 public ServerSocket multicastSocket;
	 
	 
	public static void main(String[] args) {
		new serveur().start();
	}
	
	public void run() {
		
		blockchaine=new blockchain();
		blockchaine.initialisation();
		
		try {
			
			this.initSocket=new ServerSocket(portInit);
			this.TransactionSocket=new ServerSocket(portTransaction);
			this.multicastSocket=new ServerSocket(portmulticast);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		super.run();
	}
	
	public class initTask extends Thread {
		
		private Socket socket;
		
		public initTask(Socket socket) {
			super();
			this.socket = socket;
		}
		
	public void run() {
					
		try {
				this.socket.setSoTimeout(100);
				socket = initSocket.accept();
				System.out.println("Un nouveau mineur demande l'etat...");
				OutputStream o = socket.getOutputStream();
				ObjectOutputStream os=new ObjectOutputStream(o);
				os.writeObject(serveur.blockchaine);
				os.close();
					
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			finally {
	            try {
	                socket.close();
	            } catch (IOException ex) {
	        }
			
		}
	}
	public class TransactionTask extends Thread{
		
		Socket socket;
		RequeteTransaction transactionRequest;
		
		public TransactionTask(Socket socket) {
			super();
			this.socket = socket;
		}
		
		public void run() {
			 blockchain chaine = serveur.blockchaine;
		     boolean valid = false;
			 try {
					socket.setSoTimeout(1000);
					InputStream os=socket.getInputStream();
					ObjectInputStream is = new ObjectInputStream(os);
		            transactionRequest = (RequeteTransaction) is.readObject();
					 
		            chaine.addBlock(transactionRequest.transaction, transactionRequest.sel,difficulte);
		            //serveur.nextDifficulte();
		            valid = true;
		            
			} catch (SocketException e) {
				
				e.printStackTrace();
				
			} catch (IOException e) {
				System.err.println("Le mineur n'est pas correct!!");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		     if (!socket.isClosed()) {
		            try {
		                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
		                if (valid) {
		                    ReponseTransaction reponse = new ReponseTransaction(transactionRequest.transaction, transactionRequest.sel, serveur.difficulte, true);
		                   // serveur.multicast(reponse); 
		                    os.writeObject(reponse);
		                    os.close();

		                } else {
		                    ReponseTransaction response = new ReponseTransaction(false);
		                    os.writeObject(response);
		                    os.close();
		                }
		            } catch (IOException ex) {
		            	ex.printStackTrace();
		            }

		            try {
		                socket.close();
		            } catch (IOException ex) {
		            	ex.printStackTrace();
		            }
		        }

		    }
		
		}
	public class MulticastTask extends Thread {
		 
		ReponseTransaction response;
		
		public MulticastTask(ReponseTransaction response) {
			super();
			this.response = response;
		}

		@Override
		public void run() {
			  serveur.mineur.forEach((mineur) -> {
		            try (ObjectOutputStream os = new ObjectOutputStream(mineur.getOutputStream())) {
		                os.writeObject(response);
		                os.flush();
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		        });
		    }
		}
	}
	
}
