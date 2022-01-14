package findFive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class TestLogic {
	@Test
	public void testFillSuccessful()
	{
		Logic gameLogic = new Logic(); 
		String value = gameLogic.fill(1, 1); 
		String expected = "successful"; 
		assertEquals(expected, value); 
	}
	
	public void testFillInvalidColumnEntry()
	{
		Logic gameLogic = new Logic(); 
		String value = gameLogic.fill(9, 1); 
		String expected = "invalid columnEntry"; 
		assertEquals(expected, value); 
	}
	
	
	public void testFillInvalidplayerInteger()
	{
		Logic gameLogic = new Logic(); 
		String value = gameLogic.fill(1, 1); 
		String expected = "invalid player integer"; 
		assertEquals(expected, value); 
	}
	
}
