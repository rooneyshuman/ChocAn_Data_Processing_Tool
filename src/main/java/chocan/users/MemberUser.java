package chocan.users;

/**
 * An interface for a member (user) of the software system.
 */
public interface MemberUser extends User {

    /**
     * Whether the member has paid their monthly fees.
     */
    boolean isActive();

    /**
     * Sets whether the member has paid their monthly fees.
     * @param active Whether to set the member active or inactive.
     */
    void setActive(final boolean active);

}
