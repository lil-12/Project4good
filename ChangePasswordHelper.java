import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChangePasswordHelper {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket conn = new Socket("localhost", 32007);
			String submittedUsername = "user";
			String submittedPassword = "password";
			PrintWriter outgoing = new PrintWriter(conn.getOutputStream());
			BufferedReader incoming = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			outgoing.println("CHANGE PASSWORD");
			outgoing.flush();
			outgoing.println(submittedUsername + "," + submittedPassword);
			outgoing.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
