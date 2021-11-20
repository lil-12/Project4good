//Author: Carter Morgan
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProfileScene extends SceneBasic {
	private Label profileText;
	
	//profile scene constructor
	public ProfileScene() {
		super("User Profile");
		
		Button clientMenuButton = new Button("Client Menu");
	    clientMenuButton.setOnAction(e -> {
			SceneManager.setCustomerScene();
		});
		
		Button logoutButton = new Button("Logout");
		logoutButton.setOnAction(e -> {
			SceneManager.setLoginScene();
		});
		
		profileText = new Label("");
		
		this.root.getChildren().addAll(profileText, clientMenuButton, logoutButton);
	}
	
	//sends message to server and sets the label to the response
	public void getProfile() {
		try {
			Socket conn = SceneManager.getSocket();
			PrintWriter outgoing = new PrintWriter(conn.getOutputStream());
			BufferedReader incoming = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			outgoing.println("PROFILE");
			outgoing.flush();
			String lineFromServer = incoming.readLine();
			profileText.setText(lineFromServer);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
