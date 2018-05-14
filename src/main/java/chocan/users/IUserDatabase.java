package chocan.users;

import javax.annotation.Nullable;

/**
 * A read-only interface for a user database.
 * @param <U>
 */
public interface IUserDatabase<U extends User> {

    /**
     * Gets the user from the database with the given ID
     * @param id The ID (account number) of the user.
     * @return A user with the given ID.
     */
    @Nullable
    U get(final int id);

}
