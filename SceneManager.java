// Author: Chris Fietkiewicz. For Project #4.
// Description: Manages changes from one scene to another scene.
import java.net.Socket;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Socket connection; // Socket connection to server
	private static Stage stage; // Stage used for all scenes
	private static LoginScene loginScene; // For user login
	private static AdminScene adminScene; // Menu for administrator accounts
	private static CustomerScene customerScene; // Menu for client accounts
	private static ChangePasswordScene changePasswordScene; // Form for all users to change password
	private static AccountListScene accountListScene; // Displays accounts (for administrators only)
	private static ProfileScene profileScene; // Displays client profile (for clients only)
	private static SettingsScene settingsScene; // Allows user to change Socket host and port number
	private static PlaceOrderScene placeOrderScene; // Allows user to place an order
	private static ViewOrdersScene viewOrdersScene; // Allows user to view their orders

	// Constructor
	public SceneManager() {
		loginScene = new LoginScene();
		adminScene = new AdminScene();
		customerScene = new CustomerScene();
		changePasswordScene = new ChangePasswordScene();
		accountListScene = new AccountListScene();
		profileScene = new ProfileScene();
		settingsScene = new SettingsScene();
		placeOrderScene = new PlaceOrderScene();
		viewOrdersScene = new ViewOrdersScene();
	}
	
	// Set socket connection to server
	public static void setSocket(Socket setConnection) {
		connection = setConnection;
	}

	// Get socket connection to server
	public static Socket getSocket() {
		return connection;
	}

	// Set initial stage to be used by all scenes
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	// Change view to LoginScene
	public static void setLoginScene() {
		stage.setScene(loginScene.getScene());
	}
	
	// Change view to AdminScene
	public static void setAdminScene() {
		stage.setScene(adminScene.getScene());
	}
	
	// Change view to CustomerScene
	public static void setCustomerScene() {
		stage.setScene(customerScene.getScene());
	}
	
	// Change view to ChangePasswordScene
	public static void setChangePasswordScene() {
		stage.setScene(changePasswordScene.getScene());
	}
	
	// Change view to AccountListScene
	public static void setAccountListScene() {
		accountListScene.getAccountList(); // Make AccountListScene request account list from server
		stage.setScene(accountListScene.getScene());
	}
	
	// Change view to ProfileScene
	public static void setProfileScene() {
		profileScene.getProfile(); // Make ProfileScene request profile from server
		stage.setScene(profileScene.getScene());
	}
	
	// Change view to SettingsScene
	public static void setSettingsScene() {
		stage.setScene(settingsScene.getScene());
	}
	
	// Change view to placeOrderScene
	public static void setPlaceOrderScene() {
		placeOrderScene.getInventory(); // Make placeOrderScene request inventory list from server
		stage.setScene(placeOrderScene.getScene());
	}
	
	// Change view to viewOrdersScene
	public static void setViewOrdersScene() {
		viewOrdersScene.getOrders(); // Make viewOrdersScene request orders from server
		stage.setScene(viewOrdersScene.getScene());
	}
}
