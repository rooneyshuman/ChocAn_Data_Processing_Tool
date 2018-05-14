package chocan.menus;

import chocan.cli.Command;
import chocan.cli.CommandMenu;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class UserManagementMenu extends CommandMenu {

    /**
     *
     */
    public UserManagementMenu() {
        this.setHelpTitle("[User Management Menu] Select an action:");
        this.add(new Command("add-member", "Add a new member", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("update-member", "Update an existing member", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("remove-member", "Remove a member", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("add-provider", "Add a new provider", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("update-provider", "Update an existing provider", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.add(new Command("remove-provider", "Remove a provider", "", (final List<String> args, final Scanner stdin) -> {
            // TODO
            return true;
        }));
        this.setExitCommand("back", "Go back to manager menu", "");
    }

}
