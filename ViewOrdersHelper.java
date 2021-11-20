import java.net.ServerSocket;
import java.net.Socket;

public class ViewOrdersHelper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket serv = new ServerSocket(32007);
			Socket conn = serv.accept();
			StoreThread myThread = new StoreThread(conn);
			myThread.start();
			//myThread.viewOrders(outgoing);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
