package edu.pdx.cs300.group6.chocan.cli;

import java.io.InputStream;

@FunctionalInterface
public interface CommandFunction {

    /**
     * Executes the command function with the given arguments and input stream.
     * @param args An array of arguments that were passed to this command.
     * @param inputStream The input stream to read interactive user input.
     * @return Whether to prompt for the next command or not.
     */
    boolean execute(final String[] args, final InputStream inputStream);

}
