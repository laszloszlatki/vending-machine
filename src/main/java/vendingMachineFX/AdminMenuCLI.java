package vendingMachineFX;
/**
 * author@ Laszlo Szlatki
 * author@ Patrick James O'Neill
 * date: 24/04/2020
 */

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class to manage admin interaction with the vending machine.
 */
public class AdminMenuCLI {
    protected Scanner in;
    Admin admin;

    /**
     * Constructs a AdminMenuCLI object
     *
     * @param admin who want's to use the machines
     */
    public AdminMenuCLI(Admin admin) {
        in = new Scanner(System.in);
        this.admin = admin;
    }

    /**
     * Runs the admin's vending machine system.
     *
     * @param machine the vending machine
     */
    public void run(VendingMachine machine) throws IOException {
        boolean more = true;

        while (more) {
            // display admin menu options
            System.out.println("S)how products  A)dd product  T)opUp Stock  P)owerOff  L)ogout");
            String command = in.nextLine().toUpperCase();

            switch (command) {
                case "S": ///////////////// displays all products from the vending machine
                    // cycle through each location
                    for (String location : machine.getLocations()) {
                        // call getProductDetails() from machine
                        System.out.println(machine.getProductDetailsPerLocation(location));
                    }
                    break;
                case "A": ////////////////////////////// add new product to empty location
                    System.out.println("Location:");
                    String location = in.nextLine().toUpperCase();
                    System.out.println("Description:");
                    String description = in.nextLine();
                    System.out.println("Price:");
                    try {
                        double price = in.nextDouble();
                        System.out.println("Quantity:");
                        int quantity = in.nextInt();
                        in.nextLine(); // read the new-line character
                        // if destination empty, add product
                        try {
                            // if location is empty, add product
                            if (machine.isLocationEmpty(location)) {
                                machine.addProductPerLocation(location, new Product(description, location, price, quantity),
                                        quantity);
                                machine.products.add(new Product(description, location, price, quantity));
                            } else {
                                System.out.println(
                                        "destination is not empty. Try to TopUp current product or select new location.");
                            }
                        } catch (VendingException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid price or quantity");
                    }
                    break;
                case "T": //////////////////// top up not empty locations with same product (description & price)
                    System.out.println("Location:");
                    try {
                        location = in.nextLine().toUpperCase();
                        // if location is empty, cannot topUp, need to add product
                        if (machine.isLocationEmpty(location)) {
                            System.out.println("Location is empty, cannot topUp. Try to Add product instead.");
                            // else request additional qty
                        } else {
                            System.out.println("Quantity:");
                            int quantity = in.nextInt();
                            in.nextLine(); // read the new-line character
                            // find first product from the product[] in the location
                            Product p = machine.getProductOnLocation(location);
                            machine.addProductPerLocation(location, p, quantity);
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid quantity");
                    }
                    break;
                case "L": ///////////////////// logout wont update Product.dat file
                    admin.logout(machine);
                    break;
                case "P": ///////////////////// power off update Product.dat file and stops program
                    admin.exportProductsToCsv(machine.getProducts());
                    System.exit(0);
            }
        }
    }
}