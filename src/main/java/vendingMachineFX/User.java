package vendingMachineFX;

import java.io.IOException;

/**
 * An abstract user class, parent of Client and Admin classes
 */
public abstract class User {

    private String name;
    private String password;

    /**
     * Constructor for User object
     */
    public User() {
        name = "";
        password = "";
    }

    /**
     * Constructor forUser object
     *
     * @param name     user's name
     * @param password user's password
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Gets the userName.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns program flow to VendingMachineManuCLI
     *
     * @param machine the vending machine to return to
     * @throws IOException
     */
    public void logout(VendingMachine machine) throws IOException {

        MainMenuCLI menu = new MainMenuCLI(machine);
        menu.run(machine);
    }

    /**
     * Displays user details in the desired form
     */
    @Override
    public String toString() {
        return "userName: " + name;
    }

    /**
     * Determines of this user is the same as the other user.
     *
     * @param other the other user
     * @return true if the users are equal, false otherwise
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
        if (!(other instanceof User)) {
            return false;
        }
        // if name and password are the same, return true
        User b = (User) other;
        return name.equals(b.name) && password.equals(b.password);
    }
}
