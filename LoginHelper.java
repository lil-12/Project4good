//Author: Carter Morgan
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LoginHelper {
	//creates a socket for test to connect to and calls StoreServer.login to complete test
	public static void main(String[] args) {
		try {
			ServerSocket serv = new ServerSocket(32007);
    		Socket conn = serv.accept(); 
			BufferedReader incoming = new BufferedReader( 
	                 new InputStreamReader(conn.getInputStream()));
			 PrintWriter outgoing = new PrintWriter(
			new OutputStreamWriter(conn.getOutputStream()));
			ArrayList<Account> accounts = new ArrayList<Account>();
	        accounts.add(new ClientAccount("username", "password", "profile"));
	        accounts.add(new AdminAccount("admin", "adminpassword", StoreServer.accounts));
	        StoreServer.accounts = accounts;
	        String lineFromServer = incoming.readLine();
	        assert lineFromServer.equals("LOGIN"): "never recieved login command";
			StoreServer.login(accounts, incoming, outgoing);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
