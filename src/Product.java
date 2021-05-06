import java.util.ArrayList;

public class Product {


	private int id;
	private String name;
	private double price_sold; // per piece
	private String category;
	private int stock;
	private double price_bought; // for all the pieces

	private static ArrayList<Product> products = new ArrayList<>();


	public Product(int id, String name, double price_sold, String category, int stock) {
		this.id = id;
		this.name = name;
		this.price_sold = DecimalUtils.round(price_sold,2);
		this.category = category;
		this.stock = stock;
		this.price_bought= DecimalUtils.round((price_sold/1.15),2);
	}

	public void addProduct(Product product) {
		products.add(product);
	}

	public static ArrayList<Product> getProducts() {
		return products;
	}

	public static void setProducts(ArrayList<Product> products) {
		Product.products = products;
	}

	public double getPriceBought() {
		return price_bought;
	}

	public void setPriceBought(double price_bought) {
		this.price_bought = price_bought;
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

	public double getPriceSold() {
		return price_sold;
	}

	public void setPriceSold(double price) {
		this.price_sold = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}

