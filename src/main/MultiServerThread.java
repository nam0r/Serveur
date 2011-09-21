package main;

import java.io.EOFException;
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

public class MultiServerThread extends Thread {

	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	private HashMap<String, ArrayList<String>> correspondances;

	public MultiServerThread(Socket socket, 
			HashMap<String, ArrayList<String>> correspondances) {
		super("MultiServerThread");
		this.socket = socket;
		this.correspondances = correspondances;
		
		//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		try {
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//oos.close();
		//ois.close();
		//socket.close();
		
	}
	
	private GiveSurnames getAllSurnames(int id) {
		return new GiveSurnames(id, true, "Ok", correspondances);
	}
	
	private GiveSurnames getSurnames(int id, ArrayList<String> names) {
		HashMap<String, ArrayList<String>> surnames = 
				new HashMap<String, ArrayList<String>>();
		for(String name : names) {
			surnames.put(name, correspondances.get(name));
		}
		return new GiveSurnames(id, true, "Ok", surnames);
	}
	
	private Answer registerSurnames(int id, HashMap<String, ArrayList<String>> data) {
		try {
			correspondances.putAll(data);
		} catch(NullPointerException e) {
			return new Answer(id, false, "Error: null data.");
		}
		return new Answer(id, true, "Ok");
	}
	
	private Answer addSurnames(int id, HashMap<String, ArrayList<String>> data) {
		for(String name : data.keySet()) {
			if(! correspondances.containsKey(name)) {
				return new Answer(id, false, "The given name isn't registered: can't add.");
			}
			ArrayList<String> surnames = correspondances.get(name);
			for(String surname : data.get(name)) {
				surnames.add(surname);
			}
			
			this.correspondances.put(name, surnames);
		}
		return new Answer(id, true, "Ok");
	}
	
	public Answer answer(Request request) {
		
		if(request instanceof GetAllSurnames) {
			return getAllSurnames(request.getId());
		}
		else if(request instanceof GetSurnames) {
			return getSurnames(request.getId(), ((GetSurnames)request).getNames());
		}
		else if(request instanceof RegisterSurnames) {
			return registerSurnames(request.getId(), ((RegisterSurnames)request).getData());
		}
		else if(request instanceof AddSurnames) {
			return addSurnames(request.getId(), ((AddSurnames)request).getData());
		}
		else {
			return new Answer(request.getId(), false, "No such request.");
		}
		
	}

	public void run() {

		System.out.println("New client connected.");
		
		//while(!socket.isClosed() && socket.isBound() && socket.isConnected() &&
		//		!socket.isInputShutdown() && !socket.isOutputShutdown()) {
		while(!socket.isClosed()) {
		//while(true) {
			try {
				Request paquet = (Request) ois.readObject();
				System.out.println("New request received.");
				System.out.println(answer(paquet));
				oos.writeObject(answer(paquet));
				System.out.println("Request answered.");
	
			} catch(EOFException e) {
				try {
					oos.flush();
					oos.close();
					ois.close();
					socket.close();
				} catch (IOException e1) {
					
				}
				//break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}

	}

}