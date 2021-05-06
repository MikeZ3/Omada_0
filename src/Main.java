import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) {
		Product.setProducts(getProductsFromJSON());
		// Order Example
		HashMap<Product, Integer> items = new HashMap<>();
		items.put(Product.getProducts().get(0), 2);
		items.put(Product.getProducts().get(1), 1);
		Order order1 = new Order(items);
		System.out.println(order1.getOrderCharge());

	}

	private static ArrayList<Product> getProductsFromJSON() {

		ArrayList<Product> products = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("products.json"));
			for (Object o : jsonArray) {
				JSONObject product = (JSONObject) o;
				int id = ((Long) product.get("id")).intValue();
				String name = (String) product.get("name");
				double price = (double) product.get("price");
				String category = (String) product.get("category");
				int stock = ((Long) product.get("stock")).intValue();

				products.add(new Product(id, name, price, category, stock));
			}
		} catch (IOException e) {
			System.out.println("Cannot Find products.json");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Error in products.json");
			e.printStackTrace();
		}

		return products;
	}
}
