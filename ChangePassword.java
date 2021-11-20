import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ChangePassword {
	private static ArrayList<Account> accounts;
	private static Account userAccount;
	private static BufferedReader incoming;
	private static PrintWriter outgoing;
	private static final int LISTENING_PORT = 32007;
	
	public static void main(String [] args) {
		
	}
	
	public static void changePassword(ArrayList<Account> accounts, BufferedReader incoming, PrintWriter outgoing) {
		String recieved;
		try {
			recieved = incoming.readLine();
			String[] info = recieved.split(",");
			String oldPassword = info[0];
			String newPassword = info[1];
			Account toLogIn = null;
			
			if (userAccount.verifyPassword(oldPassword)) {
			userAccount.setPassword(newPassword);
			} else {
			outgoing.println("WRONG PASSWORD");
			outgoing.flush();
			}
			
			FileWriter file = new FileWriter("accounts.txt");
			file.write("\n");
			for(int i = 0; i <accounts.size(); i++) {
				if(accounts.get(i) instanceof CustomerAccount) {
					file.write("client" + accounts.get(i).getUsername() + "%" + accounts.get(i).getPassword() + "%" + (((CustomerAccount) accounts.get(i)).getProfile()) + "\n");
				}
				if(accounts.get(i) instanceof AdminAccount) {
					file.write("client" + accounts.get(i).getUsername() + "%" + accounts.get(i).getPassword() + "% \n" );
				}
			}
			if (userAccount instanceof AdminAccount) {
			outgoing.println("ADMIN");
			outgoing.flush();
			}
			else if (userAccount instanceof CustomerAccount) {
			outgoing.println("CLIENT");
			outgoing.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}