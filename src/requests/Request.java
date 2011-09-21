package requests;
import java.io.Serializable;


@SuppressWarnings("serial")
public class Request implements Serializable {
	
	int id;
	
	public Request() {
		this.id = 0;
	}
	
	public int getId() {
		return this.id;
	}

}