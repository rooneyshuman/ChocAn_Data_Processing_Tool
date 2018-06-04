package chocan.database;

import java.util.stream.Stream;

/**
 * A read-only interface for a database of a list of items.
 * @param <I> A generic item type.
 */
public interface IDatabase<I> {

    /**
     * Gets the number of items in the database.
     */
    int size();

    /**
     * Gets a stream over the items of the database.
     */
    Stream<I> items();

}
