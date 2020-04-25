package vendingMachineFX;
/**
 * author@ Laszlo Szlatki
 * author@ Patrick James O'Neill
 * date: 24/04/2020
 */

import java.io.IOException;
import java.util.Scanner;

/**
 * A class that allows client's interaction with vending machine.
 */
public class ClientMenuCLI {
    protected Scanner in;
    Client client;

    /**
     * Constructs a ClientMenuCLI object
     *
     * @param client who want's to use the machines
     */
    public ClientMenuCLI(Client client) {
        in = new Scanner(System.in);
        this.client = client;
    }

    /**
     * Runs the client's vending machine system.
     *
     * @param machine the vending machine
     */
    public void run(VendingMachine machine) throws IOException {
        boolean more = true;

        while (more) {
            System.out.println("S)how products  B)uy  L)ogout");
            String command = in.nextLine().toUpperCase();
            switch (command) {
                case "S": ////////////////////////////////// lists all products in the machine
                    // cycle through each location
                    for (String location : machine.getLocations()) {
                        // call getProductDetails() from machine
                        System.out.println(machine.getProductDetailsPerLocation(location));
                    }
                    break;
                case "B": ////////////////////////////////// buy a product form the machine
                    // displays all products from the vending machine
                    // cycle through each location
                    for (String location : machine.getLocations()) {
                        // call getProductDetails() from machine
                        System.out.println(machine.getProductDetailsPerLocation(location));
                    }
                    try {
                        System.out.println("Enter location:");
                        String location = in.nextLine().toUpperCase();
                        // ensure location to take product from, isn't empty
                        if (!machine.isLocationEmpty(location)) {
                            // get first product from the product[] in the location, to be passed to the
                            // buyProduct method
                            Product p = machine.getProductOnLocation(location);
                            machine.buyProductPerLocation(location, /*p, */client);
                            System.out.println(
                                    "Purchased one " + p.getDescription() + " for �" + String.format("%.2f", p.getPrice()));
                            System.out.println(
                                    "You have �" + String.format("%.2f", client.checkBalance()) + " left on your account");
                        } else {
                            System.out.println("destination is empty. Try to select a different location.");
                        }
                    } catch (VendingException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "L":  ////////////////////////////////////////////////// log out form machine
                    client.logout(machine);
                    break;
            }
        }
    }
}