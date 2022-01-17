/**
 *  @author Zino Onokpise
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
	
	public boolean isClosingMessage(String message) {
		if(message.contains("closing")) {
			gameOver();
			return true;
		}
		else {
			return false; 
		}
		 
	}
	
	/**
	 * Closing streams 
	 */
	private void gameOver() {
		System.out.println("Game closed"); 
		try {
			clientSocket.close(); 
			output.close(); 
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
						do{
							messageStatus = input.readLine();
							if(isClosingMessage(messageStatus)) return; 
						}
						while(messageStatus.length() == 0);
						
						if(messageStatus.contains("You lost")) {
							System.out.println(messageStatus); 
							return; 
						}
						else if (messageStatus.contains("You won")) {
							System.out.println(messageStatus); 
							return; 
						}
						else if(messageStatus.contains("Please enter a column")) {
							do {
								if(!messageStatus.contains("successful")) {
									System.out.print(messageStatus);
								}
								response = screenInput.next(); 
								output.println(response); 	
								messageStatus = input.readLine();
								if(isClosingMessage(messageStatus)) return; 
							}	
							while(!messageStatus.contains("successful"));//successful prompt has length = 10, but change right after testing
						}
						else { 
							System.out.println(messageStatus);
						}
						
						board = getGameBoard(ROW_NUM); 
						if(isClosingMessage(board)) return; 
						System.out.println(board);
						
						
						
					} catch (IOException e) {
						gameOver(); 
						return; 
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
		
		if(args.length != 3) {
			System.out.print("Improper arguments");
			return; 
		}
		
		String inetAddr = args[0]; 
		String clientName = args[1]; 
		int port = Integer.parseInt(args[2]);
		
		
		Client player = new Client(); 
		int turn = Integer.parseInt(player.initiate(inetAddr, clientName, port));
		
		player.runGame(turn); 
		
		
	}
}



