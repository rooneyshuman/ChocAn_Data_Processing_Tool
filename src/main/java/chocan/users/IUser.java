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
     * The exact number of characters of the ID field.
     */
    int ID_LENGTH = 9;
    
    /**
     * Gets the 9-digit account number of the user.
     */
    int getID();
    
    /**
     * The maximum number of characters of the name field.
     */
    int MAX_NAME_LENGTH = 25;
    
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
     * The maximum number of characters of the address field.
     */
    int MAX_ADDRESS_LENGTH = 25;
    
    /**
     * Gets the address of the user. (25 characters max)
     */
    String getAddress();
    
    /**
     * The maximum number of characters of the city field.
     */
    int MAX_CITY_LENGTH = 14;
    
    /**
     * Gets the city of the user's address. (14 characters max)
     */
    String getCity();
    
    /**
     * The maximum number of characters of the state field.
     */
    int MAX_STATE_LENGTH = 2;
    
    /**
     * Gets the state of the user's address. (2 characters max)
     */
    String getState();
    
    /**
     * The minimum (5-digit) user zip allowed.
     */
    int MIN_ZIP = 10000;
    
    /**
     * The maximum (5-digit) user zip allowed.
     */
    int MAX_ZIP = 99999;
    
    /**
     * The exact number of characters of the zip field.
     */
    int ZIP_LENGTH = 5;
    
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
        return StringUtils.padRight("ID", ID_LENGTH) +
            " | " + StringUtils.padRight("Name", MAX_NAME_LENGTH) +
            " | " + StringUtils.padRight("Street address", MAX_ADDRESS_LENGTH) +
            " | " + StringUtils.padRight("City", MAX_CITY_LENGTH) +
            " | " + StringUtils.padRight("State", Math.max(MAX_STATE_LENGTH, 5)) +
            " | " + StringUtils.padRight("Zip", ZIP_LENGTH);
    }

    /**
     * Gets a row string for this user in a table of users.
     */
    default String displayRow() {
        return StringUtils.padRight(String.valueOf(this.getID()), ID_LENGTH) +
            " | " + StringUtils.padRight(this.getName(), MAX_NAME_LENGTH) +
            " | " + StringUtils.padRight(this.getAddress(), MAX_ADDRESS_LENGTH) +
            " | " + StringUtils.padRight(this.getCity(), MAX_CITY_LENGTH) +
            " | " + StringUtils.padRight(this.getState(), Math.max(MAX_STATE_LENGTH, 5)) +
            " | " + StringUtils.padRight(String.valueOf(this.getZip()), ZIP_LENGTH);
    }

    /**
     * Creates a copy of the user object.
     */
    IUser clone();

}
