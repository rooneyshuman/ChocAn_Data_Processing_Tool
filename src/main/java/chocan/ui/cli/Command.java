package chocan.ui.cli;

import java.util.List;
import java.util.Scanner;

/**
 * Represents an executable command.
 * Usually an instance of this class is added to a command menu to be executed by a user.
 */
public class Command implements CommandFunction {

    public final String name;
    protected String description;
    protected String help;

    private final CommandFunction function;

    /**
     * Creates a new Command.
     * @param name The name of the command.
     * @param description The description of the command.
     * @param help The help info of the command.
     * @param function The function to execute the command.
     */
    public Command(final String name, final String description, final String help, final CommandFunction function) {
        this.name = name;
        this.description = description;
        this.help = help;
        this.function = function;
    }

    /**
     * Gets the description string of the command.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the help string of the command.
     */
    public String getHelp() {
        return this.help;
    }

    @Override
    public boolean execute(final List<String> args, final Scanner stdin) {
        return this.function.execute(args, stdin);
    }

    /**
     * Prints out information about the command.
     * Internally used by other classes in the package.
     * @param includeHelp Whether to print help information.
     */
    void print(final boolean includeHelp) {
        System.out.print(this.name);
        if (this.description.isEmpty()) {
            System.out.println();
        } else {
            System.out.print(": ");
            System.out.println(this.description);
        }
        if (includeHelp && !this.help.isEmpty()) {
            System.out.println(this.help);
        }
    }

}
