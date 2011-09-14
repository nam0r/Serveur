package answers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("serial")
public class Answer implements Serializable {
	
	private int id;
	private boolean success;
	private String message;
	
	public Answer(int id, boolean success, String message) {
		this.id = id;
		this.success = success;
		this.message = message;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
