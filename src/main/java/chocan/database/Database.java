package chocan.database;

import java.io.IOException;

/**
 * An interface for a database of a list of items.
 * @param <I> A generic item type.
 */
public interface Database<I> extends IDatabase<I> {

    /**
     * Reads the database contents from an external source.
     * @throws IOException If an error occurs while reading from the external source.
     */
    void load() throws IOException;

    /**
     * Writes the database contents to an external source.
     * @throws IOException If an error occurs while writing to the external source.
     */
    void save() throws IOException;

}
