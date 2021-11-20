//Author: Carter Morgan
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StoreThread extends Thread {
	private Socket client;
	public HashMap<String, Account> accounts;
	public Account userAccount;
	private BufferedReader incoming;
	private PrintWriter outgoing;
	
	public StoreThread(Socket client) {
		// TODO Auto-generated constructor stub
		this.client = client;
		try {
			incoming = new BufferedReader( 
	                new InputStreamReader(this.client.getInputStream()));
			outgoing = new PrintWriter(
					new OutputStreamWriter(this.client.getOutputStream()));
			accounts = AccountsReader.readFile("accounts.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while(true) {
				String lineFromServer = incoming.readLine();
				System.out.println(lineFromServer);
				if(lineFromServer == null)
				{}
				else if(lineFromServer.equals("LOGIN")) {
					this.login(accounts, incoming, outgoing);
				}
				else if(lineFromServer.equals("LIST")) {
					this.sendAccountList(outgoing);
				}
				else if(lineFromServer.equals("PASSWORD")) {
					this.changePassword(userAccount, incoming, outgoing);
				}
				else if(lineFromServer.equals("PROFILE")) {
					this.sendProfile(outgoing);
				}
				else if (lineFromServer.equals("ORDERS")) {
					this.viewOrders(outgoing);
				}
				else if(lineFromServer.equals("SEND_INV"))
				{
					this.sendInventory(outgoing);
				}
				else if(lineFromServer.equals("GET_ORDER"))
				{
					this.getOrder(incoming);
				}
				else if(lineFromServer.equals("QUIT")) {
					break;
				}
			}
			client.close();				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void login(HashMap<String, Account> accounts, BufferedReader incoming, PrintWriter outgoing) {
		String recieved;
		System.out.println("trying to log in");
		try {
			recieved = incoming.readLine();
			
			String[] info = recieved.split(",");
			if(info.length < 2) {
				outgoing.println("USERNAME");
				outgoing.flush();
				System.out.println("returning");
				return;
			}
			String submittedUsername = info[0];
			String submittedPassword = info[1];
			//System.out.print
			for(String key : accounts.keySet()) {
				Account acc = accounts.get(key);
				if (submittedUsername.equals(acc.getUsername()) && acc.verifyPassword(submittedPassword)) {
					//correct
					System.out.println(acc.getUsername() + " " + acc.getPassword());
					if (acc instanceof AdminAccount) 
						{
						outgoing.println("ADMIN");
						System.out.println("sent admin");
						}
					else if (acc instanceof CustomerAccount) {
						outgoing.println("CLIENT");
						System.out.println("sent clietn");
					}
					outgoing.flush();
					userAccount = acc;
					return;
				}
				else if (submittedUsername.equals(acc.getUsername()) && !acc.verifyPassword(submittedPassword)) {
					//right username wrong password
					outgoing.println("PASSWORD");
					outgoing.flush();
				}
				else if (!submittedUsername.equals(acc.getUsername()) && acc.verifyPassword(submittedPassword)) {
					outgoing.println("USERNAME");
					outgoing.flush();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendAccountList(PrintWriter outgoing) {
		for(String key : accounts.keySet()) {
			Account a = accounts.get(key);
			String msg = a.getUsername() + " ";
			if(a instanceof CustomerAccount) msg += "CLIENT";
			else if(a instanceof AdminAccount) msg += "ADMIN";
			
			System.out.println(msg);
			outgoing.println(msg);
			outgoing.flush();
		}
		outgoing.println("STOP");
		outgoing.flush();
	}
	
	public void sendProfile(PrintWriter outgoing) {
		outgoing.println(((CustomerAccount) userAccount).getProfile());
		outgoing.flush();
	}
	
	public synchronized void changePassword(Account userAccount, BufferedReader incoming, PrintWriter outgoing) {
		try {
			String newPassword = null;
			
			String line = incoming.readLine();
			String[] passwords = line.split(",");
			if(passwords.length < 2) {
				outgoing.println("PASSWORD");
				outgoing.flush();
				return;
			}
			newPassword = passwords[1];
			String oldPassword = passwords[0];
			if(!userAccount.verifyPassword(oldPassword)) {
				outgoing.println("PASSWORD");
				outgoing.flush();
				return;
			}
			
			//change .xml
			System.out.println("changing password");
			String customerTag = "<Customer>";
			String adminTag = "<Admin>";
			String accountTag = (userAccount instanceof CustomerAccount) ? customerTag : adminTag;
			String otherTag = (userAccount instanceof AdminAccount) ? customerTag : adminTag;
			String closingTag = "</" + accountTag.substring(1);
			String otherClosingTag = "</" + otherTag.substring(1);
			
			File accountFile = (new File("accounts.xml").exists()) ? new File("accounts.xml") : new File("src\\accounts.xml");
			String wholeFile = "";
			Scanner reader = new Scanner(accountFile);
			while(reader.hasNextLine()) wholeFile += reader.nextLine();
			reader.close();
			wholeFile = wholeFile.replaceAll("\\s","");
			
			ArrayList<String> accountChunks = new ArrayList<String>();
			String[] firstSplit = wholeFile.split(customerTag);
			for (String s : firstSplit) {
				String[] secondSplit = s.split(adminTag);
				for (String x : secondSplit) {
					if(x.indexOf(otherClosingTag) == -1 && x.indexOf(closingTag) == -1) {
						accountChunks.add(x);
						continue;
					}
					else if (x.indexOf(closingTag) == -1) x = otherTag + x;
					else if (x.indexOf(otherClosingTag) == -1) x = accountTag + x;
					else if (x.indexOf(closingTag) < x.indexOf(otherClosingTag)) x = accountTag + x;
					else x = otherTag + x;
					accountChunks.add(x);
				}
			}
			
			for (int i = 0; i < accountChunks.size(); i++) {
				String chunk = accountChunks.get(i);
				int closingLocation = chunk.indexOf(closingTag);
				if (closingLocation == -1 && chunk.indexOf(otherClosingTag) == -1) continue;
				
				int startID = chunk.indexOf("<id>") + 4;
				int endID = chunk.indexOf("</id>");
				String id = chunk.substring(startID, endID).trim();
				if (!id.equals(userAccount.getID())) continue;
				int startPassword = chunk.indexOf("<password>") + 10;
				int endPassword = chunk.indexOf("</password>");
				String password = chunk.substring(startPassword, endPassword);
				userAccount.setPassword(password);
				String changedPasswordLine = chunk.substring(0, startPassword) + newPassword + chunk.substring(endPassword);
				accountChunks.set(i, changedPasswordLine);
			}
			for (String chunk : accountChunks) System.out.println(chunk);
			System.out.println("writing");
			FileWriter writer = new FileWriter(accountFile);
			for (String chunk : accountChunks) writer.write(chunk);
			writer.close();
			if(userAccount instanceof CustomerAccount) outgoing.println("CLIENT");
			else outgoing.println("ADMIN");
			outgoing.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendInventory(PrintWriter outgoing)
	    {
	    	try 
	    	{
	    		HashMap<String,String> inventory = InventoryReader.readFile("inventory.xml");
	        	int keys = inventory.keySet().size();
	        	outgoing.println(""+keys);
	        	outgoing.flush();
	        	System.out.println("sent num of keys");
	        	for (String key : inventory.keySet()) 
	    		{
	    			String toSend = ( key + "\t\t"+inventory.get(key));
	    			outgoing.println(toSend);
	    			outgoing.flush();
	    		}
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	
	    }
	    synchronized public void getOrder(BufferedReader incoming)
	    {
	    	try 
	    	{
	    		int stockNum = Integer.parseInt(incoming.readLine());
	        	int quantity = Integer.parseInt(incoming.readLine());
	        	
	        	FileWriter out=new FileWriter("orders.dat",true);
	    		out.write(Integer.parseInt(userAccount.getID()));
	    		System.out.println(userAccount.getID());
	    		out.write(stockNum);
	    		System.out.println(stockNum);
	    		out.write(quantity);
	    		System.out.println(quantity);
	    		out.flush();
	    		System.out.println("order recorded\n");
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	   
	public void viewOrders(PrintWriter outgoing) {
		 HashMap<String, String> inventory = InventoryReader.readFile("inventory.xml");
		 try {
			File file = new File("datafileLabP4BB");
			InputStream in = new FileInputStream(file);
			int got = in.read();
			int customerID = -1, stock = -1, quantity = -1;
			for(int i = 0; got >= 0; i++) {
				if(i % 3 == 0) {
					customerID = got;
					//System.out.print("customer id " + got);
				}
				else if(i % 3 == 1) {
					stock = got;
					//System.out.print(" stock number " + got);
				}
				else if(i % 3 == 2) {
					quantity = got;
					//System.out.println(" quantity " + got);
					if(Integer.toString(customerID).equals(userAccount.getID())) {
						//stock, descipt, quant
						String description = inventory.get(Integer.toString(stock));
						outgoing.println(stock + ", " + description + ", " + quantity);
					}
				}
				got = in.read();
			}
		outgoing.println("STOP");
			outgoing.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
