package chocan.ui;

/**
 *
 */
public class StringUtils {

    /**
     * Repeats a character n times into a string.
     * @param c The character to repeat.
     * @param n The number of times to repeat the character.
     * @return A string containing the repeated character.
     */
    public static String repeat(final char c, final int n) {
        final char[] chars = new char[n];
        for (int i = 0; i < n; i++) {
            chars[i] = c;
        }
        return new String(chars);
    }

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
     * Converts the string to title case.
     * @param str The string to convert.
     * @return The output string with title casing.
     */
    public static CharSequence toTitleCase(final CharSequence str) {
        final StringBuilder sb = new StringBuilder(str);
        final int length = sb.length();
        boolean nextTitleCase = true;
        for (int i = 0; i < length; i++) {
            final char c = sb.charAt(i);
            if (!Character.isLetter(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                sb.setCharAt(i, Character.toTitleCase(c));
                nextTitleCase = false;
            }
        }
        return sb;
    }

}
