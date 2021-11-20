//Author: Carter Morgan
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ListHelper {
	
	//creates accounts and server socket for ListTest then calls the method being tested StoreServer.sendAccountList()
	public static void main(String[] args) {
		StoreServer.accounts = new ArrayList<Account>();
        StoreServer.accounts.add(new ClientAccount("username", "password", "profile"));
        StoreServer.accounts.add(new AdminAccount("admin", "adminpassword", StoreServer.accounts));
		try {
			ServerSocket serv = new ServerSocket(32007);
	    	Socket conn = serv.accept();
	        BufferedReader incoming = new BufferedReader( 
	                            new InputStreamReader(conn.getInputStream()));
	        PrintWriter outgoing = new PrintWriter(
					new OutputStreamWriter(conn.getOutputStream()));
	        StoreServer.sendAccountList(outgoing);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
