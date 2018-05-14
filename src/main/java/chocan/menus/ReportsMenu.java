package chocan.menus;

import chocan.cli.Command;
import chocan.cli.CommandMenu;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class ReportsMenu extends CommandMenu {

    /**
     *
     */
    public ReportsMenu() {
        this.setHelpTitle("[Report Menu] Select a report to generate:");
        this.add(new Command("member", "Generate a member report", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("provider", "Generate a provider report", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("manager", "Generate a manager summary report", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.setExitCommand("back", "Go back to manager menu", "");
    }

}
