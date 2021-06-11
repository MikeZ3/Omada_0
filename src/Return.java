import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Return {

    private int id;
    private HashMap<Product, Integer> items;
    private static ArrayList<Return> returns = new ArrayList<>();

    public static void addReturn(Return r) {
        returns.add(r);
    }

    public Return(HashMap<Product, Integer> items) {
        this.items = items;
        for(Map.Entry<Product, Integer> item: items.entrySet()) {

            Product product = item.getKey();
            int quantinty = item.getValue();

            product.setStock(product.getStock() + quantinty);
            this.id = returns.size() + 1;
            returns.add(this);
        }
    }

    // Get Total Number of Returns
    public static int totalNumberOfReturns() {
        return returns.size();
    }

    public int getId() {
        return id;
    }

    // Calculates the charge of a return
    public double getReturnCost() {
        double cost = 0;
        for(Map.Entry<Product, Integer> item: items.entrySet()) {
            cost += item.getKey().getPrice_sold()*item.getValue();
        }
        return cost;
    }

    // Calculates total charge for All Returns
    public static double getTotalCost() {
        double cost = 0;
        for(Return r: returns) {
            for(Map.Entry<Product, Integer> item: r.items.entrySet()) {
                cost += item.getKey().getPrice_sold()*item.getValue();
            }
        }
        return cost;
    }
}

