package vendingMachineFX;
/**
 * author@ Laszlo Szlatki
 * author@ Patrick James O'Neill
 * date: 24/04/2020
 */
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class to manage admin interactions in GUI
 */
public class AdminMenuGUI {
    VendingMachine machine = Main.machine;
    Admin admin;

    // to activate and de-activate some of the buttons
    private static SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

    private static Text message = new Text();

    private HBox rowAHBox = new HBox(20);
    private HBox rowBHBox = new HBox(20);
    private HBox rowCHBox = new HBox(20);

    // some buttons and fields need to be declared as class variable,
    // to provide access in methods not just the run()
    HBox addProdHBox;
    Button btnTopup;
    TextField topupQuantity;
    Button btnAddProduct;
    TextField description;
    TextField price;
    TextField quantity;

    /**
     * Constructs an admin menu GUI
     *
     * @param admin to interact with machine
     */
    public AdminMenuGUI(Admin admin) {
        this.admin = admin;
    }

    /**
     * main method to run the admin menu
     *
     * @param primaryStage to hold all buttons, labels, textFields etc.
     */
    public void start(Stage primaryStage) {

        // create UI
        // create initial pane
        Pane root = new Pane();
        root.setPrefSize(615, 350);

        // create a back background
        Region background = new Region();
        background.setPrefSize(615, 350);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        // create the green and yellow rectangles
        HBox rootLayout = new HBox(5);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle leftBG = new Rectangle(380, 340);
        leftBG.setArcWidth(50);
        leftBG.setArcHeight(50);
        leftBG.setFill(Color.GREEN);
        Rectangle rightBG = new Rectangle(220, 340);
        rightBG.setArcWidth(50);
        rightBG.setArcHeight(50);
        rightBG.setFill(Color.ORANGE);

        // LEFT
        // create buttons for each location and set product details as labels
        Button btnA1 = new Button(machine.getProductDetailsPerLocationForGUI("A1"));
        Button btnA2 = new Button(machine.getProductDetailsPerLocationForGUI("A2"));
        Button btnA3 = new Button(machine.getProductDetailsPerLocationForGUI("A3"));
        Button btnB1 = new Button(machine.getProductDetailsPerLocationForGUI("B1"));
        Button btnB2 = new Button(machine.getProductDetailsPerLocationForGUI("B2"));
        Button btnB3 = new Button(machine.getProductDetailsPerLocationForGUI("B3"));
        Button btnC1 = new Button(machine.getProductDetailsPerLocationForGUI("C1"));
        Button btnC2 = new Button(machine.getProductDetailsPerLocationForGUI("C2"));
        Button btnC3 = new Button(machine.getProductDetailsPerLocationForGUI("C3"));

        // set all buttons the same size (100 X 75)
        rowAHBox.setPrefWidth(100);
        rowAHBox.setPrefHeight(75);
        rowBHBox.setPrefWidth(100);
        rowBHBox.setPrefHeight(75);
        rowCHBox.setPrefWidth(100);
        rowCHBox.setPrefHeight(75);
        btnA1.setMinWidth(rowAHBox.getPrefWidth());
        btnA1.setMinHeight(rowAHBox.getPrefHeight());
        btnA2.setMinWidth(rowAHBox.getPrefWidth());
        btnA2.setMinHeight(rowAHBox.getPrefHeight());
        btnA3.setMinWidth(rowAHBox.getPrefWidth());
        btnA3.setMinHeight(rowAHBox.getPrefHeight());
        btnB1.setMinWidth(rowBHBox.getPrefWidth());
        btnB1.setMinHeight(rowBHBox.getPrefHeight());
        btnB2.setMinWidth(rowBHBox.getPrefWidth());
        btnB2.setMinHeight(rowBHBox.getPrefHeight());
        btnB3.setMinWidth(rowBHBox.getPrefWidth());
        btnB3.setMinHeight(rowBHBox.getPrefHeight());
        btnC1.setMinWidth(rowCHBox.getPrefWidth());
        btnC1.setMinHeight(rowCHBox.getPrefHeight());
        btnC2.setMinWidth(rowCHBox.getPrefWidth());
        btnC2.setMinHeight(rowCHBox.getPrefHeight());
        btnC3.setMinWidth(rowCHBox.getPrefWidth());
        btnC3.setMinHeight(rowCHBox.getPrefHeight());

        // add product buttons to rows
        rowAHBox.getChildren().addAll(btnA1, btnA2, btnA3);
        rowBHBox.getChildren().addAll(btnB1, btnB2, btnB3);
        rowCHBox.getChildren().addAll(btnC1, btnC2, btnC3);

        // vertical box to hold 3 row of product buttons and message
        VBox leftVBox = new VBox(20);
        leftVBox.setAlignment(Pos.TOP_CENTER);

        leftVBox.getChildren().addAll(rowAHBox, rowBHBox, rowCHBox, message);

        // RIGHT

        // vertical box to hold client details as labels
        VBox rightVBox = new VBox(20);
        rightVBox.setAlignment(Pos.CENTER);

        // create and initiate labels, textFields and buttons for RHS
        Label name = new Label("UserName: " + admin.getName());
        btnAddProduct = new Button("Add new product");
        description = new TextField("Description");
        description.setPrefWidth(80);
        price = new TextField("0.0");
        price.setPrefWidth(70);
        quantity = new TextField("0");
        quantity.setPrefWidth(70);
        addProdHBox = new HBox(description, price, quantity);
        btnTopup = new Button("TopUp quantity");
        topupQuantity = new TextField("0");
        topupQuantity.setMaxWidth(100);
        Button btnLogout = new Button("Logout");
        Button btnShutDown = new Button("ShutDown");
        HBox quitHBox = new HBox(btnLogout, btnShutDown);
        // construct RHS VBox with labels, textFields and buttons
        rightVBox.getChildren().addAll(name,
                btnAddProduct, addProdHBox,
                btnTopup, topupQuantity, quitHBox);

        // hide these details initially until relevant button pressed
        addProdHBox.setVisible(false);
        topupQuantity.setVisible(false);

        // ADD BOTH STACKS TO ROOT LAYOUT
        rootLayout.getChildren().addAll(new StackPane(leftBG, leftVBox), new StackPane(rightBG, rightVBox));
        root.getChildren().addAll(background, rootLayout);

        // Create a scene with root layout, and place it in the stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin Menu"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        // INIT BUTTONS
        // lambda expression to associate LogOut button with action
        btnLogout.setOnAction(event -> new MainMenuGUI().start(primaryStage));

        // lambda statement to associate ShutDown button with action
        btnShutDown.setOnAction(event -> {
            try {
                // save current stock details to file
                admin.exportProductsToCsv(machine.getProducts());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });

        // lambda statement to associate add product button with action
        btnAddProduct.setOnAction(event -> {
            // check if there is any empty location
            if (machine.anyEmptyLocation()) {
                addProdHBox.setVisible(true);
                btnTopup.disableProperty().bind(playable.not());
            } else {
                message.setText("Cannot add new product, no empty location. Try to topUp");
            }
        });

        // lambda statement to associate TopUp button with action
        btnTopup.setOnAction(event -> {
            // check if there is any location with product on
            if (!machine.allEmptyLocation()) {
                topupQuantity.setVisible(true);
                btnAddProduct.disableProperty().bind(playable.not());
            } else {
                message.setText("Cannot topup product, all locations are empty.");
            }
        });

        // lambda expressions to associate each LHS button (product buttons) with action
        btnA1.setOnAction(event -> productBtnAction(btnA1, "A1", description, price, quantity, topupQuantity));

        btnA2.setOnAction(event -> productBtnAction(btnA2, "A2", description, price, quantity, topupQuantity));

        btnA3.setOnAction(event -> productBtnAction(btnA3, "A3", description, price, quantity, topupQuantity));

        btnB1.setOnAction(event -> productBtnAction(btnB1, "B1", description, price, quantity, topupQuantity));

        btnB2.setOnAction(event -> productBtnAction(btnB2, "B2", description, price, quantity, topupQuantity));

        btnB3.setOnAction(event -> productBtnAction(btnB3, "B3", description, price, quantity, topupQuantity));

        btnC1.setOnAction(event -> productBtnAction(btnC1, "C1", description, price, quantity, topupQuantity));

        btnC2.setOnAction(event -> productBtnAction(btnC2, "C2", description, price, quantity, topupQuantity));

        btnC3.setOnAction(event -> productBtnAction(btnC3, "C3", description, price, quantity, topupQuantity));
    }

    /**
     * action to be taken ob pressing button
     * - checks if new product needs to be added
     * - or if existing product needs to be topped up
     * - or clear a location
     * then resets all values and visibility on RHS VBox to default
     *
     * @param btn button to be associated with
     * @param location where action to be carried out
     * @param description textfield of product description for adding product
     * @param price textfield of product price for adding product
     * @param quantity textfield of quantity for adding product
     * @param topupQuantity textfield of quantity for topUp existing product
     */
    private void productBtnAction(Button btn, String location, TextField description, TextField price, TextField quantity, TextField topupQuantity) {

        // if add qty > 0 add new product with qty
        if (isInt(quantity) && isDouble(price) && Integer.parseInt(quantity.getText()) != 0) {
            Product p = new Product(description.getText(), location, Double.parseDouble(price.getText()), Integer.parseInt(quantity.getText()));
            addProduct(btn, location, p, Integer.parseInt(quantity.getText()));
            VendingMachine.products.add(p);
            // else if topUp qty > 0 add qty to product.qty
        } else if (isInt(topupQuantity) && Integer.parseInt(topupQuantity.getText()) != 0) {
            topupProduct(btn, location, Integer.parseInt(topupQuantity.getText()));
            // else reduce product qty by one
        } else {
            message.setText("Clear location");
            clearProduct(btn, location);
        }
        // reset visibility and default values for items on Right VBox
        this.addProdHBox.setVisible(false);
        this.topupQuantity.setVisible(false);
        btnTopup.disableProperty().bind(playable);
        btnAddProduct.disableProperty().bind(playable);
        topupQuantity.setText("0");
        description.setText("Description");
        price.setText("0.0");
        quantity.setText("0");
    }

    /**
     * method to handle product topUp
     * @param btn button to be associated with
     * @param location where product to be added
     * @param qty to be added
     */
    private void topupProduct(Button btn, String location, int qty) {
        if (!machine.isLocationEmpty(location)) {
            Product p = machine.getProductOnLocation(location);
            machine.addProductPerLocation(location, p, qty);
            // increase product qty and refresh on button
            btn.setText(machine.getProductDetailsPerLocationForGUI(location));
            // display message of successful topUp
            message.setText("TopUp successful.");
        } else {
            message.setText("Destination is empty. Try to select a different location.");
        }
    }

    /**
     * method to handle adding new product
     * @param btn button to be associated with
     * @param location where product to be added
     * @param p product to be added
     * @param qty amount to be added
     */
    private void addProduct(Button btn, String location, Product p, int qty) {
        if (machine.isLocationEmpty(location)) {
            machine.addProductPerLocation(location, p, qty);
            // update product on button text
            btn.setText(machine.getProductDetailsPerLocationForGUI(location));
            // display message of successful purchase
            message.setText("New product added successfully.");
        } else {
            message.setText("Destination is not empty, select a different location.");
        }
    }

    /**
     * method to clear product from the location
     * @param btn button to be associated with
     * @param location where product to be cleared
     */
    private void clearProduct(Button btn, String location) {
        if (!machine.isLocationEmpty(location)) {
            machine.clearProductFromLocation(location);
            // reduce product qty and refresh on button
            btn.setText(machine.getProductDetailsPerLocationForGUI(location));
            // display message of successful purchase
            message.setText("Location is now empty");
        } else {
            message.setText("destination is empty. Try to select a different location.");
        }
    }

    /**
     * validate textfield entry if int
     * @param entry to be validated
     * @return true if valid, false otherwise
     */
    private boolean isInt(TextField entry) {
        try {
            Integer.parseInt(entry.getText());
            return true;
        } catch (NumberFormatException nfe) {
            message.setText("Invalid entry for quantity");
        }
        return false;
    }

    /**
     * validate textfield entry if double
     * @param entry to be validated
     * @return true if valid, false otherwise
     */
    private boolean isDouble(TextField entry) {
        try {
            Double.parseDouble(entry.getText());
            return true;
        } catch (NumberFormatException nfe) {
            message.setText("Invalid entry for quantity");
        }
        return false;
    }
}
