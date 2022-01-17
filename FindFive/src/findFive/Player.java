/**
 * @author Zino Onokpise
 * Player Class for Find Five
 */
package findFive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
	private String name; 
	private Socket socket;  
	private PrintWriter output; 
	private BufferedReader input; 
	
	
	
	public Player(Socket skt) {
		socket = skt; 
		try {
			output = new PrintWriter(skt.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(skt.getInputStream()));
			name = input.readLine(); 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	/**
	 * Get player name 
	 * @return player name
	 */
	public String getName() {
		return name; 
	}
	
	/**
	 * Send message to player 
	 * @param message data to be sent 
	 */
	public void sendMessage(String message) {
		output.println(message);
	}
	
	/**
	 * Receive message from player input stream 
	 * @return message 
	 */
	public String receiveMessage() {
		String message = ""; 
		try {
			message = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return message; 
	}
	
	/**
	 * Get connection status of player
	 * @return connection status 
	 */
	public boolean isConnected() {
		return socket.isBound(); 
	}
	
}
