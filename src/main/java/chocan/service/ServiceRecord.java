package chocan.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A data object for storing service record information.
 */
public class ServiceRecord {

    /**
     * The date and time the record was created.
     */
    public final LocalDateTime dateTime;

    /**
     * The date the service occurred.
     */
    public final LocalDate serviceDate;

    /**
     * The account number of the provider that provided the service.
     */
    public final int providerID;

    /**
     * The account number of the member who received the service.
     */
    public final int memberID;

    /**
     * The code of the service that was provided.
     */
    public final int serviceCode;

    /**
     * Any comments that were included by the provider.
     */
    public final String comments;

    /**
     * Creates a new service record with the given information.
     * @param dateTime The date and time the record was created.
     * @param serviceDate The date the service occurred.
     * @param providerID The account number of the provider that provided the service.
     * @param memberID The account number of the member who received the service.
     * @param serviceCode The code of the service that was provided.
     * @param comments Any comments that were included by the provider.
     */
    public ServiceRecord(final LocalDateTime dateTime, final LocalDate serviceDate,
                         final int providerID, final int memberID,
                         final int serviceCode,
                         final String comments) {
        this.dateTime = dateTime;
        this.serviceDate = serviceDate;
        this.providerID = providerID;
        this.memberID = memberID;
        this.serviceCode = serviceCode;
        this.comments = comments;
    }

    /**
     *
     */
    public void display() {
        // TODO How do we want to format this data?
    }

}
