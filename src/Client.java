import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class Client {
	
	private Socket client;
	private PrintStream output;
	
	public Client(int port) {
		try {
			client = new Socket("localhost", port);
			output = new PrintStream(client.getOutputStream());
			output.println("Ha ha ha je me suis connecté.");
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new Client(9999);
	}
	
}
