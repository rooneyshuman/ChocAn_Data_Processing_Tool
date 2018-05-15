package chocan;

import chocan.cli.Command;
import chocan.cli.CommandMenu;
import chocan.menus.MainMenu;
import chocan.menus.UserManagementMenu;
import chocan.users.Manager;
import chocan.users.ManagerDatabase;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The main application class.
 */
public class Application {

    /**
     * Entry point for the interactive console application.
     * @param args
     */
    public static void main(final String[] args) throws IOException {
        final Application application = new Application();
        application.run();
    }

    /**
     * Initializes the interactive console application.
     */
    private Application() {}

    /**
     * Runs the application.
     */
    private void run() throws IOException {
        // Load the manager database
        final ManagerDatabase managerDatabase = new ManagerDatabase();
        try {
            System.out.print("Loading databases...");
            managerDatabase.load();
            System.out.println(" Done.");
            try (final Scanner stdin = new Scanner(System.in)) {
                // Check for at least one manager
                if (managerDatabase.size() <= 0) {
                    System.out.println("No managers detected.");
                    final Manager initialManager = Application.createInitialManager(stdin, 100000000);
                    if (initialManager == null) {
                        return;
                    }
                    managerDatabase.add(initialManager);
                    System.out.println("Created initial manager: " + initialManager.name + " (" + initialManager.id + ")");
                }
                // Display welcome message
                System.out.println("[Chocoholics Anonymous] Welcome!");
                // Start text menu
                new MainMenu().run(stdin);
            }
        } finally {
            System.out.print("Saving databases...");
            managerDatabase.save();
            System.out.println(" Done.");
        }
    }
    
    @Nullable
    private static Manager createInitialManager(final Scanner stdin, final int id) {
        final Manager[] initialManager = new Manager[1];
        final CommandMenu initialManagerMenu = new CommandMenu();
        initialManagerMenu.setHelpTitle("How should the initial manager be created?");
        initialManagerMenu.setHelpEnabled(false);
        initialManagerMenu.add(new Command("provide", "I will provide the information", "", (final List<String> args, final Scanner _stdin) -> {
            initialManager[0] = UserManagementMenu.promptManager(id, stdin);
            return false;
        }));
        initialManagerMenu.add(new Command("default", "Let the application use default information", "", (final List<String> args, final Scanner _stdin) -> {
            initialManager[0] = new Manager(id, true, "admin", "", "", "", 0);
            return false;
        }));
        initialManagerMenu.run(stdin);
        return initialManager[0];
    }

}
