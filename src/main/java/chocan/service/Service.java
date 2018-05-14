package chocan.service;

import chocan.database.SimpleDatabaseItem;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * A data object for storing service information.
 */
public class Service implements SimpleDatabaseItem {

    public final int code;
    public final String name;

    public BigDecimal fee;

    /**
     * Creates a new service object with the given information.
     * @param code The 9-digit code of the service.
     * @param name The name of the service.
     * @param fee The price a provider charges to provide the service.
     */
    public Service(final int code, final String name, final BigDecimal fee) {
        this.code = code;
        this.name = name;
        this.fee = fee;
    }

    /**
     * Creates a new service object from the input data object.
     * @param input An input data object to read from.
     */
    public Service(final DataInput input) throws IOException {
        this.code = input.readInt();
        this.name = input.readUTF();
        this.fee = new BigDecimal(input.readUTF());
    }

    /**
     *
     */
    public void display() {
        // TODO How do we want to format this data?
    }

    /**
     * Writes the binary-serialized service data to the output object.
     */
    @Override
    public void write(final DataOutput output) throws IOException {
        output.writeInt(this.code);
        output.writeUTF(this.name);
        output.writeUTF(this.fee.toString());
    }

}
