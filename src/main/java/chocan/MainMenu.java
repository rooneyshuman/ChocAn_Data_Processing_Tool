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
        this.add(new Command("provider", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.add(new Command("operator", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.add(new Command("manager", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.add(new Command("acme", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
    }

}
