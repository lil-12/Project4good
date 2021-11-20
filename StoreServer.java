//Author: Carter Morgan
import java.io.IOException;
import java.net.ServerSocket;

public class StoreServer {
	private static final int LISTENING_PORT = 32007;
	
	//waits on port and creates thread for each client
	public static void main(String[] args) {
		try {
			ServerSocket serv = new ServerSocket(LISTENING_PORT);
			while(true) {
				new StoreThread(serv.accept()).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
