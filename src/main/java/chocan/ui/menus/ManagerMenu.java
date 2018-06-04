package chocan.ui.menus;

import chocan.report.ReportGenerator;
import chocan.service.ProviderServiceDirectory;
import chocan.service.Service;
import chocan.service.ServiceRecord;
import chocan.service.ServiceRecordDatabase;
import chocan.ui.IOUtils;
import chocan.ui.cli.Command;
import chocan.ui.cli.CommandMenu;
import chocan.users.IUser;
import chocan.users.MemberUser;
import chocan.users.User;
import chocan.users.UserDatabase;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 *
 */
public class ManagerMenu extends CustomMenu {

    private final Supplier<UserDatabase<? extends User>> managerDatabaseSupplier;

    private final UserManagementMenu userManagementMenu;
    private final ServiceManagementMenu serviceManagementMenu;
    private final ReportsMenu reportsMenu;

    /**
     *
     */
    public ManagerMenu(final Supplier<UserDatabase<? extends MemberUser>> memberDatabaseSupplier,
                       final Supplier<UserDatabase<? extends User>> providerDatabaseSupplier,
                       final Supplier<UserDatabase<? extends User>> managerDatabaseSupplier,
                       final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier,
                       final Supplier<ServiceRecordDatabase<ServiceRecord>> serviceRecordDatabaseSupplier,
                       final ReportGenerator reportGenerator) {
        this.managerDatabaseSupplier = managerDatabaseSupplier;
        // Create sub-menus
        this.userManagementMenu = new UserManagementMenu(
            memberDatabaseSupplier,
            providerDatabaseSupplier,
            managerDatabaseSupplier);
        this.serviceManagementMenu = new ServiceManagementMenu(providerServiceDirectorySupplier);
        this.reportsMenu = new ReportsMenu(
            memberDatabaseSupplier,
            providerDatabaseSupplier,
            providerServiceDirectorySupplier,
            serviceRecordDatabaseSupplier,
            reportGenerator);
        // Configure manager menu
        this.setHelpTitle("[Manager Menu] Choose an option:");
        this.add(new Command("user", "User management", "", (final List<String> args, final Scanner stdin) -> {
            this.userManagementMenu.run(stdin);
            return true;
        }));
        this.add(new Command("service", "Service management", "", (final List<String> args, final Scanner stdin) -> {
            this.serviceManagementMenu.run(stdin);
            return true;
        }));
        this.add(new Command("report", "Report generation", "", (final List<String> args, final Scanner stdin) -> {
            this.reportsMenu.run(stdin);
            return true;
        }));
        this.setExitCommand("back", "Go back to main menu", "");
    }

    @Override
    public void run(final Scanner stdin) {
        // Load the manager database
        final UserDatabase<? extends User> managerDatabase = this.managerDatabaseSupplier.get();
        if (managerDatabase == null) {
            return;
        }
        // Authenticate the manager
        if (authenticate(managerDatabase, stdin)) {
            // Run text menu
            super.run(stdin);
        }
    }

    private static <U extends User> boolean authenticate(final UserDatabase<U> managerDatabase, final Scanner stdin) {
        final U manager;
        try {
            // Check for at least one manager
            if (managerDatabase.size() <= 0) {
                System.out.println("No managers detected.");
                manager = ManagerMenu.createInitialManager(managerDatabase, stdin);
                if (manager == null) {
                    return false;
                }
                managerDatabase.add(manager);
                System.out.println("Created initial manager: " + manager.getName() + " (" + manager.getID() + ")");
                System.out.print("Saving manager database...");
                try {
                    managerDatabase.save();
                    System.out.println(" Done.");
                } catch (final IOException e) {
                    System.out.println(" Failed!");
                    e.printStackTrace();
                }
            } else {
                // Authenticate manager
                System.out.print("Enter your manager ID: ");
                final int managerID = IOUtils.readUnsignedInt(stdin, () -> {
                    System.out.println("Error! Please enter a positive numeric manager ID...");
                    System.out.print("Enter your manager ID: ");
                });
                manager = managerDatabase.get(managerID);
                if (manager == null) {
                    System.out.println("Invalid manager ID!");
                    return false;
                }
            }
        } finally {
            System.out.println();
        }
        // Display welcome banner
        System.out.println("*** Welcome, " + manager.getFirstName() + "! ***");
        System.out.println();
        return true;
    }

    @Nullable
    private static <U extends User> U createInitialManager(final UserDatabase<U> userDatabase, final Scanner stdin) {
        // Create local class for menu
        class InitializeManagerMenu extends CommandMenu {

            @Nullable
            private U initialManager = null;

            private InitializeManagerMenu() {
                this.setHelpTitle("How should the initial manager be created?");
                this.setHelpEnabled(false);
                this.add(new Command("provide", "I will provide the information", "", (final List<String> args, final Scanner _stdin) -> {
                    this.initialManager = UserManagementMenu.confirmNewUser(IUser.MIN_ID, userDatabase, stdin, true, "manager");
                    return false;
                }));
                this.add(new Command("default", "Let the application use default information", "", (final List<String> args, final Scanner _stdin) -> {
                    this.initialManager = userDatabase.createUser(IUser.MIN_ID, "admin", "", "", "", 0);
                    return false;
                }));
                this.setExitCommand("back", "Go back to main menu", "");
            }

        }
        // Run menu
        final InitializeManagerMenu initializeManagerMenu = new InitializeManagerMenu();
        initializeManagerMenu.run(stdin);
        return initializeManagerMenu.initialManager;
    }

}
