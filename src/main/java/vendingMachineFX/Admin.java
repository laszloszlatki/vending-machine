package vendingMachineFX;
/**
 * author@ Laszlo Szlatki
 * author@ Patrick James O'Neill
 * date: 24/04/2020
 */

import java.io.IOException;
import java.util.List;

/**
 * An administrator who uses the vending machine
 */
public class Admin extends User implements CsvPrint {

    /**
     * Constructs an Admin object with no args
     * not used, good practice to create one
     */
    public Admin() {
        super();
    }

    /**
     * Constructs an Admin object
     *
     * @param name     admin name
     * @param password admin password
     */
    public Admin(String name, String password) {
        super(name, password);
    }

    /**
     * to validate admin credentials
     *
     * @param name     entered by user
     * @param password entered by user
     * @param admins   list of admins created from the Admin.dat csv file
     * @return the admin to use the system
     */
    public static Admin getAdminIfValid(String name, String password, List<Admin> admins) {
        Admin validAdmin = null;

        // looping through the list to get the admin with matching 'name'
        for (Admin admin : admins) {
            if (admin.getName().equals(name) && admin.getPassword().equals(password)) {
                validAdmin = admin;
            }
        }
        return validAdmin;
    }

    /**
     * gets the admin's name in a string format
     *
     * @return admin's name as string
     */
    @Override
    public String toString() {
        return "userName: " + getName();
    }

    /**
     * Determines if this admin is the same as the other admin.
     *
     * @param other the other admin
     * @return true if the admins are equal, false otherwise
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
        if (!(other instanceof Admin)) {
            return false;
        }
        // if name and password are the same, return true
        Admin b = (Admin) other;
        return getName().equals(b.getName()) && getPassword().equals(b.getPassword());
    }

    /**
     * gets formated admin details to be used for writing to csv
     *
     * @return name,password of admin
     */
    @Override
    public String toCsvString() {
        return getName() + "," + getPassword();
    }

    /**
     * exports up to date products to csv file
     *
     * @param newProducts list of products with latest details
     * @throws IOException if cannot write to file or file not found
     */
    public void exportProductsToCsv(List<Product> newProducts) throws IOException {
        // save new products list to csv
        CsvWriter.writeProductsToCSV("src/records/Product.dat", newProducts);
    }
}
