public class Product {

    private int id;
    private String name;
    private double price_sold; // per piece
    private double price_bought; // for all the pieces
    private String category;
    private int stock;
    private int selves;


    public Product(int id, String name, double price_sold, double price_bought, String category, int stock, int selves) {
        this.id = id;
        this.name = name;
        this.price_sold = DecimalUtils.round(price_sold,2);
        this.price_bought= DecimalUtils.round(price_bought,2);
        this.category = category;
        this.stock = stock;
        this.selves = selves;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((Product) o).id;
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

    public double getPrice_sold() {
        return price_sold;
    }

    public void setPrice_sold(double price) {
        this.price_sold = price;
    }

    public double getPrice_bought() {
        return price_bought;
    }

    public void setPrice_bought(double price_bought) {
        this.price_bought = price_bought;
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

    public int getSelves() {
        return selves;
    }

    public void setSelves(int selves) {
        this.selves = selves;
    }
}

