package chocan.service;

import javax.annotation.Nullable;

/**
 *
 */
public interface IProviderServiceDirectory {

    /**
     *
     * @param id
     * @return
     */
    @Nullable
    Service get(final int id);

}
