import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ChangePasswordTest {

	@Test
	void changePasswordTest() {
		
	 ArrayList<Account> accounts = new ArrayList<Account>();
	 
	AdminAccount newAA = new AdminAccount("admin", "pass", accounts);
	accounts.add(newAA);
	ArrayList<Account> accounts2 = new ArrayList<Account>();
	accounts2 = accounts;
	 Account userAccount;
	 BufferedReader incoming;
	 PrintWriter outgoing;
	 int LISTENING_PORT = 32007;
	 try {
        
         accounts.add(new ClientAccount("username", "password", "profile"));
     	ServerSocket serv = new ServerSocket(LISTENING_PORT);
     	Socket conn = serv.accept();
         incoming = new BufferedReader( 
                             new InputStreamReader(conn.getInputStream()) );
         outgoing = new PrintWriter(
					new OutputStreamWriter(conn.getOutputStream()));
         ChangePassword.changePassword(accounts,  incoming, outgoing);
         FileReaderTester.readAccounts(accounts);
         
         assert accounts.equals(accounts2) : "It did not work";
	 }
	 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
