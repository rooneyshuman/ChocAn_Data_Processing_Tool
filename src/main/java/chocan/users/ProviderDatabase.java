package chocan.users;

import java.io.File;
import java.nio.ByteBuffer;

/**
 *
 */
public class ProviderDatabase extends UserDatabase<Provider> {

    /**
     * Creates a new user database handler.
     * @param file The backing file for the database.
     */
    public ProviderDatabase(final File file) {
        super(file);
    }

    @Override
    protected Provider createUser(final ByteBuffer input) {
        return new Provider(input);
    }

}
