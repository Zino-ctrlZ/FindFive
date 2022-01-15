package findFive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class TestLogic {
	@Test
	public void testFillSuccessful(){
		Logic gameLogic = new Logic(); 
		String value = gameLogic.fill(1, 1); 
		String expected = "successful"; 
		assertEquals(expected, value); 
	}
	
	@Test
	public void testFillInvalidColumnEntry(){
		Logic gameLogic = new Logic(); 
		String value = gameLogic.fill(9, 1); 
		String expected = "invalid columnEntry"; 
		assertEquals(expected, value); 
	}
	
	@Test
	public void testFillInvalidplayerInteger(){
		Logic gameLogic = new Logic(); 
		String value = gameLogic.fill(1, 3); 
		String expected = "invalid player integer"; 
		assertEquals(expected, value); 
	}
	
	@Test
	public void testHasWonCheckRows(){
		Logic gameLogic = new Logic(); 
		gameLogic.fill(0, 2); 
		gameLogic.fill(1, 2); 
		gameLogic.fill(2, 2); 
		gameLogic.fill(3, 2); 
		gameLogic.fill(4, 2); 
		gameLogic.fill(5, 1); 
		gameLogic.fill(6, 1); 
		gameLogic.fill(7, 2); 
		gameLogic.fill(8, 1); 
		
		boolean expected = true; 
		boolean value = gameLogic.hasWon(2); 
		assertEquals(expected, value); 
	}
	
	@Test
	public void testHasWonCheckColumn(){
		Logic gameLogic = new Logic(); 
		gameLogic.fill(0, 1); 
		gameLogic.fill(0, 2); 
		gameLogic.fill(0, 2); 
		gameLogic.fill(0, 2); 
		gameLogic.fill(0, 2); 
		gameLogic.fill(1, 1); 
		gameLogic.fill(1, 1); 
		gameLogic.fill(1, 2); 
		gameLogic.fill(1, 1); 
		
		boolean expected = false; 
		boolean value = gameLogic.hasWon(1); 
		assertEquals(expected, value); 
	}
	
	@Test 
	public void testHasWonCheckDiagonalsLowerLeft(){
		Logic gameLogic = new Logic();
		gameLogic.fill(0, 2);
		gameLogic.fill(0, 1);
		gameLogic.fill(0, 2);
		gameLogic.fill(0, 1);
		
		gameLogic.fill(1, 2);
		gameLogic.fill(1, 2);
		gameLogic.fill(1, 1);
		
		gameLogic.fill(2, 1);
		gameLogic.fill(2, 1);
		gameLogic.fill(2, 2);
		gameLogic.fill(2, 1);
		
		gameLogic.fill(3, 1);
		gameLogic.fill(3, 2);
		gameLogic.fill(3, 1);
		gameLogic.fill(3, 2);
		gameLogic.fill(3, 1);
		
		gameLogic.fill(4, 2);
		gameLogic.fill(4, 2);
		gameLogic.fill(4, 1);
		gameLogic.fill(4, 2);
		gameLogic.fill(4, 2);
		
		System.out.println(gameLogic.toString()); 
		
		boolean expected = true; 
		boolean value = gameLogic.hasWon(2); 
		assertEquals(expected, value); 
	}
	
	@Test 
	public void testHasWonCheckDiagonalsUpperLeft(){
		Logic gameLogic = new Logic();
		gameLogic.fill(0, 2);
		gameLogic.fill(0, 1);
		gameLogic.fill(0, 2);
		gameLogic.fill(0, 1);
		gameLogic.fill(0, 2);
		
		gameLogic.fill(1, 2);
		gameLogic.fill(1, 2);
		gameLogic.fill(1, 1);
		gameLogic.fill(1, 2);
		gameLogic.fill(1, 1);
		
		gameLogic.fill(2, 1);
		gameLogic.fill(2, 1);
		gameLogic.fill(2, 2);
		gameLogic.fill(2, 1);
		
		
		gameLogic.fill(3, 1);
		gameLogic.fill(3, 2);
		gameLogic.fill(3, 1);
		gameLogic.fill(3, 2);
		gameLogic.fill(3, 1);
		
		gameLogic.fill(4, 2);
		gameLogic.fill(4, 2);
		gameLogic.fill(4, 1);
		gameLogic.fill(4, 2);
		gameLogic.fill(4, 1);
		
		
		
		boolean expected = true; 
		boolean value = gameLogic.hasWon(2); 
		assertEquals(expected, value); 
	}
	
	
}
