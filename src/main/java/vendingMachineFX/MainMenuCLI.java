package vendingMachineFX;

import java.io.IOException;
import java.util.Scanner;

/**
 * A menu from the vending machine.
 */
public class MainMenuCLI {

    protected Scanner in;

    /**
     * Constructs a VendingMachineMenuCLI object
     *
     * @param machine the menu will work on
     */
    public MainMenuCLI(VendingMachine machine) {
        super();
        in = new Scanner(System.in);
    }

    /**
     * Runs the vending machine system.
     *
     * @param machine the menu will run on
     */
    public void run(VendingMachine machine) throws IOException {
        boolean more = true;

        while (more) {
            System.out.println("C)ustomer  A)dmin");
            String command = in.nextLine().toUpperCase();

            if (command.equals("C")) {

                // request userName + password
                System.out.println("Enter name: ");
                String name = in.nextLine();
                System.out.println("Enter password: ");
                String password = in.nextLine();

                // validate userName and password
                Client client = Client.getClientIfValid(name, password, machine.getClients());
                if (client == null) {
                    System.out.println("Incorrect details entered");
                } else {
                    System.out.println(client);
                    ClientMenuCLI menu = new ClientMenuCLI(client);
                    menu.run(machine);
                }
            } else if (command.equals("A")) {

                // request userName + password
                System.out.println("Enter userName: ");
                String userName = in.nextLine();
                System.out.println("Enter password: ");
                String password = in.nextLine();

                // validate userName and password
                Admin admin = Admin.getAdminIfValid(userName, password, machine.getAdmins());
                if (admin == null) {
                    System.out.println("Incorrect details entered");
                } else {
                    System.out.println(admin);
                    AdminMenuCLI menu = new AdminMenuCLI(admin);
                    menu.run(machine);
                }
            }
        }
    }
}
