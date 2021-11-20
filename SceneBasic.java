//Author: Carter Morgan
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

abstract class SceneBasic {
	private Scene scene;
	protected VBox root;
	
	//scene constructor
	public SceneBasic(String titleText) {
		Label titleLabel = new Label(titleText);
		root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(titleLabel);
		scene = new Scene(root, 500, 400);
	}
	
	//sends quit message so server closes connection and returns to login screen
	public void logout() {
		Socket connection = SceneManager.getSocket();
		PrintWriter out;
		try {
			out = new PrintWriter(connection.getOutputStream());
			out.write("QUIT");
			out.flush();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SceneManager.setSocket(null);
		SceneManager.setLoginScene();
	}
	
	//scene getter
	public Scene getScene() {
		return scene;
	}
}
