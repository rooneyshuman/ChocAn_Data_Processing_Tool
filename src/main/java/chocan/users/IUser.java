package chocan.users;

import chocan.ui.StringUtils;

/**
 * A read-only interface for a generic user of the software system.
 */
public interface IUser extends Cloneable {

    /**
     * The minimum (9-digit) user ID allowed.
     */
    int MIN_ID = 100000000;

    /**
     * The maximum (9-digit) user ID allowed.
     */
    int MAX_ID = 999999999;

    /**
     * Gets the 9-digit account number of the user.
     */
    int getID();

    /**
     * Gets the name of the user. (25 character max)
     */
    String getName();

    /**
     * Gets the first name of the user (assuming the full name starts with their first name).
     */
    default String getFirstName() {
        return this.getName().trim().split(" ")[0];
    }

    /**
     * Gets the address of the user. (25 characters max)
     */
    String getAddress();

    /**
     * Gets the city of the user's address. (14 characters max)
     */
    String getCity();

    /**
     * Gets the state of the user's address. (2 characters max)
     */
    String getState();

    /**
     * Gets the 5-digit zip code of the user's address.
     */
    int getZip();

    /**
     * Displays the user information to the standard output.
     */
    default void display() {
        System.out.println("ID: " + this.getID());
        System.out.println("Name: " + this.getName());
        System.out.println("Street address: " + this.getAddress());
        System.out.println("City: " + this.getCity());
        System.out.println("State: " + this.getState());
        System.out.println("Zip: " + this.getZip());
    }

    /**
     * Gets the header string for a table of users.
     */
    default String displayHeader() {
        return StringUtils.padRight("ID", 9) +
            " | " + StringUtils.padRight("Name", 25) +
            " | " + StringUtils.padRight("Street address", 25) +
            " | " + StringUtils.padRight("City", 14) +
            " | " + StringUtils.padRight("State", 5) +
            " | " + StringUtils.padRight("Zip", 5);
    }

    /**
     * Gets a row string for this user in a table of users.
     */
    default String displayRow() {
        return StringUtils.padRight(String.valueOf(this.getID()), 9) +
            " | " + StringUtils.padRight(this.getName(), 25) +
            " | " + StringUtils.padRight(this.getAddress(), 25) +
            " | " + StringUtils.padRight(this.getCity(), 14) +
            " | " + StringUtils.padRight(this.getState(), 5) +
            " | " + StringUtils.padRight(String.valueOf(this.getZip()), 5);
    }

    /**
     * Creates a copy of the user object.
     */
    IUser clone();

}
