package chocan.users;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;

/**
 * A database of managers.
 */
public class ManagerDatabase extends UserDatabase<Manager> {

    /**
     * Creates a new manager database.
     */
    public ManagerDatabase() {
        super(new File("manager.db"));
    }

    @Override
    protected Manager createUser(final DataInput input) throws IOException {
        return new Manager(input);
    }

}
