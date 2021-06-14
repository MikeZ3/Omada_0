import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        File file = new File("products.ser");
        if(!file.exists()) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(getProductsFromJSON());
            objectOutputStream.close();
            fileOutputStream.close();
        }

        file = new File("cart.png");
        if(file.exists()) {
            stage.getIcons().add(new Image(new FileInputStream(file)));
        }

        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        stage.setTitle("Store");
        stage.setScene(new Scene(root));

        stage.setMaximized(true);
        stage.show();
    }

    public static ArrayList<Product> getProductsFromJSON() {

        ArrayList<Product> products = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("products.json", StandardCharsets.UTF_8));
            for (Object o : jsonArray) {
                JSONObject product = (JSONObject) o;
                int id = ((Long) product.get("id")).intValue();
                String name = (String) product.get("name");
                double price_sold = (double) product.get("price_sold");
                double price_bought = (double) product.get("price_bought");
                String category = (String) product.get("category");
                int stock = ((Long) product.get("stock")).intValue();
                int selves = ((Long) product.get("selves")).intValue();
                int recommended_stock = ((Long) product.get("recommended_stock")).intValue();
                int min_stock = ((Long) product.get("min_stock")).intValue();

                products.add(new Product(id, name, price_sold, price_bought, category, stock, selves, recommended_stock, min_stock));
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
