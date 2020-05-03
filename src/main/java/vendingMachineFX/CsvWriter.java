package vendingMachineFX;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * class to handle writing to csv file
 */
public class CsvWriter {

    // https://www.java67.com/2015/06/how-to-write-to-file-in-java-using-bufferedwriter.html

    /**
     * method to write upddated client details to file
     *
     * @param fileName to be written into
     * @param clients  to be written
     * @throws IOException if problem with writing to file
     */
    public static void writeClientsToCSV(String fileName, List<Client> clients) throws IOException {
        // check if file exists and is a file
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {

            // Writing to a file using BufferedWriter in Java
            FileWriter writer = new FileWriter(fileName);
            BufferedWriter bwr = new BufferedWriter(writer);
            PrintWriter pwr = new PrintWriter(bwr);

            // loop through clients list
            for (Client client : clients) {
                // call toCsvSting on each and write those
                pwr.println(client.toCsvString());
            }
            pwr.close();
            bwr.close();
            System.out.println("Customers.dat is succesfully updated now.");
        }
    }

    /**
     * method to write upddated product details to file
     *
     * @param fileName to be written into
     * @param products to be written
     * @throws IOException if problem with writing to file
     */
    public static void writeProductsToCSV(String fileName, List<Product> products) throws IOException {

        // check if file exists and is a file
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {

            // Writing to a file using BufferedWriter in Java
            FileWriter writer = new FileWriter(fileName);
            BufferedWriter bwr = new BufferedWriter(writer);
            PrintWriter pwr = new PrintWriter(bwr);

            // loop through products list
            for (Product product : products) {
                // if qty is 0, skip line
                if (product.getQuantity() == 0) {
                    System.out.println("qty: 0, Locatin: " + product.getLocation());
                    continue;
                }
                // call toCsvSting on each and write those
                pwr.println(product.toCsvString());
            }
            pwr.close();
            bwr.close();
            System.out.println("product.dat is succesfully updated now.");
        }
    }
}
