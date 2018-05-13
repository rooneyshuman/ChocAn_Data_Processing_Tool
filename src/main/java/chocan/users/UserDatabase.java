package chocan.users;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

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
	 * Overridable factory function for creating a new user from raw data.
	 * @param input The data to parse and create the user from.
	 * @return A newly created user.
	 */
	protected abstract U createUser(final ByteBuffer input);
	
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
	 * @param user
	 * @return
	 */
	public boolean contains(final U user) {
		return this.contains(user.id);
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
	 *
	 * @param user
	 * @return
	 */
	public boolean remove(final U user) {
		return this.remove(user.id);
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
