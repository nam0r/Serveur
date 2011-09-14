package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import requests.RegisterSurnames;
import requests.Request;
import answers.Answer;
import answers.GiveSurnames;


public class Client {
	
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Client(int port) {
		try {
			client = new Socket("localhost", port);
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());
			
			this.send();
			this.receive();
			
			oos.flush();
			oos.close();
			client.close();
			//client.getOutputStream().close();
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Client(9998);
	}
	
	public void receive() throws ClassNotFoundException, IOException {
		Answer answer = (Answer) ois.readObject();
		if(answer.getSuccess()) {
			if(answer instanceof GiveSurnames) {
				HashMap<String, ArrayList<String>> data = 
						((GiveSurnames)answer).getData();
				
				for(String name : data.keySet()) {
					System.out.println(name);
					for(String surname : data.get(name)) {
						System.out.println("\t" + surname);
					}
				}
			}
		}
		else {
			System.err.println(answer.getMessage());
		}
	}
	
	public void send() throws IOException {
		HashMap<String, ArrayList<String>> corres = 
				new HashMap<String, ArrayList<String>> ();
		
		ArrayList<String> sur = new ArrayList<String>();
		sur.add("Kikou");
		sur.add("Namor");
		sur.add("Lamasticot");
		corres.put("Roman", sur);
		
		Request paquet = new RegisterSurnames(corres);
		oos.writeObject(paquet);
	}
	
}
