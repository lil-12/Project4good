//Author: Carter Morgan
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AccountsReader {
	//sync login and change password

	public static HashMap<String, Account> readFile(String filename) {
		HashMap<String, Account> accounts = new HashMap<String, Account>();
		try {
			File file = (new File(filename).exists()) ? new File(filename): new File("src\\" + filename);
			String customerTag = "<Customer>";
			String adminTag = "<Admin>";
			String customerClosingTag = "</Customer>";
			String adminClosingTag = "</Admin>";
			
			
			String wholeFile = "";
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) wholeFile += reader.nextLine();
			reader.close();
			wholeFile = wholeFile.replaceAll("\\s", "");
			
			ArrayList<String> accountChunks = new ArrayList<String>();
			String[] firstSplit = wholeFile.split(customerTag);
			for (String s : firstSplit) {
				s = customerTag + s;
				String[] secondSplit = s.split(adminTag);
				for (String x : secondSplit) {
					x = adminTag + x;
					accountChunks.add(x);
				}
			}
			
			for (String chunk : accountChunks) {
				if (chunk.indexOf(customerClosingTag) == -1 && chunk.indexOf(adminClosingTag) == -1) continue;
				
				//System.out.println(chunk);
				
				int startUsername = chunk.indexOf("<username>") + 10;;
				int endUsername = chunk.indexOf("</username>");
				String username = chunk.substring(startUsername, endUsername).trim();
								
				int startPassword = chunk.indexOf("<password>") + 10;
				int endPassword = chunk.indexOf("</password>");
				String password = chunk.substring(startPassword, endPassword).trim();
				
				int startID = chunk.indexOf("<id>") + 4;
				int endID = chunk.indexOf("</id>");
				int id = Integer.parseInt(chunk.substring(startID, endID).trim());		
				if (chunk.indexOf("<profile>") != -1) {
					int startProfile = chunk.indexOf("<profile>") + 9;
					int endProfile = chunk.indexOf("</profile>");
					String profile = chunk.substring(startProfile, endProfile).trim();
					CustomerAccount customer = new CustomerAccount(id, username, password, profile);
					accounts.put(customer.getID(), customer);
				}
				else if (chunk.indexOf(adminClosingTag) != -1) {
					AdminAccount admin = new AdminAccount(id, username, password);
					accounts.put(admin.getID(), admin);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}
}
