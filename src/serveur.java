import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
*
* @author Fantome_Hamidou
* 
*/

public class serveur extends Thread {
	
	
	volatile int difficulte = 3;
	volatile static blockchain blockchaine;
	ServerSocket transactionServeur;
	
	public ServerSocket initSocket;
	private final int portInit=5051;
	
	public ServerSocket TransactionSocket;
	private final int portTransaction=5052;
	
	 private final int portmulticast = 5053;
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
				OutputStream o = socket.getOutputStream();
				ObjectOutputStream os=new ObjectOutputStream(o);
				os.writeObject(serveur.blockchaine);
				os.close();
					
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			super.run();
		}
	}
	public class TransactionTask extends Thread{
		
		Socket socket;

		
		public TransactionTask(Socket socket) {
			super();
			this.socket = socket;
		}
		
		public void run() {
			 try {
				socket.setSoTimeout(1000);
				OutputStream os=socket.getOutputStream();
				//ObjectInputStream is = new ObjectInputStream();
				
			} catch (SocketException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Le mineur n'est pas correct!!");
				e.printStackTrace();
			}
			super.run();
		}
		
	}
}
