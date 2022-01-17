package findFive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestClientServerConnection {

	@Test 
	public void testHandShake() {
		Server gameServer = new Server(); 
		gameServer.createAndListen(9876);
		Client client = new Client(); 
		String actual = client.initiate("127.0.0.1", "John", 9876);
		String expected = "hello John"; 
		assertEquals(expected, actual); 
	}
}
