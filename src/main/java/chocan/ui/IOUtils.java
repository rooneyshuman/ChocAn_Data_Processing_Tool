package chocan.ui;

import java.math.BigDecimal;
import java.text.NumberFormat;
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
	 * Reads and validates a string from the given scanner.
	 * @param scanner The scanner to read lines from.
	 * @param prompt The prompt before accepting input.
	 * @param fieldName The name of the field being read in.
	 * @param maxLength The maximum allowed length of the input string.
	 * @return The read and validated string.
	 */
	public static String promptMax(final Scanner scanner,
	                               final String prompt, final String fieldName,
	                               final int maxLength) {
		System.out.print(prompt);
		String line;
		while ((line = scanner.nextLine()).length() > maxLength) {
			System.out.println("Error: " + fieldName + " is longer than " + maxLength + "!");
			System.out.print(prompt);
		}
		return line;
	}
	
	/**
	 * Reads and validates an unsigned integer from the given scanner.
	 * @param scanner The scanner to read lines from.
	 * @param prompt The prompt before accepting input.
	 * @param fieldName The name of the field being read in.
	 * @param length The exact allowed length of the input string.
	 * @param parseError An error to display if the input could not be parsed as an unsigned integer.
	 * @return The read and validated unsigned integer.
	 */
	public static int promptUnsignedInt(final Scanner scanner,
	                                    final String prompt, final String fieldName,
	                                    final int length, final String parseError) {
		while (true) {
			System.out.print(prompt);
			final String line = scanner.nextLine();
			if (line.length() != length) {
				System.out.println("Error: " + fieldName + " is not " + length + " digits!");
				continue;
			}
			final Integer parsedInteger = ParseUtils.parseUnsignedInt(line);
			if (parsedInteger == null) {
				System.out.println(parseError);
				continue;
			}
			return parsedInteger;
		}
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
	 * Reads and validates a monetary BigDecimal from the given scanner.
	 * @param scanner The scanner to read lines from.
	 * @param prompt The prompt before accepting input.
	 * @param fieldName The name of the field being read in.
	 * @param max The maximum monetary value allowed.
	 * @param parseError An error to display if the input could not be parsed as a monetary BigDecimal.
	 * @return The read and validated BigDecimal.
	 */
	public static BigDecimal promptMoney(final Scanner scanner,
	                                     final String prompt, final String fieldName,
	                                     final BigDecimal max, final String parseError) {
		while (true) {
			System.out.print(prompt);
			final BigDecimal parsedMoney = ParseUtils.parseMoney(scanner.nextLine());
			if (parsedMoney == null) {
				System.out.println(parseError);
				continue;
			}
			if (parsedMoney.compareTo(max) > 0) {
				System.out.println("Error: " + fieldName + " is larger than " + NumberFormat.getCurrencyInstance().format(max) + "!");
				continue;
			}
			return parsedMoney;
		}
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
    public static String readMultiLine(final Scanner scanner, 
                                       final String prompt, final String fieldName, 
                                       final int maxLength) {
    	while (true) {
    		System.out.println(prompt);
		    final StringBuilder sb = new StringBuilder();
		    String line;
		    while (!(line = scanner.nextLine()).isEmpty()) {
			    sb.append(line).append(System.lineSeparator());
		    }
		    final String multiline = sb.toString();
		    if (multiline.length() > maxLength) {
			    System.out.println("Error: " + fieldName + " are longer than " + maxLength + " characters!");
		    	continue;
		    }
		    return multiline;
	    }
    }

}
