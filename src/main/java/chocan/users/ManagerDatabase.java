package chocan.users;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;

/**
 * A database of managers.
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
    protected Manager createUser(final DataInput input) throws IOException {
        return new Manager(input);
    }

}
