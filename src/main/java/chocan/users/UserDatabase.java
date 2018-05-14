package chocan.users;

import chocan.database.SimpleDatabase;

import javax.annotation.Nullable;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @param <U>
 */
public abstract class UserDatabase<U extends User> extends SimpleDatabase<U> implements IUserDatabase<U> {

    private final Map<Integer, U> users = new HashMap<>();

	/**
	 * Creates a new user database handler.
	 * @param file The backing file for the database.
	 */
	protected UserDatabase(final File file) {
	    super(file);
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
        this.users.put(user.id, user);
    }

    /**
	 * 
	 * @param id
	 * @return
	 */
	public boolean contains(final int id) {
		// TODO
		return false;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Nullable
	public U get(final int id) {
		// TODO
		return null;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean add(final U user) {
		// TODO
		return false;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean remove(final int id) {
        // TODO
        return false;
    }



}
