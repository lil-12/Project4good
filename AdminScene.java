//Author: Carter Morgan
import javafx.scene.control.Button;

public class AdminScene extends SceneBasic {
	
	//admin scene constructor
	public AdminScene() {
		super("Administrator Menu");
		Button listAccounts = new Button("List accounts");
		listAccounts.setOnAction(e -> SceneManager.setAccountListScene());
		
		Button changePassword = new Button("Change password");
		changePassword.setOnAction(e -> SceneManager.setChangePasswordScene());
		
		Button logout = new Button("Logout");
		logout.setOnAction(e -> logout());   
	   
		root.getChildren().addAll(listAccounts,changePassword,logout);
	}
}
