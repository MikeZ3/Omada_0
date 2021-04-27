import java.math.BigDecimal;

public class Product {
	
	
	private int id;
	private String name;
	private double price_sold; // per piece 
	private int stock;
	private double price_bought; // for all the pieces   => stock*price_sold/0.015
	
	
	public Product(int anId, String aName, double aPrice, int Stock, double price_Bought) {
		this.id=anId;
		this.name=aName;
		this.price_sold=DecimalUtils.round(aPrice,2);
		this.stock=Stock;
		this.price_bought=DecimalUtils.round(price_Bought,2);
	}

	public double getPriceBought() {
		return price_bought;
	}

	public void setPriceBought(double price_bought) {
		this.price_bought = price_bought;
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

	public double getPriceSold() {
		return price_sold;
	}

	public void setPriceSold(double price) {
		this.price_sold = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
    public static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
    public static class DecimalUtils {
    	 
        public static double round(double value, int numberOfDigitsAfterDecimalPoint) {
            BigDecimal bigDecimal = new BigDecimal(value);
            bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                    BigDecimal.ROUND_HALF_UP);
            return bigDecimal.doubleValue();
        }
    }

}

