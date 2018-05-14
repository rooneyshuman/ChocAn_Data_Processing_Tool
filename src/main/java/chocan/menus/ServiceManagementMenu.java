package chocan.menus;

import chocan.cli.Command;
import chocan.cli.CommandMenu;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ServiceManagementMenu extends CommandMenu {

    /**
     *
     */
    public ServiceManagementMenu() {
        this.setHelpTitle("[Service Management Menu] Select an action:");
        this.add(new Command("add", "Add a new service", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("update", "Update an existing service", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("remove", "Remove a service", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.setExitCommand("back", "Go back to manager menu", "");
    }

}
