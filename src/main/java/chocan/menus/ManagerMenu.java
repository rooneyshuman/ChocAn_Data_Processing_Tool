package chocan.menus;

import chocan.cli.Command;
import chocan.cli.CommandMenu;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ManagerMenu extends CommandMenu {

    /**
     *
     */
    public ManagerMenu() {
        this.setHelpTitle("[Manager Menu] Choose an option:");
        this.add(new Command("user", "User management", "", (final List<String> args, final Scanner stdin) -> {
            // TODO Launch user management menu
            this.help();
            return true;
        }));
        this.add(new Command("service", "Service management", "", (final List<String> args, final Scanner stdin) -> {
            // TODO Launch service management menu
            this.help();
            return true;
        }));
    }

}
