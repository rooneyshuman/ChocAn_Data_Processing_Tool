package edu.pdx.cs300.group6.chocan.cli;

import java.io.InputStream;

/**
 *
 */
public class Command implements CommandFunction {

    public final String name;
    public final String description;
    public final String help;

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

    @Override
    public boolean execute(final String[] args, final InputStream inputStream) {
        return this.function.execute(args, inputStream);
    }

}
