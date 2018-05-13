package chocan.service;

import java.math.BigDecimal;

/**
 * A data object for storing service information.
 */
public class Service {

    public final int code;
    public final String name;
    public final BigDecimal fee;

    /**
     * Creates a new service object with the given information.
     * @param code The 9-digit code of the service.
     * @param name The name of the service.
     * @param fee The price a provider charges to provide the service.
     */
    public Service(final int code, final String name, final BigDecimal fee) {
        this.code = code;
        this.name = name;
        this.fee = fee;
    }

    /**
     *
     */
    public void display() {
        // TODO How do we want to format this data?
    }

}
