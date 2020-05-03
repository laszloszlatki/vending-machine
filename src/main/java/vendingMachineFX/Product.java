package vendingMachineFX;

/**
 * A product in a vending machine.
 */
public class Product implements Comparable<Product>, CsvPrint {
	private String description;
	private String location;
	private double price;
	private int quantity;

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Constructs a Product object
	 * 
	 * @param description the description of the product
	 * @param location    the product's location in the vending machine
	 * @param price       the price of the product
	 * @param quantity    the quantity available in the machine
	 */
	public Product(String description, String location, double price, int quantity) {
		this.description = description;
		this.location = location;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * Gets the product form given location
	 * 
	 * @param location of the product to be returned
	 * @return product on the queried location
	 */
	public Product getProduct(String location) {
		if (this.location == location) {
			return this;
		}
		return null;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the location in the machine
	 * 
	 * @return location in the vending machine
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets the price.
	 * 
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Gets the reminding quantity
	 * 
	 * @return qty available in the machine
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Determines of this product is the same as the other product.
	 * 
	 * @param other the other product
	 * @return true if the products are equal, false otherwise
	 */
	@Override
	public boolean equals(Object other) {
		// if compared to null, return false
		if (other == null) {
			return false;
		}
		// if compared to itself, return true
		if (other == this) {
			return true;
		}
		// if other is not the same type as this, return false
		if (!(other instanceof Product)) {
			return false;
		}
		// if all fields are the same, return true
		Product b = (Product) other;
		return description.equals(b.description) && price == b.price;
	}

	/**
	 * Formats the product's description and price to 2 decimal with filling 0-s
	 */
	public String toString() {
		return  description + "\t @ \u20AC" + String.format("%.2f", price); // \u20AC is unicode for €
	}

	/**
	 * check if two products are the same
	 *
	 * @param that product to be compared to
	 * @return true if same, else otherwise
	 */
	@Override
	public int compareTo(Product that) {
		return that.description.compareTo(this.description);
	}

	/**
	 * to format to csv requirements
	 *
	 * @return the formatted string
	 */
	@Override
	public String toCsvString() {
		return description + "," + location + "," + String.format("%.2f", price) + "," + quantity;
	}
}
