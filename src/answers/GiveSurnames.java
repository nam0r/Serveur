package answers;

import java.util.ArrayList;
import java.util.HashMap;

public class GiveSurnames extends Answer {
	
	private HashMap<String, ArrayList<String>> data; // le(s) nom(s)-surnom(s)
	
	public GiveSurnames(int id, boolean success, String message,
			HashMap<String, ArrayList<String>> data) {
		super(id, success, message);
		this.data = data;
	}
	
	public HashMap<String, ArrayList<String>> getData() {
		return this.data;
	}
	
	public String toString() {
		return this.data.toString();
	}

}
