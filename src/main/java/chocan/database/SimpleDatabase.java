package chocan.database;

import java.io.*;

/**
 * Represents a simple database of a list of items.
 * @param <I> A generic item type.
 */
public abstract class SimpleDatabase<I extends SimpleDatabaseItem> {

    /**
     * The backing file for this database.
     */
    public final File file;

    /**
     * Creates a new simple database handler.
     * @param file The backing file for the database.
     */
    protected SimpleDatabase(final File file) {
        this.file = file;
    }

    /**
     * Gets the number of items in the database.
     */
    public abstract int size();

    /**
     * Gets a iterable interface over the items of the database.
     */
    protected abstract Iterable<I> getItems();

    /**
     * Overridable factory function for creating a new item from binary data.
     * @param input The input data object to read from to create the item.
     * @return A newly created item.
     * @throws IOException If an error occurred while reading from the input data object.
     */
    protected abstract I createItem(final DataInput input) throws IOException;

    /**
     * Invoked when an item is created during a load operation.
     * @param item The newly created item.
     */
    protected abstract void itemCreated(final I item);

    /**
     * Reads the file into the database.
     * @throws IOException If an error occurs while reading from the database file.
     */
    public void load() throws IOException {
        if (this.file.exists()) {
            try (final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(this.file))) {
                while (dataInputStream.available() > 0) {
                    this.itemCreated(this.createItem(dataInputStream));
                }
            }
        }
    }

    /**
     * Writes the database to the file.
     * @throws IOException If an error occurs while writing to the database file.
     */
    public void save() throws IOException {
        try (final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(this.file))) {
            for (final I item : this.getItems()) {
                item.write(dataOutputStream);
            }
        }
    }

}
