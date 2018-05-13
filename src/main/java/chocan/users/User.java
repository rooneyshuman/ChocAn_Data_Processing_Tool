package chocan.users;

import chocan.utils.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 */
public class User {
	
	/**
	 * A 9-digit account number of the user.
	 */
	public final int id;

    /**
     * Whether the user has paid their monthly fees.
     */
	public final boolean active;

    /**
     * The name of the user. (25 character max)
     */
	public final String name;

    /**
     * The address of the user. (25 characters max)
     */
	public final String address;

    /**
     * The city of the user's address. (14 characters max)
     */
	public final String city;

    /**
     * The state of the user's address. (2 characters max)
     */
	public final String state;

    /**
     * The 5-digit zip code of the user's address.
     */
	public final int zip;

    /**
     * Creates a new user object with the given information.
     * @param id A 9-digit account number of the user.
     * @param active Whether the user has paid their monthly fees.
     * @param name The name of the user. (25 character max)
     * @param address The address of the user. (25 characters max)
     * @param city The city of the user's address. (14 characters max)
     * @param state The state of the user's address. (2 characters max)
     * @param zip The 5-digit zip code of the user's address.
     */
	public User(final int id,
                final boolean active,
                final String name,
                final String address, final String city, final String state, final int zip) {
	    this.id = id;
	    this.active = active;
	    this.name = name;
	    this.address = address;
	    this.city = city;
	    this.state = state;
	    this.zip = zip;
	}

	/**
	 * Creates a new user from a buffer.
	 * @param buffer A buffer containing the data for this user.
	 */
	public User(final ByteBuffer buffer) {
	    buffer.order(ByteOrder.BIG_ENDIAN);
        this.id = buffer.getInt();
        this.active = buffer.get() > 0;
        this.name = BufferUtils.readUTF8_1(buffer).toString();
        this.address = BufferUtils.readUTF8_1(buffer).toString();
        this.city = BufferUtils.readUTF8_1(buffer).toString();
        this.state = BufferUtils.readUTF8_1(buffer).toString();
        this.zip = buffer.getInt();
	}

    /**
     *
     * @return
     */
	public ByteBuffer toBuffer() {
	    // TODO Write user data to buffer
        return ByteBuffer.allocate(100);
    }

}
