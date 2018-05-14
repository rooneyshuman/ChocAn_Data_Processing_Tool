package chocan.users;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;

/**
 * A database of members.
 */
public class MemberDatabase extends UserDatabase<Member> {

    /**
     * Creates a new user database handler.
     */
    public MemberDatabase() {
        super(new File("member.db"));
    }

    @Override
    protected Member createUser(final DataInput input) throws IOException {
        return new Member(input);
    }

}
