package chocan.utils;

/**
 *
 */
public class StringUtils {

    /**
     *
     * @param s
     * @param n
     * @return
     */
    public static String padRight(final String s, final int n) {
        return String.format("%1$-" + n + "s", s);
    }

    /**
     *
     * @param s
     * @param n
     * @return
     */
    public static String padLeft(final String s, final int n) {
        return String.format("%1$" + n + "s", s);
    }

}
