package chocan.database;

import java.io.DataOutput;
import java.io.IOException;

@FunctionalInterface
public interface SimpleDatabaseItem {

    /**
     * Writes the binary-serialized item data to the output object.
     * @param output The output data object.
     * @throws IOException If an error occurred while writing to the output object.
     */
    void write(final DataOutput output) throws IOException;

}
