import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CheckOrderViewController implements Initializable {

    private ArrayList<Product> productsArrayList;

    @FXML
    private TableView<CheckOrderItem> tableView;
    @FXML
    private TableColumn<Integer, CheckOrderItem> idColumn;
    @FXML
    private TableColumn<String , CheckOrderItem> nameColumn;
    @FXML
    private TableColumn<String, CheckOrderItem> categoryColumn;
    @FXML
    private TableColumn<Integer, CheckOrderItem> min_stockColumn;
    @FXML
    private TableColumn<Integer, CheckOrderItem> recommended_stockColumn;
    @FXML
    private TableColumn<Integer, CheckOrderItem> current_stockColumn;
    @FXML
    private TableColumn<Integer, CheckOrderItem> stock_to_purchaseColumn;
    @FXML
    private Button backButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox<String> choiceBox;


    public void setProductsArrayList(ArrayList<Product> productsArrayList) {
        this.productsArrayList = productsArrayList;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            recommended_stockColumn.setCellValueFactory(new PropertyValueFactory<>("recommended_stock"));
            min_stockColumn.setCellValueFactory(new PropertyValueFactory<>("min_stock"));
            current_stockColumn.setCellValueFactory(new PropertyValueFactory<>("current_stock"));
            stock_to_purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("stock_to_purchase"));


            // Add products with less than then min_stock to the list
            ObservableList<CheckOrderItem> productObservableList = FXCollections.observableArrayList();
            for(Product product: productsArrayList) {
                if(product.getStock() <= product.getMin_stock()) {
                    productObservableList.add(new CheckOrderItem(product));
                }
            }

            tableView.setOnMouseClicked( event -> {
                if( event.getClickCount() == 2 ) {
                    System.out.println(tableView.getSelectionModel().getSelectedItem());
                }});

            choiceBox.getItems().addAll("All", "ID", "Name", "Category", "Minimum Stock", "Recommended Stock", "Current Stock", "Stock to Purchase");
            choiceBox.setValue("All");


            FilteredList<CheckOrderItem> filteredProducts = new FilteredList<>(productObservableList, b->true);

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredProducts.setPredicate(product -> {


                // If filter text is empty, display all products.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                switch (choiceBox.getValue()){
                    case "ID":
                        return String.valueOf(product.getId()).startsWith(newValue);
                    case "Name":
                        return product.getName().toLowerCase().contains(lowerCaseFilter);
                    case "Category":
                        return product.getCategory().toLowerCase().contains(lowerCaseFilter);
                    case "Current Stock":
                        return String.valueOf(product.getCurrent_stock()).startsWith(newValue);
                    case "Recommended Stock":
                        return String.valueOf(product.getRecommended_stock()).startsWith(newValue);
                    case "Minimum Stock":
                        return String.valueOf(product.getMin_stock()).startsWith(newValue);
                    case "Stock to Purchase":
                        return String.valueOf(product.getStock_to_purchase()).startsWith(newValue);
                    default:
                        if (String.valueOf(product.getId()).startsWith(newValue)) return true;
                        else if (product.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                        else if (String.valueOf(product.getMin_stock()).startsWith(newValue)) return true;
                        else if (String.valueOf(product.getRecommended_stock()).startsWith(newValue)) return true;
                        else if (String.valueOf(product.getCurrent_stock()).startsWith(newValue)) return true;
                        else if (String.valueOf(product.getStock_to_purchase()).startsWith(newValue)) return true;
                        else return product.getCategory().toLowerCase().contains(lowerCaseFilter);
                }

            }));

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<CheckOrderItem> sortedProducts = new SortedList<>(filteredProducts);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedProducts.comparatorProperty().bind(tableView.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            tableView.setItems(sortedProducts);

        });


    }


    public void viewMainScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

}
