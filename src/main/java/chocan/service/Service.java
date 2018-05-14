package chocan.service;

import chocan.utils.BufferUtils;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A data object for storing service information.
 */
public class Service {

    public final int code;

    public String name;
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
     * Creates a new service object from a buffer.
     * @param buffer A buffer containing the data for this service.
     */
    public Service(final ByteBuffer buffer) {
        buffer.order(ByteOrder.BIG_ENDIAN);
        this.code = buffer.getInt();
        this.name = BufferUtils.readUTF8_1(buffer).toString();
        this.fee = new BigDecimal(BufferUtils.readUTF8_1(buffer).toString());
    }

    /**
     *
     */
    public void display() {
        // TODO How do we want to format this data?
    }

    /**
     * Creates a buffer containing the binary-serialized service data.
     * @return The buffer containing the service data.
     */
    public ByteBuffer toBuffer() {
        final ByteBuffer nameBuffer = BufferUtils.writeUTF8_1(this.name);
        final ByteBuffer feeBuffer = BufferUtils.writeUTF8_1(this.fee.toString());
        final ByteBuffer buffer = ByteBuffer.allocate(4 + nameBuffer.remaining() + feeBuffer.remaining());
        buffer.putInt(this.code);
        buffer.put(nameBuffer);
        buffer.put(feeBuffer);
        return buffer;
    }

}
