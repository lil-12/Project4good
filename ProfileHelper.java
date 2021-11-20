//Author: Carter Morgan
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ProfileHelper {
	//creates socket for test to connect to and then call StoreServer.sendProfile()
	public static void main(String[] args) {
		try {
			StoreServer.userAccount = new ClientAccount("username","password", "correct profile");
			ServerSocket serv = new ServerSocket(32007);
			Socket conn = serv.accept();
			PrintWriter outgoing = new PrintWriter(conn.getOutputStream());
			StoreServer.sendProfile(outgoing);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
