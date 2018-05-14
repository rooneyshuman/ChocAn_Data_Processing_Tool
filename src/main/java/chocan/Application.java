package chocan;

import chocan.menus.MainMenu;
import chocan.menus.UserManagementMenu;
import chocan.users.Manager;
import chocan.users.ManagerDatabase;

import java.io.IOException;
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
        System.out.println("[Chocoholics Anonymous] Welcome!");
        // Load the manager database
        final ManagerDatabase managerDatabase = new ManagerDatabase();
        try {
            System.out.print("Loading databases...");
            managerDatabase.load();
            System.out.println(" Done.");
            // Check for at least one manager
            if (managerDatabase.size() <= 0) {
                System.out.println("No managers detected. Entering setup mode...");
                //System.out.println("Create initial manager:");
                //final Scanner stdin = new Scanner(System.in);
                //final Manager initialManager = UserManagementMenu.promptManager(100000000, stdin);
                final Manager initialManager = new Manager(100000000, true, "admin", "", "", "", 0);
                managerDatabase.add(initialManager);
                System.out.println("Created initial manager: " + initialManager.name + " (" + initialManager.id + ")");
            }
            // Start text menu
            new MainMenu().run();
        } finally {
            System.out.print("Saving databases...");
            managerDatabase.save();
            System.out.println(" Done.");
        }
    }

}
