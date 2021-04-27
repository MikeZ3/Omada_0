
public class Product {
	
	private int id;
	private String name;
	private double price;
	private int stock;
	
	public Product(int anId, String aName, double aPrice, int Stock) {
		this.id=anId;
		this.name=aName;
		this.price=aPrice;
		this.stock=Stock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	

}
