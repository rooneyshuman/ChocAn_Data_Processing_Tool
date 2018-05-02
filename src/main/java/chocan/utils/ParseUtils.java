package chocan.utils;

import javax.annotation.Nullable;

public class ParseUtils {

    /**
     * Parses an integer from the given string.
     * @param str The string to parse.
     * @return An integer, or <code>null</code> if parse failed.
     */
    @Nullable
    public static Integer parseInt(final String str) {
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

    /**
     * Parses an unsigned integer from the given string.
     * @param str The string to parse.
     * @return An unsigned integer, or <code>null</code> if parse failed.
     */
    @Nullable
    public static Integer parseUnsignedInt(final String str) {
        try {
            return Integer.parseUnsignedInt(str);
        } catch (final NumberFormatException nfe) {
            return null;
        }
    }

}
