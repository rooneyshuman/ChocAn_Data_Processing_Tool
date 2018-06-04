package chocan.service;

import java.math.BigDecimal;

/**
 * An interface for a data object that stores service information.
 */
public interface Service extends IService {

    /**
     * Sets the fee of the service.
     */
    void setFee(final BigDecimal fee);

    /**
     * Sets the mutable data from the given service to this service.
     * @param service The service to copy data from.
     */
    default void set(final IService service) {
        this.setFee(service.getFee());
    }

    /**
     * Creates a copy of the service object.
     */
    Service clone();

}
