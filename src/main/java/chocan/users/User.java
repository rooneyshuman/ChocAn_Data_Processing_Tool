package chocan.users;

import chocan.database.SimpleDatabaseItem;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 *
 */
public class User implements SimpleDatabaseItem {

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
     * @param input An input data object to read from.
     */
    public User(final DataInput input) throws IOException {
        this.id = input.readInt();
        this.active = input.readBoolean();
        this.name = input.readUTF();
        this.address = input.readUTF();
        this.city = input.readUTF();
        this.state = input.readUTF();
        this.zip = input.readInt();
    }

    @Override
    public void write(final DataOutput output) throws IOException {
        output.writeInt(this.id);
        output.writeBoolean(this.active);
        output.writeUTF(this.name);
        output.writeUTF(this.address);
        output.writeUTF(this.city);
        output.writeUTF(this.state);
        output.writeInt(this.zip);
    }

}
