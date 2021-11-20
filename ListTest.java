//Author: Carter Morgan
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ListTest {
	//creates accounts than asks for list of accounts from server. Matches each and asserts they have all been found.
	@Test
	void test() {
		try {
			ArrayList<Account> expectedAccounts = new ArrayList<Account>();
			expectedAccounts.add(new ClientAccount("username", "password", "profile"));
			expectedAccounts.add(new AdminAccount("admin", "adminpassword", StoreServer.accounts));
			Socket conn = new Socket("localhost", 32007);
			PrintWriter outgoing = new PrintWriter(
					new OutputStreamWriter(conn.getOutputStream()));
			BufferedReader incoming = new BufferedReader( 
	                new InputStreamReader(conn.getInputStream()));
			outgoing.println("LIST");
			outgoing.flush();
			int found = 0;
			while(true) {
				String lineFromServer = incoming.readLine();
				if(lineFromServer.equals("STOP")) break;
				for (Account a : expectedAccounts) {
					String expectedMessage = a.getUsername() + " ";
					if (a instanceof AdminAccount) expectedMessage += "ADMIN";
					else if (a instanceof ClientAccount) expectedMessage += "CLIENT";
					
					if(expectedMessage.equals(lineFromServer)) found++;
				}
			}
			assert found == expectedAccounts.size() : "Unable to find info on all accounts expected";
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
