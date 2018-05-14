package chocan.service;

import chocan.database.SimpleDatabase;

import javax.annotation.Nullable;
import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * A directory "catalog" database for services.
 */
public class ProviderServiceDirectory extends SimpleDatabase<Service> implements IProviderServiceDirectory {

    /**
     * A hash table mapping service codes to service objects.
     */
    private final Map<Integer, Service> serviceMap = new HashMap<>();

    /**
     * A sorted set of services (by name).
     */
    private final SortedSet<Service> sortedServices = new TreeSet<>(Comparator.comparing(s -> s.name));

    /**
     * Creates a new provider service directory.
     * @param file The backing file for the directory.
     */
    public ProviderServiceDirectory(final File file) {
        super(file);
    }

    @Nullable
    @Override
    public Service get(final int code) {
        return this.serviceMap.get(code);
    }

    @Override
    protected Iterable<Service> getItems() {
        return this.sortedServices;
    }

    @Override
    protected Service createItem(final DataInput input) throws IOException {
        return new Service(input);
    }

    @Override
    protected void itemCreated(final Service service) {
        this.add(service);
    }

    @Override
    public void read() throws IOException {
        this.serviceMap.clear();
        this.sortedServices.clear();
        super.read();
    }

    /**
     * Adds the service to the directory.
     * @param service The user to add.
     * @return Whether the user was newly added.
     */
    public boolean add(final Service service) {
        if (!this.serviceMap.containsKey(service.code) && this.sortedServices.add(service)) {
            this.serviceMap.put(service.code, service);
            return true;
        }
        return false;
    }

    /**
     * Removes the service from the directory by code.
     * @param code The code of the service.
     * @return Whether the service was removed.
     */
    public boolean remove(final int code) {
        final Service service = this.serviceMap.get(code);
        if (service != null && this.sortedServices.remove(service)) {
            this.serviceMap.remove(code);
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void display() {
        // TODO How do we want to format this data?
    }

}
