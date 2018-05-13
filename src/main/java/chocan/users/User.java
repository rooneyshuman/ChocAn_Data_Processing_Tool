package chocan.users;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class User {
	
	/**
	 * A 9-digit identification number.
	 */
	public final int id;

	/**
	 * 
	 */
	public User(final int id) {
	    this.id = id;
		// TODO
	}

	/**
	 * Creates a new User from a buffer.
	 * @param buffer A buffer containing the data for this user.
	 */
	public User(final ByteBuffer buffer) {
	    buffer.order(ByteOrder.BIG_ENDIAN);
        this.id = buffer.getInt();
        // TODO Read data from buffer
	}
	
	
	
}
