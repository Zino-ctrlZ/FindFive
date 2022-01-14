/**
 * @author Zino Onokpise
 * Server side of Find Five game
 */
package findFive;

import java.io.*;
import java.net.*;

public class Server {
	private ServerSocket serverSocket; 
	private Socket[] clientSockets; 
	private boolean clientsConnected; 
	private PrintWriter out; 
	private BufferedReader in; 
	
	
	public Server() {
		clientSockets = new Socket[2]; //strictly only two sockets can be connected to the server at a time 
		clientsConnected = false; 
	}
	
	public void createAndListen(int port) {
		try {
			serverSocket = new ServerSocket(port); 
			
			while(true) {
				Socket client = serverSocket.accept(); 
				
				if(!clientSockets[0].isBound()) {
					clientSockets[0] = client; 
					out = new PrintWriter(client.getOutputStream()); 
					in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
					System.out.print(in.readLine());
					out.println("Hello client 1") ; 
				}
				else if(clientSockets[0].isBound() && !clientSockets[0].isBound()) {
					clientSockets[1] = client; 
					clientsConnected = true; 
					out = new PrintWriter(client.getOutputStream()); 
					in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
					System.out.print(in.readLine());
					out.println("Hello client 2") ; 
					clientsConnected = true; 
				}
				else if(clientSockets[0].isBound() && clientSockets[1].isBound()) {
					//return message that client cannot connect to server at the time and close socket
					out = new PrintWriter(client.getOutputStream()); 
					out.println("Cannot connect to server at this time"); 
					client.close(); 
				}
			}

			
		}
		catch(IOException IO) {
			IO.printStackTrace();
		}
		catch(IllegalArgumentException IAE) {
			IAE.printStackTrace();
		}
	}
	
	
	public void closeConnections() {
		if(clientsConnected == false) {
			try {
				in.close();
				out.close(); 
				clientSockets[0].close(); 
				clientSockets[1].close(); 
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}

	public static void main(String[] args) {
		Server mainServer = new Server(); 
		mainServer.createAndListen(6666);

	}

}
