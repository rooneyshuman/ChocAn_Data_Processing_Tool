package chocan.users;

import java.nio.ByteBuffer;

/**
 *
 */
public class Provider extends User {

    /**
     * @param id
     */
    public Provider(final int id) {
        super(id);
    }

    /**
     *
     * @param buffer
     */
    public Provider(final ByteBuffer buffer) {
        super(buffer);
    }

}
