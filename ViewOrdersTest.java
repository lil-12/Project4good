import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViewOrdersTest {
	private static BufferedReader incoming;  
    private static PrintWriter outgoing;   
    private static Socket conn;
    private static HashMap<String, String> orders;
	
	@BeforeEach
	void setUp() throws Exception {
		orders = new HashMap<String, String>();
		//stock number, description
		orders.put(Integer.toString(45), "book");
		orders.put(Integer.toString(91), "car");
		conn = new Socket("localhost", 32007);
		incoming = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		outgoing = new PrintWriter(conn.getOutputStream());
		outgoing.println("LOGIN");
		outgoing.flush();
		outgoing.println("username,password"); //sets storethread userAccount to id 9
		outgoing.flush();
		String lineFromServer = incoming.readLine();
		System.out.println(lineFromServer);
		assert lineFromServer.equals("CLIENT") : "server didnt send client response";
	}

	@Test
	void test() {
		try {
			outgoing.println("ORDERS");
			outgoing.flush();
			String lineFromServer = "";
			
			String expectedLineOne = "45, book, 5";
			String expectedLineTwo = "91, car, 255";
			
			for (int i = 0; !lineFromServer.equals("DONE"); i++) {
				lineFromServer = incoming.readLine();
				try {
					String[] parts = lineFromServer.split(",");
					// remove leading space ?? trim doesn't work
					parts[1] = parts[1].substring(1);
					parts[2] = parts[2].substring(1);
					System.out.println(parts[0] + "|" + parts[1] + "|" + parts[2]);
					assert orders.containsKey(parts[0]) : "incorrect key";
					assert parts[1].equals(orders.get(parts[0])) : "incorrect description";
				}
				catch(ArrayIndexOutOfBoundsException e) {
					if(i < 2) fail("didn't assert both messages prior to recieving done");
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	} 
	
	@AfterEach
	void shutDown() throws Exception {
		outgoing.println("QUIT");
		outgoing.flush();
		
		incoming.close();
		outgoing.close();
		conn.close();
	}
}
