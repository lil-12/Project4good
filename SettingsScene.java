//Author: Carter Morgan, edited for P4 by Lily Davis
import java.net.Socket;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SettingsScene extends SceneBasic {
	private TextField hostField;
	private TextField portField;
	private Label errorMessage;
	
	//settings scene constructor
	public SettingsScene() {
		super("Settings");
		
		errorMessage = new Label("");
		CustomerChat chat = new CustomerChat();
		
		HBox hostInput = new HBox(20);
		hostField = new TextField();
		Label usernameLabel = new Label("host");
		hostInput.getChildren().addAll(usernameLabel, hostField);
		hostInput.setAlignment(Pos.CENTER);
		
		HBox portInput = new HBox(20);
		portField = new TextField();
		Label passwordLabel = new Label("port");
		portInput.getChildren().addAll(passwordLabel, portField);
		portInput.setAlignment(Pos.CENTER);
		
		HBox buttons = new HBox(20);
		Button hostButton = new Button("Apply");
		hostButton.setOnAction(e -> {
			this.apply();
		});
		
		Button portButton = new Button("Cancel");
		portButton.setOnAction(e -> {
			this.cancel();
		});
		
		//brings user to a chat window 
		Button chatButton = new Button("Chat");
		chatButton.setOnAction(e->
		{
			chat.start(new Stage()); 
		});
		buttons.getChildren().addAll(hostButton, portButton,chatButton);
		
		buttons.setAlignment(Pos.CENTER);
		this.root.getChildren().addAll(hostInput, portInput, buttons, errorMessage);
	}
	
	//modifies socket to user specification
	private void apply() {
		try {
			Socket connection = new Socket(hostField.getText(), Integer.parseInt(portField.getText()));
			SceneManager.setSocket(connection);
			SceneManager.setLoginScene();
			errorMessage.setText("");
		} catch (Exception e) {
			errorMessage.setText("Error trying to connect to server");
		}
	}
	
	//returns to login scene without modifying socket
	private void cancel() {
		SceneManager.setLoginScene();
	}
}
