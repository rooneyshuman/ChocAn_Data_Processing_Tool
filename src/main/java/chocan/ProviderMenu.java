package chocan;

import chocan.cli.Command;
import chocan.cli.CommandMenu;

import java.util.List;

/**
 *
 */
public class ProviderMenu extends CommandMenu {

    /**
     *
     */
    public ProviderMenu() {
        this.addCommand(new Command("verify-member", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.addCommand(new Command("record-service", "", "", (final List<String> args) -> {
            // TODO
            return true;
        }));
        this.addCommand(new Command("directory", "", "", (final List<String> args) -> {
            // TODO Launch interactive service directory interface
            return true;
        }));
    }

}
