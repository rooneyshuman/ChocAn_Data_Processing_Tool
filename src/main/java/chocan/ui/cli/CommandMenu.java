package chocan.ui.cli;

import chocan.ui.ParseUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An interactive command line menu.
 */
public class CommandMenu {

    private static final long HELP_COMMAND_ORDER = Long.MAX_VALUE - 1;
    private static final long EXIT_COMMAND_ORDER = Long.MAX_VALUE;

    private final HashMap<Command, Long> commandOrderMap = new HashMap<>();
    private final TreeMap<Command, Boolean> commands = new TreeMap<>(Comparator.comparingLong(this.commandOrderMap::get));

    private final Command helpCommand = new Command("help", "Displays additional help for a command.", "", (final List<String> args, final Scanner stdin) -> {
        if (args.size() > 0) {
            this.help(args.get(0));
        } else {
            this.help();
        }
        return true;
    });
    private Command exitCommand = CommandMenu.createExitCommand("exit", "Exits the application.", "");

    private long commandOrder = Long.MIN_VALUE;

    /**
     * The string to print to indicate a command prompt.
     */
    private String prompt = "> ";

    /**
     * The string to print as the title of the help command.
     */
    private String helpTitle = "";

    /**
     * Whether to display the help command automatically when the command menu is run.
     */
    private boolean helpOnStart = true;

    /**
     * Whether to display the help command automatically if an empty line is entered.
     */
    private boolean helpOnEmpty = true;

    /**
     * Creates a new command menu.
     */
    public CommandMenu() {
        this.add(this.helpCommand, HELP_COMMAND_ORDER, false);
        this.add(this.exitCommand, EXIT_COMMAND_ORDER, false);
    }

    /**
     * Determines whether this menu contains the command.
     * @param command The command to check.
     */
    public boolean contains(final Command command) {
        return this.commandOrderMap.containsKey(command);
    }

    /**
     * Adds the command to this menu to be available for execution.
     * @param command The command to add.
     * @return Whether the command was newly added.
     */
    public boolean add(final Command command) {
        return this.add(command, this.commandOrder++, true);
    }

    private boolean add(final Command command, final long order, final boolean custom) {
        if (this.commandOrderMap.containsKey(command)) {
            return false;
        }
        this.commandOrderMap.put(command, order);
        this.commands.put(command, custom);
        return true;
    }

    /**
     * Removes the command from this menu.
     * @param command The command to remove.
     * @return Whether the command was removed.
     */
    public boolean remove(final Command command) {
        final boolean success = this.commands.remove(command);
        this.commandOrderMap.remove(command);
        return success;
    }

