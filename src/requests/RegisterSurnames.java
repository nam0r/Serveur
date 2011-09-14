package requests;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterSurnames extends Request {
	
	HashMap<String, ArrayList<String>> data;
	
	public RegisterSurnames(HashMap<String, ArrayList<String>> data) {
		this.data = data;
	}
	
	public HashMap<String, ArrayList<String>> getData() {
		return this.data;
	}

}
