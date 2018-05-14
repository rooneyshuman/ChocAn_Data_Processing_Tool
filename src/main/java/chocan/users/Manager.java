package chocan.users;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 */
public class Manager extends User {

    /**
     *
     * @param id
     * @param active
     * @param name
     * @param address
     * @param city
     * @param state
     * @param zip
     */
    public Manager(final int id,
                   final boolean active,
                   final String name,
                   final String address, final String city, final String state, final int zip) {
        super(id, active, name, address, city, state, zip);
    }

    /**
     *
     * @param input
     */
    public Manager(final DataInput input) throws IOException {
        super(input);
    }

}
