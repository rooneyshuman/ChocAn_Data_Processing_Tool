package chocan.ui;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 
 */
public class ParseUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-uuuu");
    private static final DecimalFormat BIG_DECIMAL_FORMAT;

    static {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        final DecimalFormat decimalFormat = new DecimalFormat("#,##0.0#", symbols);
        decimalFormat.setParseBigDecimal(true);
        BIG_DECIMAL_FORMAT = decimalFormat;
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
    
    /**
     * Parses a monetary BigDecimal from the given string.
     * @param str The string to parse.
     * @return An unsigned BigDecimal, or <code>null</code> if parse failed.
     */
    @Nullable
    public static BigDecimal parseMoney(final String str) {
        try {
            return (BigDecimal) BIG_DECIMAL_FORMAT.parse(str.startsWith("$") ? str.substring(1) : str);
        } catch (final ParseException e) {
            return null;
        }
    }

    /***
     * Parses a local date from the given string in the format (MM-DD-YYYY).
     * @param str The string to parse.
     * @return A local date, or <code>null</code> if parse failed.
     */
    @Nullable
    public static LocalDate parseDate(final String str) {
        try {
            return LocalDate.parse(str, DATE_FORMATTER);
        } catch (final DateTimeParseException e) {
            return null;
        }
    }

}
