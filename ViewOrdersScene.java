import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ViewOrdersScene extends SceneBasic {
	private VBox listBox;
	public ViewOrdersScene() {
		super("Your Orders");
		Label topTitle = new Label("Stock # | Description | Quantity");
		root.getChildren().add(topTitle);
		// TODO Auto-generated constructor stub
		Button clientMenuButton = new Button("Back to Menu");
	    clientMenuButton.setOnAction(e -> {
			SceneManager.setCustomerScene();
		});
	    
	    listBox = new VBox(20);
		listBox.setAlignment(Pos.CENTER);
			
		root.getChildren().addAll(listBox, clientMenuButton);
	}
	
	public void getOrders() {
		try {
			Socket conn = SceneManager.getSocket();
			PrintWriter outgoing = new PrintWriter(conn.getOutputStream());
			BufferedReader incoming = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			outgoing.println("ORDERS");
			outgoing.flush();
			String lineFromServer;
			while(true) {
				lineFromServer = incoming.readLine();
				if(lineFromServer.equals("STOP")) break;
				System.out.println(lineFromServer);
				Label account = new Label(lineFromServer);
				listBox.getChildren().add(account);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
