package chocan.users;

import chocan.database.SimpleDatabase;

import javax.annotation.Nullable;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A database of users.
 * @param <U>
 */
public abstract class UserDatabase<U extends User> extends SimpleDatabase<U> implements IUserDatabase<U> {

    /**
     * A hash table mapping user IDs to user objects.
     */
    private final Map<Integer, U> users = new HashMap<>();

    /**
     * Creates a new user database.
     * @param file The backing file for the database.
     */
    protected UserDatabase(final File file) {
        super(file);
    }

    @Nullable
    @Override
    public U get(int id) {
        return this.users.get(id);
    }

    @Override
    protected Iterable<U> getItems() {
        return this.users.values();
    }

    /**
     * Overridable factory function for creating a new user from binary data.
     * @param input The input data object to read from to create the user.
     * @return A newly created user.
     * @throws IOException If an error occurred while reading from the input data object.
     */
    protected abstract U createUser(final DataInput input) throws IOException;

    @Override
    protected U createItem(final DataInput input) throws IOException {
        return this.createUser(input);
    }

    @Override
    protected void itemCreated(final U user) {
        this.add(user);
    }

    @Override
    public void read() throws IOException {
        this.users.clear();
        super.read();
    }

    /**
     * Determines whether the database contains the user by their ID.
     * @param id The ID (account number) of the user.
     * @return Whether the database contains the user.
     */
    public boolean contains(final int id) {
        return this.users.containsKey(id);
    }

    /**
     * Adds the user to the database.
     * @param user The user to add.
     * @return Whether the user was newly added.
     */
    public boolean add(final U user) {
        return this.users.put(user.id, user) == null;
    }

    /**
     * Removes the user from the database by ID.
     * @param id The ID (account number) of the user.
     * @return Whether the user was removed.
     */
    public boolean remove(final int id) {
        return this.users.remove(id) != null;
    }

}
