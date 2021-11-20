
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ChangePasswordScene extends SceneBasic {
    private TextField oldPassword;
    private TextField newPassword;
    private Label errorMessage;
	
    //password scene
    public ChangePasswordScene() {
		super("Change Password");
		HBox oldPasswordInput = new HBox(20);
		HBox newPasswordInput = new HBox(20);
		errorMessage = new Label("");
		oldPassword = new TextField();
		newPassword = new TextField();
		Label oldPasswordLabel = new Label("Old Password");
		Label newPasswordLabel = new Label("new Password");
		oldPasswordInput.getChildren().addAll(oldPasswordLabel,oldPassword);
		newPasswordInput.getChildren().addAll(newPasswordLabel,newPassword);
		oldPasswordInput.setAlignment(Pos.CENTER);
		newPasswordInput.setAlignment(Pos.CENTER);
		Button changePassword = new Button("Change Password");
		changePassword.setOnAction(e -> change());;
		root.getChildren().addAll(oldPasswordInput,newPasswordInput,changePassword,errorMessage);
	}
    
    //Password change
	public void change() {
		errorMessage.setText("");
		String oldPass = oldPassword.getText();
		String newPass = newPassword.getText();
		oldPassword.clear();
		newPassword.clear();
		try {
			Socket conn = SceneManager.getSocket();
			PrintWriter outgoing = new PrintWriter(conn.getOutputStream());
			BufferedReader incoming = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			outgoing.println("PASSWORD");
			outgoing.println(oldPass + "," + newPass);
			outgoing.flush();
			String response = incoming.readLine();
			if(response == null) {
				throw new IOException("opened no data");
			}
			else if(response.equals("ADMIN")) {
				SceneManager.setAdminScene();
			}
			else if(response.equals("CLIENT")) {
				SceneManager.setCustomerScene();
			}
			else if(response.equals("PASSWORD")) {
				errorMessage.setText("Password is incorrect");
			}
			else {
				errorMessage.setText("unknown format message: " + response);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			errorMessage.setText("Unable to find server");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errorMessage.setText("Made connection but no message");
			e.printStackTrace();
		}
	}//gets the data to the server to change the password
}