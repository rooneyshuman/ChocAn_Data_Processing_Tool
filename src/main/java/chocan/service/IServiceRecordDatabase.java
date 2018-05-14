package chocan.service;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * A read-only interface for the service record database.
 */
public interface IServiceRecordDatabase {

    /**
     * Gets all the service records that were recorded between the two given date times.
     * The date time range is based on the date time the records were created (and received by the system).
     * @see ServiceRecord#dateTime
     * @param from The lower bound of the date time range.
     * @param to The upper bound of the date time range.
     * @return A collection of service records.
     */
    Collection<ServiceRecord> get(final LocalDateTime from, final LocalDateTime to);

}
