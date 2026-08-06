package RestAssuredTest.PojoClasses.RequestPojo;

import org.apache.commons.math3.stat.descriptive.summary.Product;

import RestAssuredTest.PojoClasses.ResponsePojo.DataGetProductEcart;

public class AddToEcartRequest{
    private String _id;
    private DataGetProductEcart product;

    public void set_id(String _id){
        this._id=_id;
    }
    public String get_id(){
        return _id;
    }
    public void setProduct(DataGetProductEcart product){
        this.product=product;
    }
    public DataGetProductEcart getProduct(){
        return product;
    }

}