package chocan.users;

import java.nio.ByteBuffer;

/**
 *
 */
public class Member extends User {

    /**
     *
     * @param id
     */
    public Member(final int id) {
        super(id);
    }

    /**
     *
     * @param buffer
     */
    public Member(final ByteBuffer buffer) {
        super(buffer);
    }

}
