package vendingMachineFX;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;

/**
 * This program simulates a vending machine.
 */
public class Main {

    public static VendingMachine machine;

    protected static Scanner in;

    /**
     * main method to start the program
     *
     * @param args command line args (not used)
     * @throws IOException if problem reading from files
     */
    public static void main(String[] args) throws IOException {
        boolean more = true;
        in = new Scanner(System.in);

        // load all clients form clients.dat
        List<Client> clients = CsvReader.readClientsFromCSV("src/records/clients.dat");

        // load all admins form admin.dat
        List<Admin> admins = CsvReader.readAdminsFromCSV("src/records/admin.dat");

        // load all products form product.dat
        List<Product> products = CsvReader.readProductsFromCSV("src/records/product.dat");

        machine = new VendingMachine(products, clients, admins);

        System.out.println("-------------------------------------------\n"
                + "  Welcome to our wending machine simulator\n"
                + "-------------------------------------------\n"
                + "Please select the interface you want to use\n");

        while (more) {
            System.out.println("G)rafical User Interface\n" + "C)ommand Line Interface");

            String command = in.nextLine().toUpperCase();
            if (command.equals("G")) {
                System.out.println("GUI will open in a new window");
                Application.launch(MainMenuGUI.class, args);
                more = false;

            } else if (command.equals("C")) {

                MainMenuCLI menu = new MainMenuCLI(machine);
                menu.run(machine);
            }
        }
    }
}
