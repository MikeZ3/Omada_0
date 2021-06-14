import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class AddProductViewController {

    @FXML
    private Button backButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField price_SoldTextField;
    @FXML
    private TextField price_BoughtTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private TextField selvesTextField;
    @FXML
    private TextField recommended_StockTextField;
    @FXML
    private TextField min_StockTextField;

    private ArrayList<Product> productsArrayList;

    public void setProductsArrayList(ArrayList<Product> productsArrayList) {
        this.productsArrayList = productsArrayList;
    }


    public void createProduct() {



        if(!nameTextField.getText().isEmpty() && !price_SoldTextField.getText().isEmpty() && !price_BoughtTextField.getText().isEmpty()
        && !categoryTextField.getText().isEmpty() && !stockTextField.getText().isEmpty() && !selvesTextField.getText().isEmpty()
        && !recommended_StockTextField.getText().isEmpty() && !min_StockTextField.getText().isEmpty()) {

            int id = productsArrayList.get(productsArrayList.size() - 1).getId() + 1;
            String name = nameTextField.getText();
            String category = categoryTextField.getText();
            try {
                double price_sold = Double.parseDouble(price_SoldTextField.getText());
                double price_bought = Double.parseDouble(price_BoughtTextField.getText());
                int stock = Integer.parseInt(stockTextField.getText());
                int selves = Integer.parseInt(selvesTextField.getText());
                int recommended_stock = Integer.parseInt(recommended_StockTextField.getText());
                int min_stock = Integer.parseInt(min_StockTextField.getText());
                Product product = new Product(id, name, price_sold, price_bought, category, stock, selves, recommended_stock, min_stock);
                productsArrayList.add(product);

                FileOutputStream fileOutputStream = new FileOutputStream("products.ser");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(productsArrayList);
                objectOutputStream.close();
                fileOutputStream.close();

                System.out.println("Product Successfully Created");

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Το προϊόν δημιουργήθηκε");
                infoAlert.showAndWait();
                nameTextField.setText("");
                price_SoldTextField.setText("");
                price_BoughtTextField.setText("");
                categoryTextField.setText("");
                stockTextField.setText("");
                selvesTextField.setText("");
            }
            catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Σφάλμα");
                errorAlert.setContentText("Ελέγξτε ότι τα παιδία είναι συμπληρωμένα σωστά");
                errorAlert.showAndWait();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Σφάλμα");
                errorAlert.setContentText("Οι αλλαγές δεν αποθηκέυτηκαν");
                errorAlert.showAndWait();
                e.printStackTrace();
                e.printStackTrace();
            }







        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Σφάλμα");
            errorAlert.setContentText("Παρακαλώ συμπληρώστε όλα τα πεδία");
            errorAlert.showAndWait();
        }


    }

    public void viewMainScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }
}
