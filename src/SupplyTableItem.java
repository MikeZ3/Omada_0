import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SupplyTableItem {

    private Product product;
    private int id;
    private String name;
    private TextField textField;
    private Button button;


    public SupplyTableItem(Product product) {
        this.product = product;
        id = product.getId();
        name = product.getName();
        textField = new TextField();
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

    public void setName(String name) {
        this.name = name;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean equals(Object o) {
        return this.id == ((SupplyTableItem) o).id;
    }

}
