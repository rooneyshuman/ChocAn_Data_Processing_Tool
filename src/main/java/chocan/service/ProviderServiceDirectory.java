package chocan.service;

import chocan.database.Database;
import chocan.ui.StringUtils;

import java.io.PrintStream;
import java.util.stream.Stream;

/**
 * An interface for the provider service directory.
 */
public interface ProviderServiceDirectory<S extends Service> extends IProviderServiceDirectory<S>, Database<S> {

    /**
     * Adds the service to the directory.
     * @param service The service to add.
     * @return Whether the service was newly added.
     */
    boolean add(final S service);

    /**
     * Removes the service from the directory by code.
     * @param code The code of the service.
     * @return Whether the service was found and removed.
     */
    boolean remove(final int code);

    /**
     * Prints the directory in a human-readable format to the given print stream.
     * @param out The stream to print to.
     */
    default void print(final PrintStream out) {
        this.print(this.items(), out);
    }

    /**
     * Prints the directory in a human-readable format to the given print stream.
     * @param services The input stream of services to print.
     * @param out The output stream to print to.
     */
    default void print(final Stream<S> services, final PrintStream out) {
        final boolean[] header = new boolean[1];
        services.forEach((final S service) -> {
            if (!header[0]) {
                final String displayHeader = service.displayHeader();
                final String horizontalLine = StringUtils.repeat('-', displayHeader.length());
                out.println(horizontalLine);
                out.println(displayHeader);
                out.println(horizontalLine);
                header[0] = true;
            }
            out.println(service.displayRow());
        });
    }

}
