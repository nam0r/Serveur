package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import requests.AddSurnames;
import requests.GetAllSurnames;
import requests.GetSurnames;
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
			
			this.testRegisterNewNames();
			this.receive();
			
			this.testRegisterNullData();
			this.receive();
			
			this.testGetAllSurnames();
			this.receive();
			
			this.testGetSurnames();
			this.receive();
			
			this.testAddSurnames();
			this.receive();
			
			this.testGetAllSurnames();
			this.receive();
			
			this.testAddSurnamesFail();
			this.receive();
			
			//client.shutdownInput();
			//client.shutdownOutput();
			//client.getOutputStream().close();
			
			/*
			oos.flush();
			oos.close();
			ois.close();
			client.close();
			*/
			
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
				System.out.println((GiveSurnames)answer);
				for(String name : data.keySet()) {
					System.out.println(name);
					for(String surname : data.get(name)) {
						System.out.println("\t" + surname);
					}
				}
			}
			System.out.println(answer.getMessage());
		}
		else {
			System.err.println(answer.getMessage());
		}
	}
	
	/* ***** *
	 * Tests *
	 * ***** */
	
	public void testRegisterNewNames() throws IOException {
		System.out.println("==> Test register new names");
		HashMap<String, ArrayList<String>> corres = 
				new HashMap<String, ArrayList<String>> ();
		
		/*
		ArrayList<String> sur = new ArrayList<String>();
		sur.add("Kikou");
		sur.add("Namor");
		sur.add("Lamasticot");
		corres.put("Roman", sur);
		
		Request paquet1 = new RegisterSurnames(corres);
		oos.writeObject(paquet1);
		*/
		
		
		ArrayList<String> sur2 = new ArrayList<String>();
		sur2.add("Surnom1");
		sur2.add("Surnom2");
		sur2.add("Surnom3");
		corres.put("Geek", sur2);
		ArrayList<String> sur3 = new ArrayList<String>();
		sur3.add("Surnom4");
		sur3.add("Surnom5");
		sur3.add("Surnom6");
		corres.put("Geek2", sur3);
		
		Request paquet2 = new RegisterSurnames(corres);
		oos.writeObject(paquet2);
	}
	
	public void testAddSurnames() throws IOException {
		System.out.println("==> Test add surnames");
		HashMap<String, ArrayList<String>> corres = 
				new HashMap<String, ArrayList<String>> ();		
		
		ArrayList<String> sur2 = new ArrayList<String>();
		sur2.add("Surnom__1");
		sur2.add("Surnom__2");
		corres.put("Geek", sur2);
		ArrayList<String> sur3 = new ArrayList<String>();
		sur3.add("Surnom__4");
		sur3.add("Surnom__5");
		sur3.add("Surnom__6");
		corres.put("Geek2", sur3);
		
		Request paquet2 = new AddSurnames(corres);
		oos.writeObject(paquet2);
	}
	
	public void testAddSurnamesFail() throws IOException {
		System.out.println("==> Test add surnames fail");
		HashMap<String, ArrayList<String>> corres = 
				new HashMap<String, ArrayList<String>> ();
		
		ArrayList<String> sur = new ArrayList<String>();
		sur.add("Kikou");
		sur.add("Namor");
		sur.add("Lamasticot");
		corres.put("Roman", sur);
		
		Request paquet1 = new AddSurnames(corres);
		oos.writeObject(paquet1);
	}
	
	public void testRegisterNullData() throws IOException {
		System.out.println("==> Test register null data");
		oos.writeObject(new RegisterSurnames(null));
	}
	
	public void testGetAllSurnames() throws IOException {
		System.out.println("==> Test get all surnames");
		oos.writeObject(new GetAllSurnames());
	}
	
	public void testGetSurnames() throws IOException {
		System.out.println("==> Test get surnames");
		ArrayList<String> names = new ArrayList<String>();
		names.add("Geek2");
		oos.writeObject(new GetSurnames(names));
	}
	
}