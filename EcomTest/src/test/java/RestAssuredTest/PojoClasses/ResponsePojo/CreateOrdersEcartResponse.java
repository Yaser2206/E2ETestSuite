package RestAssuredTest.PojoClasses.ResponsePojo;

import java.util.List;

public class CreateOrdersEcartResponse {
	private List<String> orders;
	private List<String> productIds;
	public List<String> getOrders() {
		return orders;
	}
	public void setOrders(List<String> orders) {
		this.orders = orders;
	}
	public List<String> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
}
