package vendingMachineFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class to manage client's interactions in GUI
 */
public class ClientMenuGUI {
    VendingMachine machine = Main.machine;
    Client client;

    private static Text message = new Text();

    private HBox rowAHBox = new HBox(20);
    private HBox rowBHBox = new HBox(20);
    private HBox rowCHBox = new HBox(20);

    /**
     * Constructs a client menu GUI
     *
     * @param client to interact with machine
     */
    public ClientMenuGUI(Client client) {
        this.client = client;
    }

    /**
     * main method to run the client menu
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
        leftVBox.getChildren().addAll(message, rowAHBox, rowBHBox, rowCHBox);

        // RIGHT

        // vertical box to hold client details as labels
        VBox rightVBox = new VBox(20);
        rightVBox.setAlignment(Pos.CENTER);

        // create and initiate labels, textFields and buttons for RHS
        Label lblName = new Label("UserName:");
        Label name = new Label(client.getName());
        Label lblBalance = new Label("Balance:");
        Label balance = new Label(String.format("\u20AC%.2f", client.checkBalance())); // \u20AC is the unicode for euro sign
        Button btnLogout = new Button("Logout");
        rightVBox.getChildren().addAll(lblName, name, lblBalance, balance, btnLogout);

        // ADD BOTH STACKS TO ROOT LAYOUT

        rootLayout.getChildren().addAll(new StackPane(leftBG, leftVBox), new StackPane(rightBG, rightVBox));
        root.getChildren().addAll(background, rootLayout);

        // Create a scene with root layout, and place it in the stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Client Menu"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        // INIT BUTTONS

        // lambda statement to associate LogOut button with action
        btnLogout.setOnAction(event -> {
            try {
                // save client details back to the file
                client.saveToFile(machine);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new MainMenuGUI().start(primaryStage);
        });

        // lambda expressions to associate each LHS button (product buttons) with action
        btnA1.setOnAction(event -> buyProduct(btnA1, "A1", balance));

        btnA2.setOnAction(event -> buyProduct(btnA2, "A2", balance));

        btnA3.setOnAction(event -> buyProduct(btnA3, "A3", balance));

        btnB1.setOnAction(event -> buyProduct(btnB1, "B1", balance));

        btnB2.setOnAction(event -> buyProduct(btnB2, "B2", balance));

        btnB3.setOnAction(event -> buyProduct(btnB3, "B3", balance));

        btnC1.setOnAction(event -> buyProduct(btnC1, "C1", balance));

        btnC2.setOnAction(event -> buyProduct(btnC2, "C2", balance));

        btnC3.setOnAction(event -> buyProduct(btnC3, "C3", balance));
    }

    /**
     * method to handle product button events
     *
     * @param btn      button to be associated with
     * @param location where the product is bought from
     * @param balance  client's current balance
     */
    private void buyProduct(Button btn, String location, Label balance) {
        if (!machine.isLocationEmpty(location)) {
            Product p = machine.getProductOnLocation(location);
            if (p.getPrice() <= client.checkBalance()) {
                machine.buyProductPerLocation(location, /*p, */client);
                // reduce product qty and refresh on button
                btn.setText(machine.getProductDetailsPerLocationForGUI(location));
                // reduce client balance and display
                balance.setText(String.format("%.2f", client.checkBalance()));
                // display message of successful purchase
                message.setText("Purchased one " + p.getDescription() + " for \u20AC" + String.format("%.2f", p.getPrice()));
            } else {
                message.setText("You don't have enough money to purchase this item.");
            }
        } else {
            message.setText("destination is empty. Try to select a different location.");
        }
    }
}
