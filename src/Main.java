import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {
	
	
	public static ArrayList<Product> allProducts;

	public static void main(String[] args) {
		
		allProducts = new ArrayList<Product>();
		
		try {
			BufferedReader in = new BufferedReader (new FileReader("DATA.txt"));
			allProducts.clear();
			String line;
			try {
				while ( (line = in.readLine()) != null) {
					
					String[] vals = line.split(","); //Split the line at the commas, so I have 4 elements 
					
                    //Parse them as string or integer:
					int id = Integer.parseInt( vals[0] );
					String name = vals[1];
					double price = Double.parseDouble( vals[2] );
					int stock = Integer.parseInt( vals[3] );
					
					Product p = new Product(id, name, price, stock); //Create a new object using those elements
					allProducts.add(p); // Add the object to an ArrayList of all products
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
		
}
