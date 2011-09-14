import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiServerThread extends Thread {

private Socket socket = null;

public MultiServerThread(Socket socket) {
	super("MultiServerThread");
    this.socket = socket; }

	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()));
			
			System.out.println("Le client a envoyé : " + in.readLine());
		
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) { e.printStackTrace(); }
		
	}
}