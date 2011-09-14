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

public class MultiServerThread extends Thread {

	private Socket socket = null;

	private HashMap<String, ArrayList<String>> correspondances;

	public MultiServerThread(Socket socket) {
		super("MultiServerThread");
		this.socket = socket;
		
		this.correspondances = new HashMap<String, ArrayList<String>>();
	}
	
	private GiveSurnames getAllSurnames(int id) {
		HashMap<String, ArrayList<String>> surnames = 
				new HashMap<String, ArrayList<String>>();
		return new GiveSurnames(id, true, "Ok", surnames);
	}
	
	private GiveSurnames getSurnames(int id, ArrayList<String> names) {
		HashMap<String, ArrayList<String>> surnames = 
				new HashMap<String, ArrayList<String>>();
		return new GiveSurnames(id, true, "Ok", surnames);
	}
	
	private Answer registerSurnames(int id, HashMap<String, ArrayList<String>> data) {
		return new Answer(id, true, "Ok");
	}
	
	private Answer updateSurnames(int id, HashMap<String, ArrayList<String>> data) {
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
			return updateSurnames(request.getId(), ((AddSurnames)request).getData());
		}
		else {
			return new Answer(request.getId(), false, "No such request.");
		}
		
	}

	public void run() {
		try {
			//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());

			try {
				Request paquet = (Request) ois.readObject();
				oos.writeObject(answer(paquet));

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			oos.close();
			ois.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}