    /**
     * Gets a map of commands who's names start with the given partial name (or the command at the index).
     * @param partialNameOrNumber Part of a command's name to use to filter commands (or the index of the command).
     * @return A map of commands filtered using the given partial name (or the command at the index).
     */
    private Map<Command, Boolean> get(final String partialNameOrNumber) {
        final Integer commandNumber = ParseUtils.parseUnsignedInt(partialNameOrNumber);
        if (commandNumber != null && commandNumber > 0) {
            final Optional<Map.Entry<Command, Boolean>> foundCommand = this.commands.entrySet().stream().skip(commandNumber - 1).findFirst();
            if (foundCommand.isPresent()) {
                final Map.Entry<Command, Boolean> e = foundCommand.get();
                return Collections.singletonMap(e.getKey(), e.getValue());
            }
        }
        return this.commands.entrySet().stream()
                .filter(e -> e.getKey().name.startsWith(partialNameOrNumber))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
     * Gets the string that is printed as the title of the help command.
     */
    public String getHelpTitle() {
        return this.helpTitle;
    }

    /**
     * Set the string to print as the title of the help command.
     * @param helpTitle The string to print.
     */
    public void setHelpTitle(final String helpTitle) {
        this.helpTitle = helpTitle;
    }

    /**
     * Determines whether the help command is enabled for this menu.
     */
    public boolean isHelpEnabled() {
        return this.contains(this.helpCommand);
    }

    /**
     * Enables or disables the help command for this menu.
     * @param enabled Whether to enable or disable the help command.
     */
    public void setHelpEnabled(final boolean enabled) {
        if (enabled) {
            this.add(this.helpCommand, HELP_COMMAND_ORDER, false);
        } else {
            this.remove(this.helpCommand);
        }
    }

    /**
     * Enables or disables the help command to automatically execute when the menu is run.
     * @param enabled Whether to enable or disable this automatic functionality.
     */
    public void setHelpOnStart(final boolean enabled) {
        this.helpOnStart = enabled;
    }

    /**
     * Enables or disables the help command to automatically execute if an empty line is entered.
     * @param enabled Whether to enable or disable this automatic functionality.
     */
    public void setHelpOnEmpty(final boolean enabled) {
        this.helpOnEmpty = enabled;
    }

    /**
     * Lists the name and description of the commands in this menu.
     */
    public void help() {
        // Display help title
        if (!this.helpTitle.isEmpty()) {
            System.out.println(this.helpTitle);
        }
        // Display all commands
        int i = 1;
        for (final Command command : this.commands.keySet()) {
            System.out.print("" + i + ". ");
            command.print(false);
            i++;
        }
    }

    /**
     * Displays the help of a command that starts with the given string.
     * @param partialCommandName Part of a command's name to use to filter commands (or the index of the command).
     * @return Whether the command was able to display help of some kind.
     */
    public boolean help(final String partialCommandName) {
        final Map<Command, Boolean> matchedCommands = this.get(partialCommandName);
        switch (matchedCommands.size()) {
            case 0:
                System.out.println("[Help] Unknown command: " + partialCommandName);
                return false;
            case 1:
                // Display extended help for command
                matchedCommands.keySet().stream().findFirst().orElseThrow(RuntimeException::new).print(true);
                return true;
            default:
                // Ambiguous command (multiple possible commands)
                // Display all commands that matched
                final Iterable<String> commandNames = matchedCommands.keySet().stream().map(c -> c.name)::iterator;
                System.out.println("[Help] Ambiguous command selection: " + String.join(", ", commandNames));
                return false;
        }
    }

    /**
     * Configures the properties of the exit command.
     * @param name The name of the exit command.
     * @param description The description of the exit command.
     * @param help The help text of the exit command.
     */
    public void setExitCommand(final String name, final String description, final String help) {
        this.remove(this.exitCommand);
        this.exitCommand = CommandMenu.createExitCommand(name, description, help);
        this.add(this.exitCommand, EXIT_COMMAND_ORDER, false);
    }
    
    /**
     * Run the command menu using the given input scanner.
     * This will block as it reads from the given scanner.
     * @param stdin The scanner to use to read input.
     */
    public void run(final Scanner stdin) {
        if (this.helpOnStart) {
            // Display help
            this.help();
        }
        System.out.print(this.prompt);
        for (; stdin.hasNextLine(); System.out.print(this.prompt)) {
            // Get next line entered by user
            final String line = stdin.nextLine();
            // Process line
            if (!processLine(line, stdin)) {
                break;
            }
        }
    }

    private boolean processLine(final String line, final Scanner stdin) {
        // Parse line
        final List<String> args = CommandMenu.parseArguments(line);
        if (args.size() > 0) {
            return this.execute(args.get(0), args.subList(1, args.size()), stdin);
        } else if (this.helpOnEmpty) {
            // Display help
            this.help();
        }
        return true;
    }

    /**
     * Executes a command as if it was typed interactively.
     * @param partialCommandName The partial name of the command to execute.
     * @param args The arguments to pass to the command.
     * @param stdin The standard input scanner.
     * @return The result of the command - whether to continue running the command menu.
     */
    public boolean execute(final String partialCommandName, final List<String> args, final Scanner stdin) {
        return this.execute(partialCommandName, args, stdin, false);
    }

    /**
     * Executes a command as if it was typed interactively.
     * @param partialCommandName The partial name of the command to execute.
     * @param args The arguments to pass to the command.
     * @param stdin The standard input scanner.
     * @param silent Whether to not print anything.
     * @return The result of the command - whether to continue running the command menu.
     */
    protected boolean execute(final String partialCommandName, final List<String> args, final Scanner stdin, final boolean silent) {
        // Get commands that match
        final Map<Command, Boolean> matchedCommands = this.get(partialCommandName);
        switch (matchedCommands.size()) {
            case 0:
                System.out.println("Unknown command: " + partialCommandName);
                break;
            case 1:
                // Execute command with arguments (excluding the command name)
                final Map.Entry<Command, Boolean> e = matchedCommands.entrySet().stream()
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
                final Command command = e.getKey();
                final boolean custom = e.getValue();
                this.beforeCommandExecuted(command, custom, silent);
                final boolean result = command.execute(args, stdin);
                this.afterCommandExecuted(command, custom, silent, result);
                if (!result) {
                    // Exit command line interface
                    return false;
                }
                break;
            default:
                // Ambiguous command (multiple possible commands)
                // Display all commands that matched
                final Iterable<String> commandNames = matchedCommands.keySet().stream().map(c -> c.name)::iterator;
                System.out.println("Ambiguous command selection: " + String.join(", ", commandNames));
                break;
        }
        return true;
    }

    /**
     * Invoked before a command is executed.
     * @param command The command that will be executed.
     * @param custom Whether the command is custom (user-provided).
     * @param silent Whether to not print anything.
     */
    protected void beforeCommandExecuted(final Command command, final boolean custom, final boolean silent) {}

    /**
     * Invoked after a command is executed.
     * @param command The command that was executed.
     * @param custom Whether the command is custom (user-provided).
     * @param silent Whether to not print anything.
     * @param result The result of the executed command.
     */
    protected void afterCommandExecuted(final Command command, final boolean custom, final boolean silent, final boolean result) {}

    // region Static methods

    private static Command createExitCommand(final String name, final String description, final String help) {
        return new Command(name, description, help, (final List<String> args, final Scanner stdin) -> false);
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

    // endregion

}
