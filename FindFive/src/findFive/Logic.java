/**
 * @author Zino Onokpise
 * Logic class will hold businessLogic of FindFive game
 */
package findFive;

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
	 * @param player player to requesting to fill game board  
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
	 * String representation of game Board
	 */
	public String toString() {
		String board = ""; 
		for(int index = 0; index < ROW; index++ ) {
				board +=  "[" + gameBoard[index][0] + "]" + "[" + gameBoard[index][1] + "]" + "[" + gameBoard[index][2] + "]" + "[" + gameBoard[index][3] + "]" + "[" + gameBoard[index][4] + "]"
						+ "[" + gameBoard[index][5] + "]" + "[" + gameBoard[index][6] + "]" + "[" + gameBoard[index][7] + "]" + "[" + gameBoard[index][8] + "]" + "\n";
				
		}
		return board;
	}
	
	/**
	 * helper function for readability for checking if char is uninitialized 
	 * @param c
	 * @return
	 */
	private boolean isEmpty(char c) {
		return c == 0; 
	}
	
	public static void main(String [] args) {
		Logic gameLogic = new Logic(); 
		System.out.println(gameLogic.fill(0, 1));
		System.out.println(gameLogic.fill(0, 1));
		System.out.println(gameLogic.fill(0, 1));
		System.out.println(gameLogic.fill(0, 1));
		System.out.println(gameLogic.fill(0, 1));
		System.out.println(gameLogic.fill(1, 2));
		
		
		System.out.println(gameLogic.fill(0, 1)); //expect cant place here 
		
		System.out.println(gameLogic.fill(-1, 1)); //invalid column entry 
		
		System.out.println(gameLogic.fill(0, 3)); //invalid player integer 
		System.out.print(gameLogic.toString()); 
	}

}
