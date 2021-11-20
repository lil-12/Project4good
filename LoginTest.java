//Author: Carter Morgan
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.jupiter.api.Test;

class LoginTest {
	
	//attempts to login to a client account and checks for correct response from server login method
	@Test
	void test() {
    	try {
    		Socket conn = new Socket("localhost", 32007);
			String clientUsername = "username";
			String clientPassword = "password";
			PrintWriter outgoing = new PrintWriter(conn.getOutputStream());
			BufferedReader incoming = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			outgoing.println("LOGIN");
			outgoing.flush();
			outgoing.println(clientUsername + "," + clientPassword);
			outgoing.flush();
			String lineFromServer = incoming.readLine();
			assert lineFromServer.equals("CLIENT") : "Server didn't recognize proper client login";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}