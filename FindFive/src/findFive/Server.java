/**
 * @author Zino Onokpise
 * Server side of Find Five game
 */
package findFive;

import java.io.*;
import java.net.*;
import java.util.Date;

public class Server {
	private ServerSocket serverSocket; 
	private Player[] players;  
	
	private PrintWriter out; 
	private BufferedReader in; 
	
	private final int NUM_OF_PLAYERS = 2; 
	
	private Logic gameLogic; 
	
	
	public Server() {
		players = new Player[NUM_OF_PLAYERS];  
		gameLogic = new Logic(); 
	}
	
	public void createAndListen(int port) {
		Thread createAndListenThread = new Thread() {
			public void run() {
				try {
					int currentPlayers = 0;
					boolean gameBegun =  false; 
					serverSocket = new ServerSocket(port); 
					System.out.println("Server up at port: " + port);
					while(true) {
						Socket client = serverSocket.accept(); 
						
						
						if(currentPlayers < NUM_OF_PLAYERS ) {  
							in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
							out = new PrintWriter(client.getOutputStream(), true); 
							players[currentPlayers] = new Player(client); 
							
							if(players[currentPlayers].isConnected()) {
								System.out.println(players[currentPlayers].getName() + " is connected");
								out.println(++currentPlayers); 
							}
							else {
								System.out.println("Player " + currentPlayers + " connection failed");
							}
							
							
						}
						else{
							out = new PrintWriter(client.getOutputStream(), true); 
							System.out.println("Additional client tried to connect from " + client.getInetAddress() ); 
							out.println(0); 
						}
						
						if(isPlayersComplete() && !gameBegun) {
							gameBegun = true; 
							runGame(); 
						}
						
						if(gameBegun && isPlayersComplete() == false) {
							closeConnection(); 
							return; 
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
		};
		createAndListenThread.start();
	}
	
	/**
	 * Check if desired number of players are connected 
	 * @return
	 */
	public boolean isPlayersComplete() {
		boolean complete = true; 
		for(int index = 0; index < NUM_OF_PLAYERS; index++) {
			if(players[index] == null || !players[index].isConnected()) {
				complete = false; 
				return complete; 
			}
		}
		return complete; 
	}
	
	/**
	 * Close connection with all players 
	 */
	public void closeConnection() {
		System.out.print("Players not complete...closing socket");
		for(int index = 0; index < NUM_OF_PLAYERS; index++) {
			players[index].sendMessage("Game over..closing");
			players[index].close(); 
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runGame() {
		System.out.print(gameLogic.toString());
		if(isPlayersComplete()) { //send initial gameBoard to all players
			for(int index = 0; index < NUM_OF_PLAYERS; index++) {
				players[index].sendMessage(gameLogic.toString());
			}

		}
			
		Thread gameThread = new Thread() {
			public void run() {
				int turn = 0; 
				
				while(isPlayersComplete() && !gameLogic.isFilled()) {
					int currentPlayerIndex = turn % NUM_OF_PLAYERS;  
					
					players[currentPlayerIndex].sendMessage("Please enter a column (0-8) *cause you're all engineers*: ");
					System.out.println("Sent message to : " + players[currentPlayerIndex].getName());
					for(int index = 0; index < NUM_OF_PLAYERS ; index++) { //send wait message to rest of players 
						if(index != currentPlayerIndex ) {
							players[index].sendMessage("Waiting on " + players[currentPlayerIndex].getName()); 
						}
					}
					
					String playStatus = ""; 
					String play = ""; 
					int playerColumnEntry; 
					
					do {
						
						play = players[currentPlayerIndex].receiveMessage();
						
						if(play.contains("exception")) {
							closeConnection(); 
							return; 
						}
						
						System.out.println(players[currentPlayerIndex].getName() +": " + play);
						
						try {
							playerColumnEntry = Integer.parseInt(play); 
							playStatus = gameLogic.fill(playerColumnEntry, currentPlayerIndex + 1);
						}catch (NumberFormatException e) {
							playStatus = "invalid value";
						}
						
						players[currentPlayerIndex].sendMessage(playStatus);
					}
					while( playStatus != "successful") ;
					
					System.out.println(gameLogic.toString());
					
					for(int index = 0; index < NUM_OF_PLAYERS ; index++) { //send updated game Board;  
						players[index].sendMessage(gameLogic.toString()); 
					}
					
					if(gameLogic.hasWon(currentPlayerIndex + 1)){//check if current player won 
						for(int index = 0; index < NUM_OF_PLAYERS ; index++) { //send has won message then proceed to close sockets
							
							if(index == currentPlayerIndex) {
								players[currentPlayerIndex].sendMessage("You won :)");
							}
							else {
								players[index].sendMessage("You lost"); 
							}
						}
					}
					
					turn++;
					
				}
				
				if(isPlayersComplete() == false) {
					closeConnection(); 
				}
			}
		};
		gameThread.start();
	
	}
	

	
	public static void main(String[] args) {
		Server mainServer = new Server(); 
		mainServer.createAndListen(9879);

	}

}
