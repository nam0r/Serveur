package requests;

import java.util.ArrayList;

public class GetSurnames extends Request {
	private ArrayList<String> names; // le(s) nom(s) à demander
	
	public GetSurnames(ArrayList<String> names) {
		this.names = names;
	}
	
	public ArrayList<String> getNames() {
		return this.names;
	}
}