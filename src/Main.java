import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent mainViewParent = fxmlLoader.load();
        MainViewController controller = fxmlLoader.getController();

        controller.setProductsArrayList(getProductsFromJSON());

        stage.setTitle("Store");
        stage.setScene(new Scene(mainViewParent));

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.setMaximized(true);
        stage.show();
    }

    private static ArrayList<Product> getProductsFromJSON() {

        ArrayList<Product> products = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("products.json"));
            for (Object o : jsonArray) {
                JSONObject product = (JSONObject) o;
                int id = ((Long) product.get("id")).intValue();
                String name = (String) product.get("name");
                double price = (double) product.get("price");
                double price_bought = 0;
                String category = (String) product.get("category");
                int stock = ((Long) product.get("stock")).intValue();
                int selves = ((Long) product.get("selves")).intValue();

                products.add(new Product(id, name, price, price_bought, category, stock, selves));
            }
        } catch (IOException e) {
            System.out.println("Cannot Find products.json");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error in products.json");
            e.printStackTrace();
        }

        return products;
    }

}
