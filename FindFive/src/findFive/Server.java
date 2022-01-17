/**
 * @author Zino Onokpise
 * Server side of Find Five game
 */
package findFive;

import java.io.*;
import java.net.*;

public class Server {
	private ServerSocket serverSocket; 
	private Player[] players;  
	private boolean playersComplete; 
	private PrintWriter out; 
	private BufferedReader in; 
	
	private final int NUM_OF_PLAYERS = 2; 
	
	private Logic gameLogic; 
	
	
	public Server() {
		players = new Player[2];  
		playersComplete = false; 
		gameLogic = new Logic(); 
	}
	
	public void createAndListen(int port) {
		Thread createAndListenThread = new Thread() {
			public void run() {
				try {
					int currentPlayers = 0;
					boolean gameStatus =  false; 
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
							
							if(currentPlayers == NUM_OF_PLAYERS) {
								playersComplete = true; 
							}
							
							
						}
						else{
							out = new PrintWriter(client.getOutputStream(), true); 
							System.out.println("Additional client tried to connect from " + client.getInetAddress() ); 
							out.println(0); 
						}
						
						if(playersComplete && !gameStatus) {
							gameStatus = true; 
							runGame(); 
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
	
	public void runGame() {
		System.out.print(gameLogic.toString());
		if(playersComplete) { //send initial gameBoard to all players
			for(int index = 0; index < NUM_OF_PLAYERS; index++) {
				players[index].sendMessage(gameLogic.toString());
//				players[index].sendMessage("board sent");
			}

		}
		
		
//		try { //pause thread for a millisecond 
//			this.wait(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		
		Thread gameThread = new Thread() {
			public void run() {
				int turn = 0; 
				
				while(playersComplete && !gameLogic.isFilled()) {
					int currentPlayerIndex = turn % NUM_OF_PLAYERS;  
					
					players[currentPlayerIndex].sendMessage("Please enter a column (0-8) *cause you're all engineers*: ");
					System.out.println("Sent message to : " + players[currentPlayerIndex].getName());
					for(int index = 0; index < NUM_OF_PLAYERS ; index++) { //send wait message to rest of players 
						if(index != currentPlayerIndex ) {
							players[index].sendMessage(players[currentPlayerIndex].getName()); 
						}
					}
					
					String playStatus = ""; 
					String play = ""; 
					int playerColumnEntry; 
					
					do {
						
						play = players[currentPlayerIndex].receiveMessage(); 
						playerColumnEntry = Integer.parseInt(play); 
						playStatus = gameLogic.fill(playerColumnEntry, currentPlayerIndex);
						players[currentPlayerIndex].sendMessage(playStatus);
					}
					while( playStatus != "successful") ;
					
					System.out.println(gameLogic.toString());
					
					for(int index = 0; index < NUM_OF_PLAYERS ; index++) { //send updated game Board;  
						players[index].sendMessage(gameLogic.toString()); 
					}
					
					if(gameLogic.hasWon(currentPlayerIndex)){//check if current player won 
						for(int index = 0; index < NUM_OF_PLAYERS ; index++) { //send has won message then proceed to close sockets
							
							if(index == currentPlayerIndex) {
								players[index].sendMessage("You won :)");
							}
							else {
								players[index].sendMessage("you lost"); 
							}
						}
					}
					
					turn++;
					
				}
			}
		};
		gameThread.start();
	
	}
	

	public static void main(String[] args) {
		Server mainServer = new Server(); 
		mainServer.createAndListen(9876);

	}

}
