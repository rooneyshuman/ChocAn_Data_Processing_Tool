package chocan.service;

import javax.annotation.Nullable;

/**
 * A read-only interface for the provider service directory.
 */
public interface IProviderServiceDirectory {

    /**
     * Looks up a service by its service code.
     * @param code The code of the service to lookup.
     * @return The service assigned to the given service code.
     */
    @Nullable
    Service get(final int code);

}
