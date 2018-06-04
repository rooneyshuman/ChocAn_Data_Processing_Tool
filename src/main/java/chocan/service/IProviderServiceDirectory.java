package chocan.service;

import chocan.database.IDatabase;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * A read-only interface for the provider service directory.
 */
public interface IProviderServiceDirectory<S extends IService> extends IDatabase<S> {

    /**
     * Looks up a service by its service code.
     * @param code The code of the service to lookup.
     * @return The service assigned to the given service code.
     */
    @Nullable
    S get(final int code);

    /**
     * Looks up a service by its name.
     * @param name The name of the service to lookup.
     * @return The service with the given name.
     */
    @Nullable
    S get(final String name);

    /**
     * Creates a new service compatible with this database.
     */
    S createService(final int code, final String name, final BigDecimal fee);

    /**
     * Gets the next service code available in this database.
     */
    int nextCode();

}
