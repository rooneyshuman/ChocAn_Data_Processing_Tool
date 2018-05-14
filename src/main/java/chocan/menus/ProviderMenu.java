package chocan.menus;

import chocan.cli.Command;
import chocan.cli.CommandMenu;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ProviderMenu extends CommandMenu {

    /**
     *
     */
    public ProviderMenu() {
        this.setHelpTitle("[Provider Menu] Choose an option:");
        this.add(new Command("verify", "Verify a member", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("service", "Record a service", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("directory", "Provider Service Directory", "", (final List<String> args, final Scanner stdin) -> {
            // TODO Launch interactive service directory interface
            return true;
        }));
    }

}
