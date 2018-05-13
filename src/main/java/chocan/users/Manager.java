package chocan.users;

import java.nio.ByteBuffer;

/**
 *
 */
public class Manager extends User {

    /**
     *
     * @param id
     */
    public Manager(final int id) {
        super(id);
    }

    /**
     *
     * @param buffer
     */
    public Manager(final ByteBuffer buffer) {
        super(buffer);
    }

}
