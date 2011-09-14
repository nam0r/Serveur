import java.io.IOException;
import java.net.ServerSocket;

public class MultiServer { 

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;
		final int port = 9999;
		
		try {
			serverSocket = new ServerSocket(port);
			System.err.println("Listening on port " + String.valueOf(port) + ".");
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + String.valueOf(port) + ".");
			System.exit(-1);
		} 
		while (listening) { 
			new MultiServerThread(serverSocket.accept()).start();
		}
		serverSocket.close();
	}
	
} 