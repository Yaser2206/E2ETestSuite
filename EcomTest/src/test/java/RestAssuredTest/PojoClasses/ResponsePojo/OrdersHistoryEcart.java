package RestAssuredTest.PojoClasses.ResponsePojo;

import java.util.List;

public class OrdersHistoryEcart {
	private List<OrderProductDataEcart>data;
	public List<OrderProductDataEcart> getData() {
		return data;
	}
	public void setData(List<OrderProductDataEcart> data) {
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
	private int count;
	private String message;
}
