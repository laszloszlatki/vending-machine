package vendingMachineFX;
/**
 * author@ Laszlo Szlatki
 * author@ Patrick James O'Neill
 * date: 24/04/2020
 */

import java.util.ArrayList;
import java.util.List;

/**
 * A vending machine. is a 2D array. fixed size array(9) are the locations. On
 * each location (index) there is an arrayList of products.
 */
public class VendingMachine {

    // TODO location info to be updated from csv file
    private static final int NUMBER_OF_LOCATIONS = 9;
    private static final String[] LOCATIONS = {"A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3"};
    private static final String PATTERN = "[A-C]{1}[1-3]{1}";
    public static List<Product> products;
    private static List<Client> clients;
    private static List<Admin> admins;

    ////////////// not sure where to put admin and clients, probably here isn't the
    ////////////// best!!!!!!!!!!!!1

    /**
     * @return the clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * @return the admins
     */
    public List<Admin> getAdmins() {
        return admins;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @return the locations
     */
    public String[] getLocations() {
        return LOCATIONS;
    }

    // array of arrayList<Product>
    // array index are the locations in the machine
    // arrayList holds products

    /*
     * example: array - arrayList | | V V
     *
     * A1 - prod, prod, prod, prod A2 - null A3 - prod, prod B1 - prod B2 - prod,
     * prod, prod B3 - prod, prod, prod, prod, prod C1 - null C2 - prod, prod, prod
     * C3 - prod
     */
    private ArrayList<Product>[] locationProduct; // the array of Arraylist<Product>

    /**
     * Constructs a VendingMachine object.
     *
     * @param admins
     * @param clients
     * @param products
     */
    public VendingMachine(List<Product> products, List<Client> clients, List<Admin> admins) {
        locationProduct = new ArrayList[NUMBER_OF_LOCATIONS];
        VendingMachine.products = products;
        VendingMachine.clients = clients;
        VendingMachine.admins = admins;

        // Initialising new array
        for (int i = 0; i < NUMBER_OF_LOCATIONS; i++) {
            locationProduct[i] = new ArrayList<Product>();
        }

        // load products list to correct locations /////////////////////////////////
        this.preFillProducts(products); // still fills multiple times !!!!!!!!!!!

    }

    ///////////////////////////// get products from specific location
    public Product getProductOnLocation(String location) {
        // validate location with regex.Pattern
        if (location.matches(PATTERN)) {
            int locationIndex = getLocationIndex(location);
            // add product to types if not already in it
            if (isLocationEmpty(location)) {
                return null;
            } else {
                return locationProduct[locationIndex].get(0);
            }
        } else {
            throw new VendingException("Invalid location");
        }
    }

    /**
     * Buys a product from the specified location.
     *
     * @param location //@param p the product to buy
     * @param client
     */
    //////////////////////////////////////////// buy product from specific location
    public void buyProductPerLocation(String location, Client client) {
        int locationIndex = getLocationIndex(location);

//		for (int i = 0; i < locationProduct[locationIndex].size(); i++) {
        Product prod = locationProduct[locationIndex].get(/* i */0);
        double balance = client.checkBalance();
        if (prod.getPrice() <= balance) {
            client.deductBalance(prod.getPrice());
            locationProduct[locationIndex].remove(/* i */0);

            // update quantity field in Product for write to CSV later
            prod.setQuantity(locationProduct[locationIndex].size());
            return;
        } else {
            throw new VendingException("Insufficient money");
        }
    }

    /**
     * Adds a product to the vending machine.
     *
     * @param p        the product to add
     * @param quantity the quantity
     */
    ///////////////////////////// add products to specific locations
    public void addProductPerLocation(String location, Product p, int quantity) {
        // validate location with regex pattern
        if (location.matches(PATTERN)) {
            int locationIndex = getLocationIndex(location);
            for (int i = 0; i < quantity; i++) {
                // if product not exist in Products, add to it
                locationProduct[locationIndex].add(p);
            }
            // update quantity field in Product for write to CSV later
            p.setQuantity(locationProduct[locationIndex].size());
        } else {
            throw new VendingException("Invalid location");
        }
    }

    //////////// check if given location is empty
    public boolean isLocationEmpty(String location) {
        try {
            if (location.matches(PATTERN)) {
                int locationIndex = getLocationIndex(location);
                if (locationProduct[locationIndex].size() != 0) {
                    return false;
                }
            } else {
                throw new VendingException("Invalid location");
            }
        } catch (VendingException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    // get index for given location
    private int getLocationIndex(String location) {
        int locationIndex = -1;
        for (int i = 0; i < LOCATIONS.length; i++) {
            if (LOCATIONS[i].equals(location)) {
                locationIndex = i;
                break;
            }
        }
        return locationIndex;
    }

    // get product details with location and quantity
    public String getProductDetailsPerLocation(String location) {
        // i.e. A1 mars �1.20 8
        // location empty: print "empty"
        if (isLocationEmpty(location)) {
            return location + "\tEmpty";
        } else {
            // else print location, (product details, qty) <--from machine
            Product productInLocation = getProductOnLocation(location);
            ArrayList<Product> arrayOfProducts = locationProduct[getLocationIndex(location)];
            int numberOfProducts = arrayOfProducts.size();
            return location + "\t" + (productInLocation.toString()) + "\t" + numberOfProducts;
        }
    }

    // get product details and quantity formatted for GUI
    public String getProductDetailsPerLocationForGUI(String location) {
        // i.e. mars �1.20
        //      Quantity 8
        // location empty: print "empty"
        if (isLocationEmpty(location)) {
            return location + "\tEmpty";
        } else {
            // else print location, (product details, qty) <--from machine
            Product productInLocation = getProductOnLocation(location);
            ArrayList<Product> arrayOfProducts = locationProduct[getLocationIndex(location)];
            int numberOfProducts = arrayOfProducts.size();
            return ("Product: " + productInLocation.getDescription()
                    + "\nPrice: " + String.format("%.2f", productInLocation.getPrice())
                    + "\nQuantity: " + numberOfProducts);
        }
    }

    // fill machine with products read from csv file
    public void preFillProducts(List<Product> products) {
        for (String location : LOCATIONS) {
            for (Product p : products) {
                if (p.getLocation().equals(location)) {
                    addProductPerLocation(location, p, p.getQuantity());
                }
            }
        }
    }

    // check if there is any empty location used in GUI
    public boolean anyEmptyLocation() {
        boolean anyEmptyLocation = false;
        for (String location : LOCATIONS) {
            int locationIndex = getLocationIndex(location);
            if (locationProduct[locationIndex].size() != 0) {
                anyEmptyLocation = true;
            }
        }
        return anyEmptyLocation;
    }

    // check if there is any location with product on used in GUI
    public boolean allEmptyLocation() {
        boolean allEmpty = true;
        for (String location : LOCATIONS) {
            int locationIndex = getLocationIndex(location);
            if (locationProduct[locationIndex].size() != 0) {
                allEmpty = false;
            }
        }
        return allEmpty;
    }

    public void clearProductFromLocation(String location) {
        int locationIndex = getLocationIndex(location);
        Product prod = locationProduct[locationIndex].get(0);
        while (locationProduct[locationIndex].size() != 0) {
            locationProduct[locationIndex].remove(0);
        }
        // update quantity field in Product for write to CSV later
        prod.setQuantity(locationProduct[locationIndex].size());
    }
}