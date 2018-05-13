package chocan.users;

import java.nio.ByteBuffer;

/**
 *
 */
public class Member extends User {

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
    public Member(final int id,
                  final boolean active,
                  final String name,
                  final String address, final String city, final String state, final int zip) {
        super(id, active, name, address, city, state, zip);
    }

    /**
     *
     * @param buffer
     */
    public Member(final ByteBuffer buffer) {
        super(buffer);
    }

}
