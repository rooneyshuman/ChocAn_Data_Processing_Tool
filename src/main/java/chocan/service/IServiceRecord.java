package chocan.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A read-only interface for a data object that stores service record information.
 */
public interface IServiceRecord {

    /**
     * Gets the date and time the record was created.
     */
    LocalDateTime getDateTime();

    /**
     * Gets the date the service was provided.
     */
    LocalDate getServiceDate();

    /**
     * Gets the account number of the provider that provided the service.
     */
    int getProviderID();

    /**
     * Gets the account number of the member who received the service.
     */
    int getMemberID();

    /**
     * Gets the code of the service that was provided.
     */
    int getServiceCode();

}
