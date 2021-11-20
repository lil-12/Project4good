//Author: Carter Morgan
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

class ProfileTest {
	//sends profile message to server and checks that the response is the userAccount profile
	@Test
	void test() {
		try {
			String profileText = "correct profile";
			Socket conn = new Socket("localhost", 32007);
			PrintWriter outgoing = new PrintWriter(
					new OutputStreamWriter(conn.getOutputStream()));
			BufferedReader incoming = new BufferedReader( 
                    new InputStreamReader(conn.getInputStream()));
			outgoing.println("PROFILE");
			outgoing.flush();
			String lineFromServer = incoming.readLine(); 
			assert lineFromServer.equals("correct profile") : "recieved an incorrect profile";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
