package RestAssuredTest.PojoClasses.ResponsePojo;

import java.util.List;

public class GetAllProductsResponseEcart {
	private List<DataGetAllProductsEcart> data;
	private int count;
	private String message;
	public List<DataGetAllProductsEcart> getData() {
		return data;
	}
	public void setData(List<DataGetAllProductsEcart> data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
