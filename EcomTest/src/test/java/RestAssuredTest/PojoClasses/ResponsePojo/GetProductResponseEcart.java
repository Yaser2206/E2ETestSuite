package RestAssuredTest.PojoClasses.ResponsePojo;

import java.io.Serializable;

public class GetProductResponseEcart implements Serializable {
	private DataGetProductEcart data;
	private String message;
	public DataGetProductEcart getData() {
		return data;
	}
	public void setData(DataGetProductEcart data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
