import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
*
* @author Fantome_Hamidou
* 
*/

public class serveur extends Thread {
	
	 	volatile static blockchain blockchaine;
	    volatile static int difficulte = 3; 

	    private final int multicastPort = 4151;
	    volatile static ArrayList<Socket> mineurs = new ArrayList<>();
	    ServerSocket multicastSocket;
	    
	    private final int transactionPort = 4152;
	    ServerSocket transactionSocket;
	    
	    private final int initPort = 4153;  
	    ServerSocket initSocket;
 	    volatile boolean interruption = true;
	 
	   serveur() throws IOException {
		   
		   	blockchaine=new blockchain();
			blockchaine.initialisation();

	        this.initSocket = new ServerSocket(this.initPort);
	        this.transactionSocket = new ServerSocket(transactionPort);
	        this.multicastSocket = new ServerSocket(this.multicastPort);
	        
	    }
	 	 	   
	    synchronized void nextDifficulte() {
	    	System.out.println("...........NON SUPPORTER...........");
	    }
		public void run() {
			try {
				while(interruption)
				{		
					Socket Initsocket =this.initSocket.accept();
					System.out.println("Un mineur demande l'actuel de la blockchaine ................");
					new initTask(Initsocket).start();
					
					Socket socketTransaction=this.transactionSocket.accept();
					System.out.println("Nouvelle transaction en cours de traitement .................");
					new TransactionTask(socketTransaction).start();
					
					Socket socketMulticast =this.multicastSocket.accept();
					System.out.println("Un nouveau mineur a été connecté au multitask ...............");					
					this.mineurs.add(socketMulticast);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	    public void close() {
	        this.interruption = true;
	
	        this.mineurs.forEach(mineurs -> {
	            try {
	                mineurs.close();
	            } catch (Exception ex) {
	            }
	        });
	        this.mineurs.clear();
	    }
	   public void multicast(ReponseTransaction rep) {
	        System.out.println("Muticast d'une nouvelle transaction");
	        new MulticastTask(rep);
	    }
	   
	   //class initTask
	   
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
		}
			
		//class multicastTask
	public class MulticastTask extends Thread {
			 
			ReponseTransaction response;
			
			public MulticastTask(ReponseTransaction response) {
				super();
				this.response = response;
			}
		
			@Override
			public void run() {
				  serveur.mineurs.forEach((mineur) -> {
			            try (ObjectOutputStream os = new ObjectOutputStream(mineur.getOutputStream())) {
			                os.writeObject(response);
			                os.flush();
			            } catch (IOException ex) {
			                ex.printStackTrace();
			            }
			        });
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
			            nextDifficulte();
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
			                    multicast(reponse); 
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
		
	/* ---------------*********************************    Main   *******************************------------------*/

	  public static void main(String[] args) {
		  System.out.println("\n\n***************** Serveur d'execution ******************\n");
	        try {
	            serveur serveur = new serveur();
	            System.out.println("Serveur a été initializé ");
	            System.out.println("La blockchaine a un hash de " + serveur.blockchaine.hashCode());
	            Scanner scan = new Scanner(System.in);
	            serveur.start();
	            while(true)
	            {
	            	System.out.println("Veillez saisir 1 pour afficher le hash du blockchaine");
					int num=scan.nextInt();
						if(num==1) {
							System.out.println("Le hash du blockchaine est "+serveur.blockchaine.hashCode()+".......");
						}
						else
						{
							serveur.close();
							break;
						}	
	            	}
	            
	            System.out.println("*************  Le serveur est arreter!!! Au revoir  *************** ");

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	    }
	
}
