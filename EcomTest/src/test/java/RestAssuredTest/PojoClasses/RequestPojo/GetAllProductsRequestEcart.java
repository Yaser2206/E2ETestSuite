package RestAssuredTest.PojoClasses.RequestPojo;

import java.util.List;

public class GetAllProductsRequestEcart {
	private String productName;
	private String minPrice;
	private String maxPrice;
	private List<String> productCategory;
	private List<String> productSubCategory;
	private List<String> productFor;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public List<String> getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(List<String> productCategory) {
		this.productCategory = productCategory;
	}
	public List<String> getProductSubCategory() {
		return productSubCategory;
	}
	public void setProductSubCategory(List<String> productSubCategory) {
		this.productSubCategory = productSubCategory;
	}
	public List<String> getProductFor() {
		return productFor;
	}
	public void setProductFor(List<String> productFor) {
		this.productFor = productFor;
	}
	
}
