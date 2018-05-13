package chocan.users;

import java.io.File;
import java.nio.ByteBuffer;

/**
 *
 */
public class MemberDatabase extends UserDatabase<Member> {

    /**
     * Creates a new user database handler.
     * @param file The backing file for the database.
     */
    public MemberDatabase(final File file) {
        super(file);
    }

    @Override
    protected Member createUser(final ByteBuffer input) {
        return new Member(input);
    }

}
