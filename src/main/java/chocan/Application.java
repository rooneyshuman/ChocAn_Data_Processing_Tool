package chocan;

import chocan.menus.MainMenu;

/**
 * The main application class.
 */
public class Application {

    /**
     * Entry point for the interactive console application.
     * @param args
     */
    public static void main(final String[] args) {
        final Application application = new Application();
        application.run();
    }

    private final MainMenu mainMenu = new MainMenu();

    /**
     * Initializes the interactive console application.
     */
    private Application() {}

    /**
     * Runs the application.
     */
    private void run() {
        System.out.println("[Chocoholics Anonymous] Welcome!");
        this.mainMenu.run();
    }

}
