package edu.pdx.cs300.group6.chocan.cli;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 */
public class CommandLineInterface {

    private final LinkedHashSet<Command> commands = new LinkedHashSet<>();

    private final Command helpCommand = new Command("help", "", "", (final String[] args, final InputStream inputStream) -> {
        // TODO Print all commands
        return true;
    });
    private Command exitCommand = CommandLineInterface.createExitCommand("exit", "", "");

    /**
     *
     */
    public CommandLineInterface() {
        this.addCommand(this.exitCommand);
    }

    /**
     * Determines whether this CLI contains the command.
     * @param command The command to check.
     */
    public boolean containsCommand(final Command command) {
        return this.commands.contains(command);
    }

    /**
     * Adds the command to this CLI to be available for execution.
     * @param command The command to add.
     * @return Whether the command was newly added.
     */
    public boolean addCommand(final Command command) {
        return this.commands.add(command);
    }

    /**
     * Removes the command from this CLI.
     * @param command The command to remove.
     * @return Whether the command was removed.
     */
    public boolean removeCommand(final Command command) {
        return this.commands.remove(command);
    }

    /**
     *
     * @param partialName
     * @return
     */
    private List<Command> getCommands(final String partialName) {
        // TODO needs to be implemented
        return Collections.emptyList();
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
            this.addCommand(this.helpCommand);
        } else {
            this.removeCommand(this.helpCommand);
        }
    }

    /**
     *
     */
    public void setExitCommand(final String name, final String description, final String help) {
        this.removeCommand(this.exitCommand);
        this.exitCommand = CommandLineInterface.createExitCommand(name, description, help);
        this.addCommand(this.exitCommand);
    }

    /**
     *
     */
    public void run(final InputStream inputStream) {

    }

    private static Command createExitCommand(final String name, final String description, final String help) {
        return new Command(name, description, help, (final String[] args, final InputStream inputStream) -> false);
    }

}
