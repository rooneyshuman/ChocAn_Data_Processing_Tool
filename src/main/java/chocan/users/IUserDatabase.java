package chocan.users;

import javax.annotation.Nullable;

/**
 *
 */
public interface IUserDatabase<U extends User> {

    /**
     *
     * @param id
     * @return
     */
    @Nullable
    U get(final int id);

}
