package chocan.ui.cli;

import java.util.List;
import java.util.Scanner;

@FunctionalInterface
public interface CommandFunction {

    /**
     * Executes the command function with the given arguments and input stream.
     * @param args A list of arguments that were passed to this command.
     * @param stdin A scanner reading from the standard input stream.
     * @return Whether to prompt for the next command or not.
     */
    boolean execute(final List<String> args, final Scanner stdin);

}
