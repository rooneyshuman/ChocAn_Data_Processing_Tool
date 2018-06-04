package chocan.ui.menus;

import chocan.ui.cli.Command;
import chocan.ui.cli.CommandMenu;

/**
 *
 */
public class CustomMenu extends CommandMenu {

    @Override
    protected void beforeCommandExecuted(final Command command, final boolean custom, final boolean silent) {
        super.beforeCommandExecuted(command, custom, silent);
        if (!silent) {
            if (custom) {
                System.out.println();
            }
        }
    }

    @Override
    protected void afterCommandExecuted(final Command command, final boolean custom, final boolean silent, final boolean result) {
        super.afterCommandExecuted(command, custom, silent, result);
        if (!silent) {
            if (!result) {
                System.out.println();
            } else if (custom) {
                this.help();
            }
        }
    }

}
