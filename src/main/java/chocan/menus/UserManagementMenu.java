package chocan.menus;

import chocan.cli.Command;
import chocan.cli.CommandMenu;
import chocan.users.Manager;
import chocan.utils.IOUtils;
import chocan.utils.ParseUtils;

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

    /**
     * Prompts the user to enter manager information.
     * @param id The ID of the new manager.
     * @param stdin A standard input scanner.
     * @return A newly created manager object.
     */
    public static Manager promptManager(final int id, final Scanner stdin) {
        System.out.print("Name: ");
        final String name = stdin.nextLine();
        System.out.print("Street address: ");
        final String address = stdin.nextLine();
        System.out.print("City: ");
        final String city = stdin.nextLine();
        System.out.print("State: ");
        final String state = stdin.nextLine();
        System.out.print("Zip: ");
        final int zip = IOUtils.readUnsignedInt(stdin, () -> {
            System.out.println("Unable to parse zip as an integer. Please try again...");
            System.out.print("Zip: ");
        });
        return new Manager(id, true, name, address, city, state, zip);
    }

}
