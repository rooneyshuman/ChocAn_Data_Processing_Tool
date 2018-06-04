package chocan.users;

/**
 * A generic user of the software system.
 */
public interface User extends IUser {

    /**
     * Sets the name of the user.
     */
    void setName(final String name);

    /**
     * Sets the street address of the user.
     */
    void setAddress(final String address);

    /**
     * Sets the city of the user's address.
     */
    void setCity(final String city);

    /**
     * Sets the state of the user's address.
     */
    void setState(final String state);

    /**
     * Sets the zip of the user's address.
     */
    void setZip(final int zip);

    /**
     * Sets the mutable data from the given user to this user.
     * @param user The user to copy data from.
     */
    default void set(final IUser user) {
        this.setName(user.getName());
        this.setAddress(user.getAddress());
        this.setState(user.getState());
        this.setCity(user.getCity());
        this.setZip(user.getZip());
    }

    /**
     * Creates a copy of the user object.
     */
    User clone();

}
