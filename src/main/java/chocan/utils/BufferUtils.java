package chocan.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 */
public class BufferUtils {

    /**
     * Reads a UTF-8 character sequence from the buffer with a maximum length of 2^8-1 bytes.
     * @param buffer The buffer to read from.
     * @return A character sequence from the decoded UTF-8 bytes.
     */
    public static CharSequence readUTF8_1(final ByteBuffer buffer) {
        // Read length of UTF-8 byte string
        final int length = buffer.get() & 0xFF;
        // Calculate buffer variables
        final int oldLimit = buffer.limit();
        final int endPosition = buffer.position() + length;
        // Configure new limit
        buffer.limit(endPosition);
        // Decode UTF-8 bytes to character sequence
        final CharSequence sequence = StandardCharsets.UTF_8.decode(buffer);
        // Reset limit
        buffer.limit(oldLimit);
        // Move position
        buffer.position(endPosition);
        return sequence;
    }

    /**
     * Writes a UTF-8 character sequence to the buffer with a maximum length of 2^8-1 bytes.
     * @param buffer The buffer to write to.
     * @param sequence The character sequence to write to the buffer.
     */
    public static void writeUTF8_1(final ByteBuffer buffer, final CharSequence sequence) {
        final ByteBuffer encodedBytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(sequence));
        final int length = encodedBytes.remaining();
        if (length > 255) {
            throw new IllegalArgumentException("Length of UTF-8 encoded character sequence cannot be greater than 255.");
        }
        // Write number of UTF-8 encoded bytes
        buffer.put((byte) length);
        // Write UTF-8 bytes of character sequence
        buffer.put(encodedBytes);
    }

}
