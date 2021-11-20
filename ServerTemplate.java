// Author: Chris Fietkiewicz
// Provided as a demonstration for required methods for the following:
//   * StoreThread.java.
//   * AccountsReader.java.
//   * InventoryReader.java.
// It can be compiled with the above to check for correct
// method names and parameters. It should compile without errors.
// See the assignment instructions for complete details about the methods.
// NOTE: It is *not* intended to be executed.

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerTemplate {
	private static Account userAccount;
	private static BufferedReader incoming;
	private static PrintWriter outgoing;   // Stream for sending data.

	public ServerTemplate() {
		HashMap<String, Account> accounts = AccountsReader.readFile("accounts.xml");
		HashMap<String, String> inventory = InventoryReader.readFile("inventory.xml");
		Socket client = new Socket(); // Needed for StoreThread (does not connect)
        StoreThread worker = new StoreThread(client);
        worker.login(accounts, incoming, outgoing);
        worker.sendAccountList(outgoing);
        worker.sendProfile(outgoing);
        worker.changePassword(userAccount, incoming, outgoing);
        worker.getOrder(incoming);
        worker.sendInventory(outgoing);
        worker.viewOrders(outgoing);
	}
}
