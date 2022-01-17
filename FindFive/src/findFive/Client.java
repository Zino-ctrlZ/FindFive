/**
 * @author Zino Onokpise
 * Client side of Find Five game
 */
package findFive;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	private String clientName; 
	private Socket clientSocket; 
	private PrintWriter output; 
	private BufferedReader input;  
	
	private boolean gameStatus; 
	
	public String initiate(String address, String name, int port) {
		String response = ""; 
		try {
			clientSocket = new Socket(InetAddress.getByName(address),port);
			output = new PrintWriter(clientSocket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 
			clientName = name;
			if(clientSocket.isBound()) {
				output.println(name);
				System.out.println(clientName);
			}
			else {
				System.out.println("Connection Failed"); 
			}
			
			response = input.readLine(); 
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return response; 
	}
	
	
	public void runGame(int turn) {
		if(turn <= 0) {
			System.out.println("Cannot join game now!"); 
			return; 
		}
		else {
			gameStatus = true; 
		}
		
		final int ROW_NUM = 6; 
		String gameBoard = getGameBoard(ROW_NUM); 
		System.out.println(gameBoard);
		
		
		
		
		Thread gameThread = new Thread() {
			String messageStatus; 
			Scanner screenInput = new Scanner(System.in); 
			String response; 
			String board;
			
			public void run() {
				while(gameStatus) {
					try {
						messageStatus = input.readLine();
						if(messageStatus == "you lost") {
							System.out.println(messageStatus); 
							return; 
						}
						else if(messageStatus != "Please enter a column (0-8) *cause you're all engineers*: ") {
							System.out.println("Waiting on " + messageStatus);
						}
						else { 
							do {
								if(messageStatus != "successful") {
									System.out.print(messageStatus + ": ");
								}
								response = screenInput.next(); 
								output.println(response); 	
								messageStatus = input.readLine();
							}	
							while(messageStatus != "successful");
						}
						
						board = getGameBoard(ROW_NUM); 
						System.out.println(board);
						
						
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			}
		};
		gameThread.start();
		
	}
	
	/**
	 * Produces game board
	 * @param lineNum number of rows in gameBoard
	 * @return gameBoard
	 */
	private String getGameBoard(int lineNum){
		String gameBoard = ""; 
		for(int index = 0; index < lineNum; index++) {
			try {
				gameBoard += input.readLine() + "\n";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return gameBoard; 
	}
	
	
	public static void main(String [] args) {
		Client player = new Client(); 
		int turn = Integer.parseInt(player.initiate("127.0.0.1", "himi", 9876));
		
		player.runGame(turn); 
		
		
	}
}


