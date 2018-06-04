package chocan.ui.menus;

import chocan.report.ReportGenerator;
import chocan.service.ProviderServiceDirectory;
import chocan.service.Service;
import chocan.service.ServiceRecord;
import chocan.service.ServiceRecordDatabase;
import chocan.ui.cli.Command;
import chocan.users.MemberUser;
import chocan.users.User;
import chocan.users.UserDatabase;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 *
 */
public class MainMenu extends CustomMenu {

    private final ProviderMenu providerMenu;
    private final ManagerMenu managerMenu;

    /**
     * Creates an instance of the main menu.
     * @param memberDatabaseSupplier A function that loads the member database.
     * @param providerDatabaseSupplier A function that loads the provider database.
     * @param managerDatabaseSupplier A function that loads the manager database.
     * @param providerServiceDirectorySupplier A function that loads the provider service directory.
     * @param serviceRecordDatabaseSupplier A function that loads the service record database.
     */
    public MainMenu(final Supplier<UserDatabase<? extends MemberUser>> memberDatabaseSupplier,
                    final Supplier<UserDatabase<? extends User>> providerDatabaseSupplier,
                    final Supplier<UserDatabase<? extends User>> managerDatabaseSupplier,
                    final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier,
                    final Supplier<ServiceRecordDatabase<ServiceRecord>> serviceRecordDatabaseSupplier,
                    final ReportGenerator reportGenerator) {
        // Create sub-menus
        this.providerMenu = new ProviderMenu(
            memberDatabaseSupplier,
            providerDatabaseSupplier,
            providerServiceDirectorySupplier,
            serviceRecordDatabaseSupplier);
        this.managerMenu = new ManagerMenu(
            memberDatabaseSupplier,
            providerDatabaseSupplier,
            managerDatabaseSupplier,
            providerServiceDirectorySupplier,
            serviceRecordDatabaseSupplier,
            reportGenerator);
        // Configure main menu
        this.setHelpTitle("[Main Menu] Select a mode:");
        this.add(new Command("provider", "Provider's terminal", "", (final List<String> args, final Scanner stdin) -> {
            this.providerMenu.run(stdin);
            return true;
        }));
        this.add(new Command("manager", "Manager's terminal", "", (final List<String> args, final Scanner stdin) -> {
            this.managerMenu.run(stdin);
            return true;
        }));
    }

}
