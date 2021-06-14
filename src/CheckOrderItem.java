public class CheckOrderItem {

    private Product product;
    private int id;
    private String name;
    private String category;
    private int min_stock;
    private int recommended_stock;
    private int current_stock;
    private int stock_to_purchase;

    public CheckOrderItem(Product product) {
        this.product = product;
        id = product.getId();
        name = product.getName();
        category = product.getCategory();
        min_stock = product.getMin_stock();
        recommended_stock = product.getRecommended_stock();
        current_stock = product.getStock();
        stock_to_purchase = recommended_stock-current_stock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMin_stock() {
        return min_stock;
    }

    public void setMin_stock(int min_stock) {
        this.min_stock = min_stock;
    }

    public int getRecommended_stock() {
        return recommended_stock;
    }

    public void setRecommended_stock(int recommended_stock) {
        this.recommended_stock = recommended_stock;
    }

    public int getCurrent_stock() {
        return current_stock;
    }

    public void setCurrent_stock(int current_stock) {
        this.current_stock = current_stock;
    }

    public int getStock_to_purchase() {
        return stock_to_purchase;
    }

    public void setStock_to_purchase(int stock_to_purchase) {
        this.stock_to_purchase = stock_to_purchase;
    }
}
