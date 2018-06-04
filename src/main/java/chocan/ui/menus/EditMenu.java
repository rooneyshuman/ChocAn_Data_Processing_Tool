package chocan.ui.menus;

import chocan.database.Database;
import chocan.ui.cli.Command;
import chocan.ui.cli.CommandFunction;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * TODO
 * @param <T>
 */
public class EditMenu<T> extends CustomMenu {

    private final String previousMenuName;
    protected final T copy;
    protected final Runnable save;

    /**
     * TODO
     * @param original
     * @param cloneFunction
     * @param setFunction
     * @param database
     * @param databaseName
     * @param previousMenuName
     */
    protected EditMenu(final T original, final Function<T, T> cloneFunction, final BiConsumer<T, T> setFunction,
                       final Database<? extends T> database, final String databaseName,
                       final String previousMenuName) {
        this.previousMenuName = previousMenuName;
        this.copy = cloneFunction.apply(original);
        this.save = () -> {
            if (Objects.equals(original, copy)) {
                System.out.println("No changes.");
            } else {
                setFunction.accept(original, copy);
                System.out.print("Saving " + databaseName + "...");
                try {
                    database.save();
                    System.out.println(" Done.");
                } catch (final IOException e) {
                    System.out.println(" Failed!");
                    e.printStackTrace();
                }
            }
        };
        this.setHelpEnabled(false);
        this.setExitCommand("cancel", "Go back to " + previousMenuName + " without saving", "");
    }

    protected void init() {
        this.add(new Command("save", "Save and go back to " + this.previousMenuName, "", (final List<String> args, final Scanner stdin) -> {
            this.save.run();
            return false;
        }));
    }

    /**
     * Processes the argument list with standard input scanner.
     * @param args The argument list to process.
     * @param stdin The standard input scanner to use to read interactive input.
     */
    public void process(final List<String> args, final Scanner stdin) {
        this.execute(args.get(0), args.subList(1, args.size()), stdin, true);
        this.save.run();
    }

    /**
     * TODO
     */
    protected static class FieldCommand extends Command {

        private final Supplier supplier;

        /**
         * Creates a command to edit an object field.
         * @param name The name of the object field.
         * @param supplier A supplier of the displayed field value.
         * @param function A function to edit the object field.
         */
        FieldCommand(final String name,
                     final Supplier supplier,
                     final CommandFunction function) {
            super(name, String.valueOf(supplier.get()), "", function);
            this.supplier = supplier;
        }

        @Override
        public boolean execute(final List<String> args, final Scanner stdin) {
            if (super.execute(args, stdin)) {
                this.description = String.valueOf(this.supplier.get());
            }
            return true;
        }

    }

}
