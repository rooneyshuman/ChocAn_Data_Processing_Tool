package edu.pdx.cs300.group6.chocan;

import edu.pdx.cs300.group6.chocan.cli.Command;
import edu.pdx.cs300.group6.chocan.cli.CommandLineInterface;

import java.io.InputStream;
import java.util.List;

/**
 *
 */
public class ProvidersApplication {

    /**
     * Entry point for the provider's interactive console application.
     * @param args
     */
    public static void main(final String[] args) {
        final ProvidersApplication application = new ProvidersApplication();
        application.run();
    }

    private final CommandLineInterface cli = new CommandLineInterface();

    /**
     * Initializes the provider's interactive console application.
     */
    public ProvidersApplication() {
        this.cli.addCommand(new Command("verify-member", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.cli.addCommand(new Command("record-service", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.cli.addCommand(new Command("directory", "", "", (final List<String> args) -> {
            // TODO Launch interactive service directory interface
            return true;
        }));
    }

    /**
     * Runs the application.
     */
    public void run() {
        System.out.println("Chocoholics Anonymous: Provider Interface");
        this.cli.run();
    }

}
