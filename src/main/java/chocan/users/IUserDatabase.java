package chocan.users;

import chocan.database.IDatabase;

import javax.annotation.Nullable;

/**
 * A read-only interface for a user database.
 * @param <U> A generic user type.
 */
public interface IUserDatabase<U extends IUser> extends IDatabase<U> {

    /**
     * Gets the user from the database with the given ID.
     * @param id The ID (account number) of the user.
     * @return A user with the given ID.
     */
    @Nullable
    U get(final int id);

    /**
     * Creates a new user compatible with this database.
     */
    U createUser(final int id, final String name, final String address, final String city, final String state, final int zip);

    /**
     * Gets the next user ID available in this database.
     */
    int nextID();

}
