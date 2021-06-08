import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class BasketTableItem {
    private Product product;
    private int id;
    private String name;
    private double price;
    private Spinner<Integer> spinner;
    private Button button;

    public BasketTableItem(Product product) {
        this.product = product;
        id = product.getId();
        name = product.getName();
        price = product.getPrice_sold();
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        spinnerValueFactory.setValue(1);
        spinner = new Spinner<>(spinnerValueFactory);
        button = new Button("Remove");
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

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Spinner<Integer> getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner<Integer> spinner) {
        this.spinner = spinner;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean equals(Object o) {
        return this.id == ((BasketTableItem) o).id;
    }
}
