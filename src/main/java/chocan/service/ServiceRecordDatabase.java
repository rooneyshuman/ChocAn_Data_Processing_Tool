package chocan.service;

import chocan.database.Database;

/**
 * An interface for the service record database - an add-only database for service records.
 */
public interface ServiceRecordDatabase<SR extends ServiceRecord> extends IServiceRecordDatabase<SR>, Database<SR> {
    
    /**
     * Adds the service record to the database.
     * @param record The service record to add.
     */
    void add(final SR record);

}
