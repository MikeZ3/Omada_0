import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order {

	private int id;
	private HashMap<Product, Integer> items;
	private static ArrayList<Order> orders = new ArrayList<>();

	public Order(HashMap<Product, Integer> items) {
		this.items = items;
		for(Map.Entry<Product, Integer> item: items.entrySet()) {
			Product product = item.getKey();
			int quantinty = item.getValue();
			if(product.getStock() >= quantinty) {
				product.setStock(product.getStock() - quantinty);
				this.id = orders.size() + 1;
				orders.add(this);
			} else {
				System.out.println("Not Enough Stock to get Product " + product.getName());
				System.out.println("Current Stock: " + product.getStock());
				System.out.println("Order was not made");
			}
		}
	}

	// Get Total Number of Orders
	public static int totalNumberOfOrders() {
		return orders.size();
	}

	public int getId() {
		return id;
	}

	// Calculates the charge of an order
	public double getOrderCharge() {
		double charge = 0;
		for(Map.Entry<Product, Integer> item: items.entrySet()) {
			charge += item.getKey().getPriceSold()*item.getValue();
		}
		return charge;
	}

	// Calculates total charge for All Orders
	public static double getTotalCharge() {
		double charge = 0;
		for(Order order: orders) {
			for(Map.Entry<Product, Integer> item: order.items.entrySet()) {
				charge += item.getKey().getPriceSold()*item.getValue();
			}
		}
		return charge;
	}
}
