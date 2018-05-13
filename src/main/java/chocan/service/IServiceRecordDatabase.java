package chocan.service;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 *
 */
public interface IServiceRecordDatabase {

    /**
     *
     * @param from
     * @param to
     * @return
     */
    Stream<ServiceRecord> get(final LocalDateTime from, final LocalDateTime to);

}
