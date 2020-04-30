/* Had to add these to module-info.java to get javafx work!!!
 * 
 * https://www.youtube.com/watch?v=B-mgPyXEIgo
 * 
 * 	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.web;
 * */

package vendingMachineFX;
/**
 * author@ Laszlo Szlatki
 * author@ Patrick James O'Neill
 * date: 24/04/2020
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The starting point in the GUI application
 */
public class MainMenuGUI extends Application {

    VendingMachine machine = Main.machine;

    /**
     * main method to run the GUI app
     *
     * @param primaryStage to hold all buttons, labels, textFields etc.
     */
    @Override
    public void start(Stage primaryStage) {
        TextField userName = new TextField();
        TextField password = new TextField();
        Label pasLabel = new Label("Enter Password");
        Label userLabel = new Label("Enter Username");

        // initially not visible
        password.setVisible(false);
        pasLabel.setVisible(false);
        userName.setVisible(false);
        userLabel.setVisible(false);

        // user types for drop down menu
        ObservableList<String> userTypes = FXCollections.observableArrayList();
        userTypes.add("Select type");
        userTypes.add("Client Operator");
        userTypes.add("Admin Operator");
        ComboBox<String> comboBox = new ComboBox<>();

        comboBox.setItems(userTypes);
        comboBox.getSelectionModel().select(0);

        // if second or third item selected, show userName and password fields
        comboBox.setOnAction(e -> {
            if (comboBox.getSelectionModel().getSelectedIndex() > 0) {
                password.setVisible(true);
                pasLabel.setVisible(true);
                userName.setVisible(true);
                userLabel.setVisible(true);
            } else {
                password.setVisible(false);
                pasLabel.setVisible(false);
                userName.setVisible(false);
                userLabel.setVisible(false);
            }
        });

        primaryStage.setTitle("Login");

        VBox main = new VBox();
        main.setPadding(new Insets(20, 20, 20, 20));
        main.setSpacing(10);
        main.getChildren().add(new Label("Select type and press start"));
        main.getChildren().add(comboBox);
        Button start = new Button("Start");
        start.setPrefWidth(160);
        start.getStyleClass().add("btn");
        main.getChildren().add(start);

        main.getChildren().add(userLabel);
        main.getChildren().add(userName);
        main.getChildren().add(pasLabel);
        main.getChildren().add(password);

        // lambda statement to associate start button with action
        start.setOnAction(e -> {
            switch (comboBox.getSelectionModel().getSelectedIndex()) {
                case 0: /////////// give error message if none selected
                    displayMsg("Please select operator type");
                    break;
                case 1: /////////// verify client and start client menu
                    Client client = Client.getClientIfValid(userName.getText(), password.getText(), machine.getClients());
                    if (password.getText().length() > 0 && client != null
                    ) {
                        new ClientMenuGUI(client).start(primaryStage);

                    } else {
                        displayMsg("Invalid details entered for Client Operator");
                    }

                    break;
                case 2: ////////// verify admin and start admin menu
                    Admin admin = Admin.getAdminIfValid(userName.getText(), password.getText(), machine.getAdmins());

                    if (password.getText().length() > 0 && admin != null
                    ) {
                        new AdminMenuGUI(admin).start(primaryStage);
                    } else {
                        displayMsg("Invalid details entered for Admin Operator");
                    }

                    break;
            }
        });
        // add scene to stage
        Scene sc = new Scene(main, 200, 250);
        primaryStage.setScene(sc);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Method to display popup error message
     *
     * @param msg to be displayed
     */
    private void displayMsg(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Attempt");
        alert.setContentText(msg);
        alert.show();
    }
}
