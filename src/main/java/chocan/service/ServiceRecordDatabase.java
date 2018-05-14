package chocan.service;

import chocan.database.SimpleDatabase;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * An add-only database for service records.
 */
public class ServiceRecordDatabase extends SimpleDatabase<ServiceRecord> implements IServiceRecordDatabase {

    /**
     * A sorted map of service records.
     */
    private final SortedMap<LocalDateTime, ServiceRecord> serviceRecordMap = new TreeMap<>();

    /**
     * Creates a new service record database.
     * @param file The backing file for the database.
     */
    public ServiceRecordDatabase(final File file) {
        super(file);
    }

    @Override
    public int size() {
        return this.serviceRecordMap.size();
    }

    @Override
    public Collection<ServiceRecord> get(final LocalDateTime from, final LocalDateTime to) {
        return this.serviceRecordMap.subMap(from, to).values();
    }

    @Override
    protected Iterable<ServiceRecord> getItems() {
        return this.serviceRecordMap.values();
    }

    @Override
    protected ServiceRecord createItem(final DataInput input) throws IOException {
        return new ServiceRecord(input);
    }

    @Override
    protected void itemCreated(final ServiceRecord record) {
        this.add(record);
    }

    @Override
    public void load() throws IOException {
        this.serviceRecordMap.clear();
        super.load();
    }

    /**
     * Adds the service record to the database.
     * @param record The service record to add.
     */
    public void add(final ServiceRecord record) {
        this.serviceRecordMap.put(record.dateTime, record);
    }

}
