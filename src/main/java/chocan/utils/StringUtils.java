package chocan.utils;

/**
 *
 */
public class StringUtils {

    /**
     * Pads the given string on the right with spaces until the string reaches the target size.
     * @param str The original string.
     * @param targetSize The target size of the final string.
     * @return The padded string.
     */
    public static String padRight(final String str, final int targetSize) {
        return String.format("%1$-" + targetSize + "s", str);
    }

    /**
     * Pads the given string on the left with spaces until the string reaches the target size.
     * @param str The original string.
     * @param targetSize The target size of the final string.
     * @return The padded string.
     */
    public static String padLeft(final String str, final int targetSize) {
        return String.format("%1$" + targetSize + "s", str);
    }

}
