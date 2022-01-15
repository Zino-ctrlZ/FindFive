/**
 * @author Zino Onokpise
 * Logic class will hold businessLogic of FindFive game
 */
package findFive;

import java.util.Random;

public class Logic {
	
	private static final int ROW = 6; 
	private static final int COLUMN = 9; 
	private char[][] gameBoard ;
	
	public Logic() {
		gameBoard = new char[ROW][COLUMN];
	}
	
	
	
	/**
	 * Fill bottom most empty row of a specified column in gameBoard with a character ('o' or 'x')
	 * @param columnEntry column to place character
	 * @param player player requesting to fill game board  
	 */
	public String fill(int columnEntry, int player ) {
		char playerEntry = '/'; 
		String statusMessage = ""; 
		//error checking on parameters
		if( player != 1 && player != 2) {
			statusMessage += "invalid player integer"; 
			return statusMessage; 
		}
		
		if(columnEntry < 0 || columnEntry >= COLUMN) {
			statusMessage += "invalid columnEntry";
			return statusMessage; 
		}
		
		
		if(player == 1) {
			playerEntry = 'x'; 
		}
		else {
			playerEntry = 'o'; 
		}
		
		int rowIndex = 0; 
		while(rowIndex + 1 < ROW && isEmpty(gameBoard[rowIndex + 1][columnEntry])) { //incrementing rowIndex till it is right above the last filled row on the column
			rowIndex++; 
		}
		
		if(!isEmpty(gameBoard[rowIndex][columnEntry])) { //this means the entire row has been filled 
			statusMessage += "Cant place here"; 
			return statusMessage; 
		}
		else {
			gameBoard[rowIndex][columnEntry] = playerEntry; 
			statusMessage += "successful"; 
		}
		
		return statusMessage; 
	}
	
	/**
	 * helper function for readability that checking if char is uninitialized
	 * @param c
	 * @return
	 */
	private boolean isEmpty(char c) {
		return c == 0; 
	}
	
	
	
	/**
	 * String representation of game Board
	 */
	public String toString() {
		String board = ""; 
		for(int index = 0; index < ROW; index++ ) {
				board +=  "[" + gameBoard[index][0] + "] " + "[" + gameBoard[index][1] + "] " + "[" + gameBoard[index][2] + "] " + "[" + gameBoard[index][3] + "] " + "[" + gameBoard[index][4] + "] "
						+ "[" + gameBoard[index][5] + "] " + "[" + gameBoard[index][6] + "] " + "[" + gameBoard[index][7] + "] " + "[" + gameBoard[index][8] + "] " + "\n";
				
		}
		return board;
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean hasWon(int player) {
		boolean won = false; 
		final int MATCHNUM = 5; 
		char playerEntry = '/';
		
		
		if( player != 1 && player != 2) {
			return false; 
		}
		
		if(player == 1) {
			playerEntry = 'x'; 
		}
		else {
			playerEntry = 'o'; 
		}
		
		//check rows 
		for(int rowIndex = 0; rowIndex < ROW; rowIndex++) {
			int matches = 0; 
			for(int columnIndex = 0; columnIndex < COLUMN; columnIndex++) {
				if(gameBoard[rowIndex][columnIndex] == playerEntry) {
					matches++; 
				}
				else {
					matches = 0; 
				}
				
				
				if(matches == MATCHNUM) { 
					won = true; 
					return won; 
				}
			}
			
		}
		
		//check columns 
		for(int columnIndex = 0; columnIndex < COLUMN; columnIndex++) {
			int matches = 0; 
			for(int rowIndex = 0; rowIndex < ROW; rowIndex++) {
				if(gameBoard[rowIndex][columnIndex] == playerEntry) {
					matches++;
				}
				else {
					matches = 0; 
				}
				
				if(matches == MATCHNUM) {
					won = true; 
					return won; 
				}
			}
			
		}
		
		
		//check lower left to upper right diagonals 
		for(int firstRow = 0; firstRow < ROW ; firstRow++) {
			int rowIndex = firstRow;
			int columnIndex = 0;
			int matches = 0; 
			for( ; rowIndex >= 0; rowIndex--) {
				if(gameBoard[rowIndex][columnIndex] == playerEntry) {
					matches++; 
				}
				else {
					matches = 0; 
				}
				
				if(matches == MATCHNUM) {
					won = true; 
					return won; 
				}
				
				columnIndex++; 
			}
		}
		
		for(int lastColumn = 1; lastColumn < COLUMN ; lastColumn++) {
			int columnIndex = lastColumn; 
			int rowIndex = ROW - 1; 
			int matches = 0; 
			while(rowIndex <= 0 && columnIndex < COLUMN ) {
				if(gameBoard[rowIndex][columnIndex] == playerEntry) {
					matches++; 
				}
				else {
					matches = 0; 
				}
				
				if(matches == MATCHNUM) {
					won = true; 
					return won; 
				}
				
				columnIndex++;
				rowIndex--; 
			}
		}
		
		//check upper left to lower right diagonals
		for(int firstRow = ROW - 1; firstRow >= 0; firstRow--) {
			int rowIndex = firstRow; 
			int columnIndex = 0; 
			int matches = 0; 
			for(; rowIndex < ROW; rowIndex++ ) {
				if(gameBoard[rowIndex][columnIndex] == playerEntry) {
					matches++; 
				}
				else {
					matches = 0; 
				}
				
				if(matches == MATCHNUM) {
					won = true; 
					return won; 
				}
				
				columnIndex++; 
			}
			
		}
		
		for(int firstColumn = 1; firstColumn < COLUMN; firstColumn++) {
			int columnIndex = firstColumn; 
			int rowIndex = 0; 
			int matches = 0;
			
			while(rowIndex < ROW && columnIndex < COLUMN) {
				if(gameBoard[rowIndex][columnIndex] == playerEntry) {
					matches++;
				}
				else {
					matches = 0; 
				}
				
				if(matches == MATCHNUM) {
					won = true; 
					return won; 
				}
				columnIndex++; 
				rowIndex++; 
			}
		}
		
		
		return won; 
	}
	
	/*
	 * Check if entire game Board has been filled
	 */
	public boolean isFilled() {
		return (!isEmpty(gameBoard[0][0]) && !isEmpty(gameBoard[0][1]) && !isEmpty(gameBoard[0][2]) && !isEmpty(gameBoard[0][3]) &&
				!isEmpty(gameBoard[0][4]) && !isEmpty(gameBoard[0][5]) && !isEmpty(gameBoard[0][6]) && !isEmpty(gameBoard[0][7]) && !isEmpty(gameBoard[0][8]) );
	}
	
	public static void main(String [] args) {
		Logic gameLogic = new Logic(); 
		int steps = 0; 
		Random rand = new Random(); 
		
		while(!gameLogic.isFilled() && gameLogic.hasWon(1) == false && gameLogic.hasWon(2) == false) {
			System.out.println(gameLogic.toString()); 
			if(steps % 2 == 0) {
				int position = rand.nextInt(9); 
				while(gameLogic.fill(position, 1) == "Cant place here") {
					position = rand.nextInt(9); 
				}
				steps++; 
			}
			else {
				int position = rand.nextInt(9); 
				while(gameLogic.fill(position, 2) == "Cant place here") {
					position = rand.nextInt(9); 
				}
				steps++; 
			}
			
		}
		
		if(gameLogic.hasWon(1)) {
			System.out.println("player 1 wins"); 
		}
		else if(gameLogic.hasWon(2)) {
			System.out.println("player 2 wins"); 
		}
		else {
			System.out.println(steps);
		}
		
		System.out.println(gameLogic.toString()); 
	}

}
