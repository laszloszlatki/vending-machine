package vendingMachineFX;
/**
 * author@ Laszlo Szlatki
 * author@ Patrick James O'Neill
 * date: 24/04/2020
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Client who uses the vending machine
 */
public class Client extends User implements CsvPrint {

    private double balance;

    /**
     * Constructs a Client object
     */
    public Client() {
        super();
        this.balance = 0.0;
    }

    /**
     * Constructs a Client object
     *
     * @param name     client name
     * @param password client password
     * @param balance  client balance
     */
    public Client(String name, String password, double balance) {
        super(name, password);
        this.balance = balance;
    }

    /**
     * Gets the balance
     *
     * @return the balance
     */
    public double checkBalance() {
        return balance;
    }

    /**
     * Reduce balance by the price of the product
     *
     * @param price price of the product to be deducted from balance
     */
    public void deductBalance(double price) {
        if (this.balance >= price) {
            this.balance -= price;
        }
    }

    /**
     * validates client details against the data from the Clients.dat file
     *
     * @param name that user entered
     * @param password that user enteered
     * @param clients list of clients read form csv file
     * @return validated client to use machine, or null if invalid details entered
     */
    public static Client getClientIfValid(String name, String password, List<Client> clients) {
        // TODO!!!!!!!!!!!!!!!!!!!!!!!!!
        // below is only to bypass csv file
        Client validClient = null;

        // looping through the list to get the client with matching 'name'
        for (Client client : clients) {
            if (client.getName().equals(name) && client.getPassword().equals(password)) {
                validClient = client;
            }
        }
        return validClient;

    }

    /**
     * log client out and save updated balance to file
     *
     * @param machine the vending machine to log out from
     * @throws IOException if problem writing to file
     */
    @Override
    public void logout(VendingMachine machine) throws IOException {
        saveToFile(machine);
        // return program flow to main menu
        MainMenuCLI menu = new MainMenuCLI(machine);
        menu.run(machine);
    }

    /**
     * method to save client data to csv file
     *
     * @param machine that stores most up to date client list
     * @throws IOException if problem writing to Clients.dat
     */
    public void saveToFile(VendingMachine machine) throws IOException {
        // construct the new clients list
        List<Client> oldClients = machine.getClients();
        List<Client> newClients = new ArrayList<>();
        for (Client c : oldClients) {
            if (c.getName() == this.getName()) {
                newClients.add(this);
            } else {
                newClients.add(c);
            }
        }
        // save new client list to csv
        CsvWriter.writeClientsToCSV("src/records/Clients.dat", newClients);
    }

    /**
     * gets client's details in a string format
     *
     * @return client's details as a string
     */
    @Override
    public String toString() {
        return "userName: " + getName() + "\t balance: �" + String.format("%.2f", balance);
    }

    /**
     * Determines of this client is the same as the other client.
     *
     * @param other the other client
     * @return true if the clients are equal, false otherwise
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
        if (!(other instanceof Client)) {
            return false;
        }
        // if name, password and balance are the same, return true
        Client b = (Client) other;
        return getName().equals(b.getName()) && getPassword().equals(b.getPassword()) && balance == (b.balance);
    }

    /**
     * gets client details  formatted to match csv requirements
     *
     * @return client details: name,balance,password
     */
    @Override
    public String toCsvString() {
        return getName() + "," + balance + "," + getPassword();
    }
}
