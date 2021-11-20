
//Author: Carter Morgan
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginScene extends SceneBasic {
	private TextField usernameField;
	private PasswordField passwordField;
	private Label errorMessage;
    
	//login scene constructor
	public LoginScene() {
		super("Login Menu");
		
		errorMessage = new Label("");
		
		HBox usernameInput = new HBox(20);
		usernameField = new TextField();
		Label usernameLabel = new Label("username");
		usernameInput.getChildren().addAll(usernameLabel, usernameField);
		usernameInput.setAlignment(Pos.CENTER);
		
		HBox passwordInput = new HBox(20);
		passwordField = new PasswordField();
		Label passwordLabel = new Label("password");
		passwordInput.getChildren().addAll(passwordLabel, passwordField);
		passwordInput.setAlignment(Pos.CENTER);
		
		HBox buttons = new HBox(20);
		Button logInButton = new Button("Log In");
		logInButton.setOnAction(e -> {
			this.login();
		});
		
		Button settingsButton = new Button("Settings");
		settingsButton.setOnAction(e -> {
			SceneManager.setSettingsScene();
		});
		
		Button chatButton = new Button("Chat");
		chatButton.setOnAction(e -> {
			CustomerChat chat = new CustomerChat();
			chat.start(new Stage());
		});
		buttons.getChildren().addAll(logInButton, settingsButton, chatButton);
		buttons.setAlignment(Pos.CENTER);
		
		this.root.getChildren().addAll(usernameInput, passwordInput, buttons, errorMessage);
	}
	
	//sends login signal and entered data then handles server response
	public void login() {
		errorMessage.setText("");
		String submittedUsername = usernameField.getText();
		String submittedPassword = passwordField.getText();
		passwordField.clear();
		usernameField.clear();
		try {
			Socket conn = SceneManager.getSocket();
			if (conn == null) {
				SceneManager.setSocket(new Socket("localhost", 32007));
				conn = SceneManager.getSocket();
			}
			PrintWriter outgoing = new PrintWriter(conn.getOutputStream());
			BufferedReader incoming = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			outgoing.println("LOGIN");
			outgoing.flush();
			outgoing.println(submittedUsername + "," + submittedPassword);
			System.out.println(submittedUsername + "," + submittedPassword);
			outgoing.flush();
			String response = incoming.readLine();
			if(response == null) {
				throw new IOException("opened no data");
			}
			else if(response.equals("ADMIN")) {
				errorMessage.setText("");
				SceneManager.setAdminScene();
			}
			else if(response.equals("CLIENT")) {
				errorMessage.setText("");
				SceneManager.setCustomerScene();
			}
			else if(response.equals("PASSWORD")) {
				errorMessage.setText("Password is incorrect");
			}
			else if(response.equals("USERNAME")) {
				errorMessage.setText("Username is incorrect");
			}
			else {
				errorMessage.setText("unknown format message: " + response);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			errorMessage.setText("Unable to find server");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errorMessage.setText("Made connection but no message");
		}
	}
}