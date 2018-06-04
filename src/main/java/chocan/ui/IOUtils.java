package chocan.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Utility class for reading from input streams and scanners, and writing to output streams.
 */
public class IOUtils {

    /**
     * Prompts for confirmation to the given question.
     * @param scanner The scanner to read user input.
     * @param question The question to ask.
     * @return Whether the question was confirmed.
     */
	public static boolean confirm(final Scanner scanner, final String question, final boolean defaultOption) {
		System.out.print(question);
		if (defaultOption) {
            System.out.print(" [Y/n]: ");
        } else {
            System.out.print(" [y/N]: ");
        }
		if (scanner.hasNextLine()) {
            final String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                return defaultOption;
            }
            return line.toLowerCase().startsWith("y");
        }
        return false;
	}
	
	/**
	 * Reads an unsigned integer from the scanner.
	 * If an integer fails to be parsed, the error handler is invoked.
	 * @param scanner The scanner to read lines from.
	 * @param onError The function to invoke on parse failure.
	 * @return The parsed unsigned integer.
	 */
	public static int readUnsignedInt(final Scanner scanner, final Runnable onError) {
		return _read(scanner, onError, ParseUtils::parseUnsignedInt);
	}
	
	/**
	 * Reads a monetary BigDecimal from the scanner.
	 * If a monetary value fails to be parsed, the error handler is invoked.
	 * @param scanner The scanner to read lines from.
	 * @param onError The function to invoke on parse failure.
	 * @return The parsed BigDecimal value.
	 */
	public static BigDecimal readMoney(final Scanner scanner, final Runnable onError) {
		return _read(scanner, onError, ParseUtils::parseMoney);
	}

	/**
     * Reads a local date from the scanner in the format (MM-DD-YYYY).
     * If a local date fails to be parsed, the error handler is invoked.
	 * @param scanner The scanner to read lines from.
	 * @param onError The function to invoke on parse failure.
	 * @return The parsed local date.
	 */
	public static LocalDate readDate(final Scanner scanner, final Runnable onError) {
        return _read(scanner, onError, ParseUtils::parseDate);
    }

    private static <T> T _read(final Scanner scanner, final Runnable onError,
                               final Function<String, T> parseFunction) {
        T value;
        while ((value = parseFunction.apply(scanner.nextLine())) == null) {
            onError.run();
        }
        return value;
    }

    /**
     * Reads a multi-line string from the given scanner.
     * The multi-line string ends when an empty line is read.
     * @param scanner The scanner to read lines from.
     * @return A multi-line string.
     */
    public static String readMultiLine(final Scanner scanner) {
        final StringBuilder sb = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }

}
