package chocan.menus;

import chocan.cli.Command;
import chocan.cli.CommandMenu;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class MainMenu extends CommandMenu {

    private final ProviderMenu providerMenu = new ProviderMenu();
    private final ManagerMenu managerMenu = new ManagerMenu();

    /**
     *
     */
    public MainMenu() {
        this.setHelpTitle("[Main Menu] Select a mode:");
        this.add(new Command("provider", "Provider's terminal", "", (final List<String> args, final Scanner stdin) -> {
            // TODO Provider authentication
            this.providerMenu.run(stdin);
            this.help();
            return true;
        }));
        this.add(new Command("manager", "Manager's terminal", "", (final List<String> args, final Scanner stdin) -> {
            // TODO Manager authentication
            this.managerMenu.run(stdin);
            this.help();
            return true;
        }));
    }

}
