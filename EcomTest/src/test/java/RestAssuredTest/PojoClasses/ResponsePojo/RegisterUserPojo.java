package RestAssuredTest.PojoClasses.ResponsePojo;

public class RegisterUserPojo {
	private int id;
	private String token;
	private _Meta _meta;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public _Meta get_meta() {
		return _meta;
	}
	public void set_meta(_Meta _meta) {
		this._meta = _meta;
	}
	
}
