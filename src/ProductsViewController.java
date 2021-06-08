import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsViewController implements Initializable {

    private ArrayList<Product> productsArrayList;

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Integer, Product> idColumn;
    @FXML
    private TableColumn<String , Product> nameColumn;
    @FXML
    private TableColumn<Double, Product> price_soldColumn;
    @FXML
    private TableColumn<Double, Product> price_boughtColumn;
    @FXML
    private TableColumn<String, Product> categoryColumn;
    @FXML
    private TableColumn<Integer, Product> stockColumn;
    @FXML
    private TableColumn<Integer, Product> selvesColumn;
    @FXML
    private Button backButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox<String> choiceBox;


    // Set Arguments


    public void setProductsArrayList(ArrayList<Product> productsArrayList) {
        this.productsArrayList = productsArrayList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            price_soldColumn.setCellValueFactory(new PropertyValueFactory<>("price_sold"));
            price_boughtColumn.setCellValueFactory(new PropertyValueFactory<>("price_bought"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            selvesColumn.setCellValueFactory(new PropertyValueFactory<>("selves"));


            ObservableList<Product> productObservableList = FXCollections.observableArrayList(productsArrayList);

            tableView.setOnMouseClicked( event -> {
                if( event.getClickCount() == 2 ) {
                    System.out.println( tableView.getSelectionModel().getSelectedItem());
                }});

            choiceBox.getItems().addAll("All", "ID", "Name", "Price Sold", "Price Bought", "Category", "Stock", "Selves");
            choiceBox.setValue("All");


            FilteredList<Product> filteredProducts = new FilteredList<>(productObservableList, b->true);

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
                    case "Price Sold":
                        return String.valueOf(product.getPrice_sold()).startsWith(newValue);
                    case "Price Bought":
                        return String.valueOf(product.getPrice_bought()).startsWith(newValue);
                    case "Category":
                        return product.getCategory().toLowerCase().contains(lowerCaseFilter);
                    case "Stock" :
                        return String.valueOf(product.getStock()).startsWith(newValue);
                    case "Selves" :
                        return String.valueOf(product.getSelves()).startsWith(newValue);
                    default:
                        if (String.valueOf(product.getId()).startsWith(newValue)) return true;
                        else if (product.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                        else if (String.valueOf(product.getPrice_sold()).startsWith(newValue)) return true;
                        else if (String.valueOf(product.getPrice_bought()).startsWith(newValue)) return true;
                        else return product.getCategory().toLowerCase().contains(lowerCaseFilter);
                }

            }));

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<Product> sortedProducts = new SortedList<>(filteredProducts);

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