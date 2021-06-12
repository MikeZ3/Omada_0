import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Purchase {

    private int id;
    private HashMap<Product, Integer> items;
    private static ArrayList<Purchase> purchases = new ArrayList<>();

    public static void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public Purchase(HashMap<Product, Integer> items) {
        this.items = items;
        for(Map.Entry<Product, Integer> item: items.entrySet()) {
            Product product = item.getKey();
            int quantinty = item.getValue();
            product.setStock(product.getStock() + quantinty);
            this.id = purchases.size() + 1;
            purchases.add(this);
        }
    }

    // Get Total Number of Purchases
    public static int totalNumberOfPurchases() {
        return purchases.size();
    }

    public int getId() {
        return id;
    }

    // Calculates the cost of an purchase
    public double getPurchaseCost() {
        double cost = 0;
        for(Map.Entry<Product, Integer> item: items.entrySet()) {
            cost += item.getKey().getPrice_bought()*item.getValue();
        }
        return cost;
    }

    // Calculates total cost for All Purchases
    public static double getTotalCost() {
        double cost = 0;
        for(Purchase purchase: purchases) {
            for(Map.Entry<Product, Integer> item: purchase.items.entrySet()) {
                cost += item.getKey().getPrice_bought()*item.getValue();
            }
        }
        return cost;
    }
}
