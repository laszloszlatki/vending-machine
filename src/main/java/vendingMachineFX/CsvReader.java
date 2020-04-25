package vendingMachineFX;
/**
 * author@ Laszlo Szlatki
 * author@ Patrick James O'Neill
 * date: 24/04/2020
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * https://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
   
    Here are the steps to load data from CSV file in Java without using any third party library :

    Open CSV file using FileReader object
    Create BufferedReader from FileReader
    Read file line by line using readLine() method
    Split each line on comma to get an array of attributes using String.split() method
    Create object of Book class from String array using new Book()
    Add those object into ArrayList using add() method
    Return the List of books to caller
*/
public class CsvReader {

    /**
     * reads clients from csv file and returns them as a list
     *
     * @param fileName to be read
     * @return list of customers created from the csv file
     * @throws IOException if problem reading from file
     */
    public static List<Client> readClientsFromCSV(String fileName) throws IOException {
        List<Client> clients = new ArrayList<>();

        // check if file exists and is a file
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {

            Path pathToFile = Paths.get(fileName);
            // create an instance of BufferedReader
            BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII);

            // read the first line from the file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use String.split to load a string array with the values from
                // each line of the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                Client client = createClient(attributes);

                // adding client into ArrayList
                clients.add(client);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

            br.close();
        }
        return clients;
    }

    /**
     * creates client objects from passed in string array
     *
     * @param metadata details of the client as string array
     * @return client object
     */
    private static Client createClient(String[] metadata) {
        String name = metadata[0].trim();
        double balance = Double.parseDouble(metadata[1].trim());
        String password = metadata[2].trim();

        // create and return client of this metadata
        return new Client(name, password, balance);
    }

    /**
     * reads admins from csv file and returns them as a list
     *
     * @param fileName to be read
     * @return list of admins created from the csv file
     * @throws IOException if problem reading from file
     */
    public static List<Admin> readAdminsFromCSV(String fileName) throws IOException {
        List<Admin> admins = new ArrayList<>();

        // check if file exists and is a file
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            Path pathToFile = Paths.get(fileName);

            // create an instance of the BufferedReader
            BufferedReader br = Files.newBufferedReader(pathToFile);

            // read the first line form the file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use String.split to load a string array with the values from
                // each line of the file, using comma as the delimiter
                String[] attributes = line.split(",");

                Admin admin = createAdmin(attributes);

                // adding admin to the ArrayList
                admins.add(admin);

                // read next line before looping
                // if EOF reached, line will be null and exits the loop
                line = br.readLine();
            }
        }
        return admins;
    }

    /**
     * creates Admin object from passed in string array
     *
     * @param metadata details of the admin as string array
     * @return admin object
     */
    private static Admin createAdmin(String[] metadata) {
        String name = metadata[0].trim();
        String password = metadata[1].trim();

        // create and return admin of this metadata
        return new Admin(name, password);
    }

    /**
     * reads products from csv file and returns them as a list
     *
     * @param fileName to be read
     * @return list of products created from the csv file
     */
    public static List<Product> readProductsFromCSV(String fileName) {
        List<Product> products = new ArrayList<>();

        // check if file exists and is a file
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            Path pathToFile = Paths.get(fileName);

            // create an instance of the BufferedReader
            // using try with resource, Java 7 feature to close resources
            // as program exits the try{}, file is closed automatically
            try (BufferedReader br = Files.newBufferedReader(pathToFile)) {

                // read the first line form the file
                String line = br.readLine();

                // loop until all lines are read
                while (line != null) {

                    // use String.split to load a string array with the values from
                    // each line of the file, using comma as the delimiter
                    String[] attributes = line.split(",");

                    Product product = createProduct(attributes);

                    // adding admin to the ArrayList
                    products.add(product);

                    // read next line before looping
                    // if EOF reached, line will be null and exits the loop
                    line = br.readLine();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return products;
    }

    /**
     * creates Product object from passed in string array
     *
     * @param metadata details of the product as string array
     * @return project object
     */
    private static Product createProduct(String[] metadata) {
        String description = metadata[0].trim();
        String location = metadata[1].trim();
        double price = Double.parseDouble(metadata[2].trim());
        int quantity = Integer.parseInt(metadata[3].trim());

        // create and return admin of this metadata
        return new Product(description, location, price, quantity);
    }
}
