package chocan.users;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;

/**
 * A database of providers.
 */
public class ProviderDatabase extends UserDatabase<Provider> {

    /**
     * Creates a new user database handler.
     */
    public ProviderDatabase() {
        super(new File("provider.db"));
    }

    @Override
    protected Provider createUser(final DataInput input) throws IOException {
        return new Provider(input);
    }

}
