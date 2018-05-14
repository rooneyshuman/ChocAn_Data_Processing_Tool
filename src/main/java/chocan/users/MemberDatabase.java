package chocan.users;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;

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
    protected Member createUser(final DataInput input) throws IOException {
        return new Member(input);
    }

}
