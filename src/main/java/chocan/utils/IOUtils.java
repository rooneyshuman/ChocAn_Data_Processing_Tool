package chocan.utils;

import java.util.Scanner;
import java.util.function.Function;

public class IOUtils {
	
	/**
	 * Reads a signed integer from the scanner.
	 * If an integer fails to be parsed, the error string is printed.
	 * @param scanner The scanner to read lines from.
	 * @param onError The function to invoke on parse failure.
	 * @return The parsed signed integer.
	 */
	public static int readInt(final Scanner scanner, final Runnable onError) {
		return _readInt(scanner, onError, ParseUtils::parseInt);
	}
	
	/**
	 * Reads an unsigned integer from the scanner.
	 * If an integer fails to be parsed, the error string is printed.
	 * @param scanner The scanner to read lines from.
	 * @param onError The function to invoke on parse failure.
	 * @return The parsed unsigned integer.
	 */
	public static int readUnsignedInt(final Scanner scanner, final Runnable onError) {
		return _readInt(scanner, onError, ParseUtils::parseUnsignedInt);
	}
	
	private static int _readInt(final Scanner scanner, final Runnable onError, 
	                                  final Function<String, Integer> parseFunction) {
		Integer zipObject;
		while ((zipObject = parseFunction.apply(scanner.nextLine())) == null) {
			onError.run();
		}
		return zipObject;
	}
	
}
