package chocan.cli;

import java.util.List;

@FunctionalInterface
public interface CommandFunction {

    /**
     * Executes the command function with the given arguments and input stream.
     * @param args A list of arguments that were passed to this command.
     * @return Whether to prompt for the next command or not.
     */
    boolean execute(final List<String> args);

}
