package chocan.users;

import java.io.File;
import java.nio.ByteBuffer;

/**
 *
 */
public class ManagerDatabase extends UserDatabase<Manager> {

    /**
     * Creates a new user database handler.
     * @param file The backing file for the database.
     */
    public ManagerDatabase(final File file) {
        super(file);
    }

    @Override
    protected Manager createUser(final ByteBuffer input) {
        return new Manager(input);
    }

}
