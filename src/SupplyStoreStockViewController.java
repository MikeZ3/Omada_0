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

public class SupplyStoreStockViewController implements Initializable {

    private ArrayList<Product> productsArrayList;

    private boolean isSupplyStock = false;

    private Purchase purchase;

    private ObservableList<SupplyTableItem> supplyTableItems;

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Integer, Product> idColumn;
    @FXML
    private TableColumn<String , Product> nameColumn;
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



    @FXML
    private TableView<SupplyTableItem> supplyTableView;
    @FXML
    private TableColumn<SupplyTableItem, Integer> idSupplyColumn;
    @FXML
    private TableColumn<SupplyTableItem, String> nameSupplyColumn;
    @FXML
    private TableColumn<SupplyTableItem, Spinner<Integer>> quantitySupplyColumn;
    @FXML
    private TableColumn<SupplyTableItem, Button> removeButtonColumn;


    public void setProductsArrayList(ArrayList<Product> productsArrayList) {
        this.productsArrayList = productsArrayList;
    }

    public void setSupplyStock(boolean b) {
        isSupplyStock = b;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            selvesColumn.setCellValueFactory(new PropertyValueFactory<>("selves"));


            ObservableList<Product> productObservableList = FXCollections.observableArrayList(productsArrayList);

            supplyTableItems = FXCollections.observableArrayList();


            //Add to basket with double click
            productsTableView.setOnMouseClicked( event -> {
                if( event.getClickCount() == 2 ) {
                    addToSupply(productsTableView.getSelectionModel().getSelectedItem());
                }});
            //Add to basket with enter
            productsTableView.setOnKeyPressed( keyEvent -> {
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    addToSupply(productsTableView.getSelectionModel().getSelectedItem());
                }
            });

            choiceBox.getItems().addAll("All", "ID", "Name", "Category", "Stock", "Selves");
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
                    case "Category":
                        return product.getCategory().toLowerCase().contains(lowerCaseFilter);
                    case "Stock" :
                        return String.valueOf(product.getStock()).startsWith(newValue);
                    case "Selves" :
                        return String.valueOf(product.getSelves()).startsWith(newValue);
                    default:
                        if (String.valueOf(product.getId()).startsWith(newValue)) return true;
                        else if (product.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                        else if (String.valueOf(product.getStock()).startsWith(newValue)) return true;
                        else if (String.valueOf(product.getSelves()).startsWith(newValue)) return true;
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



            idSupplyColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameSupplyColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            quantitySupplyColumn.setCellValueFactory(new PropertyValueFactory<>("textField"));
            removeButtonColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
            supplyTableView.setItems(supplyTableItems);




        });

    }

    private void addToSupply(Product product) {

        SupplyTableItem supplyTableItem = new SupplyTableItem(product);

        if(!supplyTableItems.contains(supplyTableItem)) {
            supplyTableItem.getButton().setOnAction(e -> removeFromSupply(supplyTableItem));
            supplyTableItems.add(supplyTableItem);
        } else {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setHeaderText("Το προϊόν είναι ήδη προς εφοδιασμό");
            infoAlert.showAndWait();
        }

    }


    private void removeFromSupply(SupplyTableItem supplyTableItem) {
        supplyTableItems.remove(supplyTableItem);
    }

    public void clearSupply() {
        supplyTableItems.clear();
    }


    public void completeSupply() {

        if(supplyTableItems.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Παρακαλώ εισάγετε προϊόντα");
            errorAlert.showAndWait();
            return;
        }

        for(SupplyTableItem supplyTableItem: supplyTableItems) {
            try {
                Integer.parseInt(supplyTableItem.getTextField().getText());
            }
            catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Σφάλμα");
                errorAlert.setContentText("Ελέγξτε ότι η ποσότητα είναι συμπληρωμένη σωστά");
                errorAlert.showAndWait();
                return;
            }
        }

        if(!isSupplyStock) {

            ArrayList<String> products_not_supplied = new ArrayList<>();
            for (SupplyTableItem supplyTableItem : supplyTableItems) {
                int quantity = Integer.parseInt(supplyTableItem.getTextField().getText());
                if (supplyTableItem.getProduct().getStock() >= quantity) {
                    supplyTableItem.getProduct().setStock(supplyTableItem.getProduct().getStock() - quantity);
                    supplyTableItem.getProduct().setSelves(supplyTableItem.getProduct().getSelves() + quantity);
                } else {
                    //If the stock is not enough supply it all
                    products_not_supplied.add("ID: " + supplyTableItem.getProduct().getId() + " Name: " + supplyTableItem.getProduct().getName() + " Ποσότητα που εφοδιάστηκε: " + supplyTableItem.getProduct().getStock());
                    supplyTableItem.getProduct().setSelves(supplyTableItem.getProduct().getSelves() + supplyTableItem.getProduct().getStock());
                    supplyTableItem.getProduct().setStock(0);
                }
            }

            if (!products_not_supplied.isEmpty()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Τα ακόλουθα προϊόντα δεν εφοδιάστηκαν στην ποσότητα που ζητήσατε:" + "\n");
                for (String product : products_not_supplied) {
                    errorAlert.setHeaderText(errorAlert.getHeaderText() + product + "\n");
                }
                errorAlert.showAndWait();
            }
        } else {
            HashMap<Product, Integer> items = new HashMap<>();
            for(SupplyTableItem supplyTableItem: supplyTableItems) {
                items.put(supplyTableItem.getProduct(), Integer.parseInt(supplyTableItem.getTextField().getText()));
            }
            purchase = new Purchase(items);
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("products.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(productsArrayList);
            objectOutputStream.close();
            fileOutputStream.close();

            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setHeaderText("Ο εφοδιασμός Ολοκληρώθηκε" + (isSupplyStock ? "\nΚόστος: " + purchase.getPurchaseCost() + "€" : ""));

            infoAlert.showAndWait();
            supplyTableItems.clear();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Σφάλμα");
            errorAlert.setContentText("Οι αλλαγές δεν αποθηκέυτηκαν");
            errorAlert.showAndWait();
            e.printStackTrace();
        }



    }



    public void viewMainScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }
}
