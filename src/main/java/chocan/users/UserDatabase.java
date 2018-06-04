package chocan.users;

import chocan.database.Database;

/**
 * An interface for a user database.
 * @param <U> A generic user type.
 */
public interface UserDatabase<U extends User> extends IUserDatabase<U>, Database<U> {

    /**
     * Adds the user to the database.
     * @param user The user to add.
     * @return Whether the user was newly added.
     */
    boolean add(final U user);

    /**
     * Removes the user from the database by ID.
     * @param id The ID (account number) of the user.
     * @return Whether the user was removed.
     */
    boolean remove(final int id);

}
