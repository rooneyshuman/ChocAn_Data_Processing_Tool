package chocan.service;

import chocan.database.IDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * A read-only interface for the service record database.
 */
public interface IServiceRecordDatabase<SR extends IServiceRecord> extends IDatabase<SR> {

    /**
     * Gets all the service records that were recorded between the two given date times.
     * The date time range is based on the date time the records were created (and received by the system).
     * @see IServiceRecord#getDateTime
     * @param from The lower bound of the date time range.
     * @param to The upper bound of the date time range.
     * @return A collection of service records.
     */
    Collection<SR> get(final LocalDateTime from, final LocalDateTime to);

    /**
     * Creates a new service record compatible with this database.
     */
    SR createServiceRecord(final LocalDate serviceDate,
                           final int providerID, final int memberID,
                           final int serviceCode,
                           final String comments);

}
