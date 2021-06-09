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
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class OrderViewController implements Initializable {

    private ArrayList<Product> productsArrayList;

    private ObservableList<BasketTableItem> basketTableItems;

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Integer, Product> idColumn;
    @FXML
    private TableColumn<String , Product> nameColumn;
    @FXML
    private TableColumn<Double, Product> priceColumn;
    @FXML
    private TableColumn<String, Product> categoryColumn;
    @FXML
    private Button backButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox<String> choiceBox;



    @FXML
    private TableView<BasketTableItem> orderTableView;
    @FXML
    private TableColumn<BasketTableItem, Integer> idOrderColumn;
    @FXML
    private TableColumn<BasketTableItem, String> nameOrderColumn;
    @FXML
    private TableColumn<BasketTableItem, Double> priceOrderColumn;
    @FXML
    private TableColumn<BasketTableItem, Spinner<Integer>> quantityOrderColumn;
    @FXML
    private TableColumn<BasketTableItem, Button> removeButtonColumn;
    @FXML
    private Label totalPriceLabel;


    public void setProductsArrayList(ArrayList<Product> productsArrayList) {
        this.productsArrayList = productsArrayList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price_sold"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));


            ObservableList<Product> productObservableList = FXCollections.observableArrayList(productsArrayList);

            basketTableItems = FXCollections.observableArrayList();


            //Add to basket with double click
            productsTableView.setOnMouseClicked( event -> {
                if( event.getClickCount() == 2 ) {
                    addToBasket(productsTableView.getSelectionModel().getSelectedItem());
                }});
            //Add to basket with enter
            productsTableView.setOnKeyPressed( keyEvent -> {
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    addToBasket(productsTableView.getSelectionModel().getSelectedItem());
                }
            });

            choiceBox.getItems().addAll("All", "ID", "Name", "Price", "Category");
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
                    case "Price":
                        return String.valueOf(product.getPrice_sold()).startsWith(newValue);
                    case "Category":
                        return product.getCategory().toLowerCase().contains(lowerCaseFilter);
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
            sortedProducts.comparatorProperty().bind(productsTableView.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            productsTableView.setItems(sortedProducts);



            idOrderColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameOrderColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceOrderColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityOrderColumn.setCellValueFactory(new PropertyValueFactory<>("spinner"));
            removeButtonColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
            orderTableView.setItems(basketTableItems);




        });

    }

    private void addToBasket(Product product) {
        BasketTableItem basketTableItem = new BasketTableItem(product);

        int index = basketTableItems.indexOf(basketTableItem);
        if(index != -1) {
            basketTableItems.get(index).getSpinner().getValueFactory().setValue(basketTableItems.get(index).getSpinner().getValue() + 1);
        } else {
            basketTableItem.getButton().setOnAction(e -> removeFromBasket(basketTableItem));
            basketTableItem.getSpinner().valueProperty().addListener((obs, oldValue, newValue) -> {
                if(oldValue < newValue) {
                    totalPriceLabel.setText(String.valueOf(DecimalUtils.round(Double.parseDouble(totalPriceLabel.getText()) + basketTableItem.getPrice(), 2)));
                } else {
                    totalPriceLabel.setText(String.valueOf(DecimalUtils.round(Double.parseDouble(totalPriceLabel.getText()) - basketTableItem.getPrice(), 2)));
                }
            });
            basketTableItems.add(basketTableItem);
            totalPriceLabel.setText(String.valueOf(DecimalUtils.round(basketTableItem.getPrice() + Double.parseDouble(totalPriceLabel.getText()), 2)));
        }
    }


    private void removeFromBasket(BasketTableItem basketTableItem) {

        basketTableItems.remove(basketTableItem);

        totalPriceLabel.setText(String.valueOf(DecimalUtils.round(Double.parseDouble(totalPriceLabel.getText()) - basketTableItem.getSpinner().getValue() * basketTableItem.getPrice(), 2)));
    }

    public void clearBasket() {
        basketTableItems.clear();
        totalPriceLabel.setText("0");
    }


    public void completeOrder() {
        HashMap<Product, Integer> items = new HashMap<>();

        if(basketTableItems.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Παρακαλώ εισάγετε προϊόντα");
            errorAlert.showAndWait();
            return;
        }

        for(BasketTableItem basketTableItem : basketTableItems) {
            //Check if there are enough
            if(basketTableItem.getProduct().getStock() > basketTableItem.getSpinner().getValue()) {
                items.put(basketTableItem.getProduct(), basketTableItem.getSpinner().getValue());
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Δεν υπάρχει αρκετή ποσότητα");
                errorAlert.setContentText("Προϊόν: \n" + basketTableItem.getProduct().getName()
                        + "\n Συνολική διαθέσιμη ποσότητα: " + basketTableItem.getProduct().getStock() + "\nΗ παραγγελεία δεν ολοκληρώθηκε");
                errorAlert.showAndWait();
                return;
            }
        }

        Order.getOrders().add(new Order(items));

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("products.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(productsArrayList);
            objectOutputStream.close();
            fileOutputStream.close();

            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setHeaderText("Η παραγγελία ολοκληρώθηκε");
            infoAlert.showAndWait();
            basketTableItems.clear();
            totalPriceLabel.setText("0");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void viewMainScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

}
