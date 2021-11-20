//Author : Carter Morgan
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;

public class Store extends Application {
	private SceneManager sceneManager;
	private Socket connection;
	
	//sets socket to null so LoginScene can check for connection
	public Store() {
		 sceneManager = new SceneManager();
		 connection = null;
		 SceneManager.setSocket(connection);
	}
	
	//shows LoginScene
	public void start(Stage stage) {
		//javaFX starts here
		sceneManager.setStage(stage);
		SceneManager.setLoginScene();
		stage.show();
	}
	
	//sends quit signal to server when client is closed
	public void stop() {
		if(SceneManager.getSocket() == null) return;
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
	}
	
	//launches javafx
	public static void main(String[] args) {
		launch(args);	
	}
}
