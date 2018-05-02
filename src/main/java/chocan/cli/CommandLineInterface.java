package chocan.cli;

import java.util.*;

/**
 *
 */
public class CommandLineInterface {

    private static final long HELP_COMMAND_ORDER = Long.MAX_VALUE - 1;
    private static final long EXIT_COMMAND_ORDER = Long.MAX_VALUE;

    private final HashMap<Command, Long> commandOrderMap = new HashMap<>();
    private final TreeSet<Command> commands = new TreeSet<>(Comparator.comparingLong(this.commandOrderMap::get));

    private final Command helpCommand = new Command("help", "Displays additional help for a command.", "", this::helpCommandHandler);
    private Command exitCommand = CommandLineInterface.createExitCommand("exit", "Exits the application.", "");

    private long commandOrder = Long.MIN_VALUE;

    /**
     * The string to print to indicate a command prompt.
     */
    private String prompt = "> ";

    /**
     * Whether to display the help command automatically if an empty line is entered.
     */
    private boolean helpOnEmpty = true;

    /**
     *
     */
    public CommandLineInterface() {
        this.addCommand(this.helpCommand, HELP_COMMAND_ORDER);
        this.addCommand(this.exitCommand, EXIT_COMMAND_ORDER);
    }

    /**
     * Determines whether this CLI contains the command.
     * @param command The command to check.
     */
    public boolean containsCommand(final Command command) {
        return this.commandOrderMap.containsKey(command);
    }

    /**
     * Adds the command to this CLI to be available for execution.
     * @param command The command to add.
     * @return Whether the command was newly added.
     */
    public boolean addCommand(final Command command) {
        return this.addCommand(command, this.commandOrder++);
    }

    private boolean addCommand(final Command command, final long order) {
        if (this.commandOrderMap.containsKey(command)) {
            return false;
        }
        this.commandOrderMap.put(command, order);
        return this.commands.add(command);
    }

    /**
     * Removes the command from this CLI.
     * @param command The command to remove.
     * @return Whether the command was removed.
     */
    public boolean removeCommand(final Command command) {
        final boolean success = this.commands.remove(command);
        this.commandOrderMap.remove(command);
        return success;
    }

    /**
     * Gets a list of commands who's names start with the given partial name.
     * @param partialName Part of a command's name to use to filter command.
     * @return A list of commands filtered using the given partial name.
     */
    private List<Command> getCommands(final String partialName) {
        final List<Command> matchedCommands = new ArrayList<>(this.commands.size());
        for (final Command command : this.commands) {
            if (command.name.startsWith(partialName)) {
                matchedCommands.add(command);
            }
        }
        return matchedCommands;
    }
    
    /**
     * Gets the string that is printed to indicate a command prompt.
     */
    public String getPrompt() {
        return this.prompt;
    }
    
    /**
     * Set the string to print to indicate a command prompt.
     * @param prompt The string to print.
     */
    public void setPrompt(final String prompt) {
        this.prompt = prompt;
    }

    /**
     * Determines whether the help command is enabled for this CLI.
     */
    public boolean isHelpEnabled() {
        return this.containsCommand(this.helpCommand);
    }

    /**
     * Enables or disables the help command for this CLI.
     * @param enabled Whether to enable or disable the help command.
     */
    public void setHelpEnabled(final boolean enabled) {
        if (enabled) {
            this.addCommand(this.helpCommand, HELP_COMMAND_ORDER);
        } else {
            this.removeCommand(this.helpCommand);
        }
    }

    /**
     * Enables or disables the help command to automatically execute if an empty line is entered.
     * @param enabled Whether to enable or disable this automatic functionality.
     */
    public void setHelpOnEmpty(final boolean enabled) {
        this.helpOnEmpty = enabled;
    }

    private boolean helpCommandHandler(final List<String> args) {
        if (args.size() > 0) {
            final String partialCommandName = args.get(0);
            final List<Command> matchedCommands = this.getCommands(partialCommandName);
            switch (matchedCommands.size()) {
                case 0:
                    System.out.println("[Help] Unknown command: " + partialCommandName);
                    break;
                case 1:
                    // Display extended help for command
                    matchedCommands.get(0).print(true);
                    break;
                default:
                    // Ambiguous command (multiple possible commands)
                    // Display all commands that matched
                    final Iterable<String> commandNames = matchedCommands.stream().map(c -> c.name)::iterator;
                    System.out.println("[Help] Ambiguous command selection: " + String.join(", ", commandNames));
                    break;
            }
        } else {
            // Display all commands
            for (final Command command : this.commands) {
                command.print(false);
            }
        }
        return true;
    }

    /**
     *
     */
    public void setExitCommand(final String name, final String description, final String help) {
        this.removeCommand(this.exitCommand);
        this.exitCommand = CommandLineInterface.createExitCommand(name, description, help);
        this.addCommand(this.exitCommand, EXIT_COMMAND_ORDER);
    }
    
    /**
     * Run the command line interface.
     * This will block as it reads from the given input stream.
     */
    public void run() {
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.print(this.prompt);
            for (; scanner.hasNextLine(); System.out.print(this.prompt)) {
                // Get next line entered by user
                final String line = scanner.nextLine();
                // Parse line
                final List<String> args = CommandLineInterface.parseArguments(line);
                if (args.size() > 0) {
                    // Get commands that match
                    final String partialCommandName = args.get(0);
                    final List<Command> matchedCommands = this.getCommands(partialCommandName);
                    switch (matchedCommands.size()) {
                        case 0:
                            System.out.println("Unknown command: " + partialCommandName);
                            break;
                        case 1:
                            // Execute command with arguments (excluding the command name)
                            if (!matchedCommands.get(0).execute(args.subList(1, args.size()))) {
                                // Exit command line interface
                                return;
                            }
                            break;
                        default:
                            // Ambiguous command (multiple possible commands)
                            // Display all commands that matched
                            final Iterable<String> commandNames = matchedCommands.stream().map(c -> c.name)::iterator;
                            System.out.println("Ambiguous command selection: " + String.join(", ", commandNames));
                            break;
                    }
                } else if (this.helpOnEmpty) {
                    // Display help
                    this.helpCommand.execute(Collections.emptyList());
                }
            }
        }
    }

    private static Command createExitCommand(final String name, final String description, final String help) {
        return new Command(name, description, help, (final List<String> args) -> false);
    }

    private static List<String> parseArguments(final String line) {
        final List<String> args = new LinkedList<>();
        final int l = line.length();
        final StringBuilder sb = new StringBuilder(l);
        boolean quoted = false;
        for (int i = 0; i < l; i++) {
            final char c = line.charAt(i);
            if (c == '"') {
                quoted = !quoted;
            } else if (!quoted && c == ' ') {
                args.add(sb.toString());
                sb.delete(0, sb.length());
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) {
            args.add(sb.toString());
        }
        // Convert to array list for fast random access.
        return new ArrayList<>(args);
    }

}
