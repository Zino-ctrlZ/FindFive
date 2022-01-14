/**
 * @author Zino Onokpise
 * Client side of Find Five game
 */
package findFive;

import java.io.*;
import java.net.*;

public class Client {
	private Socket clientSocket; 
	private PrintWriter out; 
	private BufferedReader in; 
	
	public void createAndListen(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream()); 
			in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream())); 
			while(clientSocket.isBound()) {
				if(in.readLine() != null) {
					System.out.println(in.readLine());
				}
			}
		} catch (IOException IOE) {
			IOE.printStackTrace();
		} 
	}
}
