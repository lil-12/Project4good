
//Author: Carter Morgan
import javafx.scene.control.Button;

public class CustomerScene extends SceneBasic {
	
	//client scene constructor
	public CustomerScene() {
		super("Customer Menu");
		
		Button profileButton = new Button("Show Profile");
		profileButton.setOnAction(e -> {
			SceneManager.setProfileScene();
		});
		
		Button changePasswordButton = new Button("Change Password");
		changePasswordButton.setOnAction(e -> {
			SceneManager.setChangePasswordScene();
		});
		Button placeOrder = new Button("Place Order");
		placeOrder.setOnAction(e-> SceneManager.setPlaceOrderScene());
		Button logoutButton = new Button("Logout");
		logoutButton.setOnAction(e -> {
			logout();
		});
		
		Button viewOrdersButton = new Button("View Orders");
			viewOrdersButton.setOnAction(e -> {
			SceneManager.setViewOrdersScene();
		});
		
		this.root.getChildren().addAll(profileButton, viewOrdersButton, changePasswordButton, logoutButton,placeOrder);
	}
}