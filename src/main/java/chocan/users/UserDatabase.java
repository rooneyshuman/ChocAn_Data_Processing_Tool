package chocan.users;

import javax.annotation.Nullable;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @param <U>
 */
public abstract class UserDatabase<U extends User> implements IUserDatabase<U> {
	
	/**
	 * The backing file for this database.
	 */
	private final File file;
	
	/**
	 * Creates a new user database handler.
	 * @param file The backing file for the database.
	 */
	public UserDatabase(final File file) {
		this.file = file;
	}

	/**
	 * Overridable factory function for creating a new user from binary data.
	 * @param input The input data object to read from to create the user.
	 * @return A newly created user.
	 * @throws IOException If an error occurred while reading from the input data object.
	 */
	protected abstract U createUser(final DataInput input) throws IOException;
	
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
	
	/**
	 * Reads the file into the database.
	 * Calling this function doesn't necessarily read the entire file.
	 * It is only required to indicate that the existing data in the file should be considered.
	 * @throws IOException
	 */
	public void read() throws IOException {
		// TODO
	}
	
	/**
	 * Writes the database to the file.
	 * @throws IOException
	 */
	public void write() throws IOException {
		// TODO
	}
	
}
