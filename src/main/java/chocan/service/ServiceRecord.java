package chocan.service;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
     * Creates a new service record from a input data object.
     * @param input An input data object to read from.
     */
    public ServiceRecord(final DataInput input) throws IOException {
        this.dateTime = LocalDateTime.ofEpochSecond(input.readLong(), 0, ZoneOffset.UTC);
        this.serviceDate = LocalDate.ofEpochDay(input.readLong());
        this.providerID = input.readInt();
        this.memberID = input.readInt();
        this.serviceCode = input.readInt();
        this.comments = input.readUTF();
    }

    /**
     *
     */
    public void display() {
        // TODO How do we want to format this data?
    }

    /**
     * Writes the binary-serialized service record data to the output object.
     */
    public void write(final DataOutput output) throws IOException {
        output.writeLong(this.dateTime.toEpochSecond(ZoneOffset.UTC));
        output.writeLong(this.serviceDate.toEpochDay());
        output.writeInt(this.providerID);
        output.writeInt(this.memberID);
        output.writeInt(this.serviceCode);
        output.writeUTF(this.comments);
    }

}
