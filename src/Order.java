import java.util.*;
public class Order {
	
	private int id[];
	private int amount[];
	private Product productsTable[];
	public static ArrayList<Order> allOrders=new ArrayList<Order>();
	
	public Order(int anId[], int anAmount[], Product productsTable[]) {
		this.id=anId;
		this.amount=anAmount;
		this.productsTable=productsTable;
		allOrders.add(this);
		int k=0;
		for(int aNId:id) {
			for(Product aProduct: productsTable) {
				if(aNId==aProduct.getId()) {
					aProduct.setStock(aProduct.getStock()+amount[k]);
					k++;
				}
			}
		}
		
	}
	
	public double getOrdersCharge() {
		double charge=0;
		int k=0;
		for(int anId:id) {
			for(Product aProduct: productsTable) {
				if(anId==aProduct.getId()) {
					charge+=(aProduct.getPriceBought()*amount[k]);
					k++;
				}
			}
		}
		return charge;
	}
	public double getTotalCharge() {
		double totalcharge=0;
		for (Order anOrder:allOrders) {
			totalcharge+=anOrder.getOrdersCharge();
		}
		return totalcharge;
	}
	
}

