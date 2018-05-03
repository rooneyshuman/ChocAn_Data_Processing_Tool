package chocan;

import chocan.cli.Command;
import chocan.cli.CommandMenu;

import java.util.List;

/**
 *
 */
public class MainMenu extends CommandMenu {

    /**
     *
     */
    public MainMenu() {
        this.addCommand(new Command("provider", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.addCommand(new Command("operator", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.addCommand(new Command("manager", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.addCommand(new Command("acme", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
    }

}
