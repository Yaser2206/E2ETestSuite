package RestAssuredTest.PojoClasses.RequestPojo;

import java.util.List;

public class OrdersEcart {
	private List<CreateOrdersEcart> orders;

	public List<CreateOrdersEcart> getOrders() {
		return orders;
	}

	public void setOrders(List<CreateOrdersEcart> orders) {
		this.orders = orders;
	}
}
