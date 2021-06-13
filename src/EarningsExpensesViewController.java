import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EarningsExpensesViewController implements Initializable {

    @FXML
    private Label ordersLabel;
    @FXML
    private Label  returnsLabel;
    @FXML
    private Label purchasesLabel;
    @FXML
    private Label earningsLabel;
    @FXML
    private Label expensesLabel;
    @FXML
    private Label profitLabel;
    @FXML
    private Button backButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        double orders = DecimalUtils.round(Order.getTotalCharge(),2);
        double returns = DecimalUtils.round(Return.getTotalCost(), 2);
        double purchases = DecimalUtils.round(Purchase.getTotalCost(), 2);

        ordersLabel.setText(orders + "€");
        returnsLabel.setText(returns + "€");
        purchasesLabel.setText(purchases + "€");
        earningsLabel.setText(orders + "€");
        expensesLabel.setText(DecimalUtils.round(returns + purchases, 2) + "€");
        profitLabel.setText(DecimalUtils.round(orders - returns - purchases, 2) + "€");


    }

    public void viewMainScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

}
