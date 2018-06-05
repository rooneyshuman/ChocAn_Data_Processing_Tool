package chocan.ui.menus;

import chocan.service.IService;
import chocan.service.IServiceRecord;
import chocan.service.ProviderServiceDirectory;
import chocan.service.Service;
import chocan.service.ServiceRecord;
import chocan.service.ServiceRecordDatabase;
import chocan.ui.IOUtils;
import chocan.ui.ParseUtils;
import chocan.ui.cli.Command;
import chocan.users.MemberUser;
import chocan.users.User;
import chocan.users.UserDatabase;

import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 *
 */
public class ProviderMenu extends CustomMenu {

    private final Supplier<UserDatabase<? extends MemberUser>> memberDatabaseSupplier;
    private final Supplier<UserDatabase<? extends User>> providerDatabaseSupplier;
    private final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier;
    private final Supplier<ServiceRecordDatabase<ServiceRecord>> serviceRecordDatabaseSupplier;

    @Nullable
    private User currentProvider;

    /**
     *
     */
    public ProviderMenu(final Supplier<UserDatabase<? extends MemberUser>> memberDatabaseSupplier,
                        final Supplier<UserDatabase<? extends User>> providerDatabaseSupplier,
                        final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier,
                        final Supplier<ServiceRecordDatabase<ServiceRecord>> serviceRecordDatabaseSupplier) {
        this.memberDatabaseSupplier = memberDatabaseSupplier;
        this.providerDatabaseSupplier = providerDatabaseSupplier;
        this.providerServiceDirectorySupplier = providerServiceDirectorySupplier;
        this.serviceRecordDatabaseSupplier = serviceRecordDatabaseSupplier;
        // Configure provider menu
        this.setHelpTitle("[Provider Menu] Choose an option:");
        this.add(new Command("verify", "Verify a member", "[member-id]", (final List<String> args, final Scanner stdin) -> {
            this.verifyMember(args, stdin);
            return true;
        }));
        this.add(new Command("service", "Record a service", "[member-id]", this::recordService));
        this.add(new Command("directory", "Open Provider Service Directory", "", this::printProviderServiceDirectory));
        this.setExitCommand("back", "Go back to main menu", "");
    }

    @Override
    public void run(final Scanner stdin) {
        // Load the provider database
        final UserDatabase<? extends User> providerDatabase = this.providerDatabaseSupplier.get();
        if (providerDatabase == null) {
            return;
        }
        final User provider;
        try {
            // Check for at least one provider
            if (providerDatabase.size() <= 0) {
                System.out.println("No providers detected.");
                return;
            } else {
                // Authenticate provider
                System.out.print("Enter your provider ID: ");
                final int providerID = IOUtils.readUnsignedInt(stdin, () -> {
                    System.out.println("Error! Please enter a positive 9-digit provider ID...");
                    System.out.print("Enter your provider ID: ");
                });
                provider = providerDatabase.get(providerID);
                if (provider == null) {
                    System.out.println("Invalid provider ID!");
                    return;
                }
            }
        } finally {
            System.out.println();
        }
        // Display welcome banner
        System.out.println("*** Welcome, " + provider.getFirstName() + "! ***");
        System.out.println();
        // Set current provider
        try {
            this.currentProvider = provider;
            // Run text menu
            super.run(stdin);
        } finally {
            this.currentProvider = null;
        }
    }

    @Nullable
    private MemberUser verifyMember(final List<String> args, final Scanner stdin) {
        try {
            final int memberID;
            if (args.size() > 0) {
                final Integer parsedMemberID = ParseUtils.parseUnsignedInt(args.get(0));
                if (parsedMemberID == null) {
                    System.out.println("Failed to parse member ID!");
                    return null;
                }
                memberID = parsedMemberID;
            } else {
                System.out.print("Enter a member ID: ");
                memberID = IOUtils.readUnsignedInt(stdin, () -> {
                    System.out.println("Error! Please enter a positive 9-digit member ID...");
                    System.out.print("Enter a member ID: ");
                });
            }
            final UserDatabase<? extends MemberUser> memberDatabase = this.memberDatabaseSupplier.get();
            if (memberDatabase != null) {
                final MemberUser member = memberDatabase.get(memberID);
                if (member != null) {
                    if (member.isActive()) {
                        System.out.println("Validated");
                        return member;
                    } else {
                        System.out.println("Error: Member suspended");
                    }
                } else {
                    System.out.println("Error: Invalid number (" + memberID + ")");
                }
            }
        } finally {
            System.out.println();
        }
        return null;
    }

    private boolean recordService(final List<String> args, final Scanner stdin) {
        final User currentProvider = Objects.requireNonNull(this.currentProvider);
        final MemberUser verifiedMember = this.verifyMember(args, stdin);
        if (verifiedMember == null) {
            return true;
        }
        try {
            // Prompt for service record information
            final String serviceDatePrompt = "Enter the date of the service (MM-DD-YYYY): ";
            System.out.print(serviceDatePrompt);
            final LocalDate serviceDate = IOUtils.readDate(stdin, () -> {
                System.out.println("Error! Please enter a date in the requested format: MM-DD-YYYY");
                System.out.print(serviceDatePrompt);
            });
            final IService service = this.readService(stdin);
            if (service != null) {
                final String comments = IOUtils.readMultiLine(stdin, 
                    "Enter additional comments: (Type an empty line to finish)", 
                    "Comments", IServiceRecord.MAX_COMMENTS_LENGTH);
                // Get service record database
                final ServiceRecordDatabase<ServiceRecord> serviceRecordDatabase = this.serviceRecordDatabaseSupplier.get();
                if (serviceRecordDatabase != null) {
                    // Create a new service record with the entered information
                    final ServiceRecord newServiceRecord = serviceRecordDatabase.createServiceRecord(serviceDate, currentProvider.getID(), verifiedMember.getID(), service.getCode(), comments);
                    // Add service record to database
                    serviceRecordDatabase.add(newServiceRecord);
                    System.out.println("Added new service record: " + currentProvider.getName() + " provided " + service.getName() + " to " + verifiedMember.getName() + ".");
                    System.out.print("Saving service record database...");
                    try {
                        serviceRecordDatabase.save();
                        System.out.println(" Done.");
                    } catch (final IOException e) {
                        System.out.println(" Failed!");
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            System.out.println();
        }
        return true;
    }

    @Nullable
    private IService readService(final Scanner stdin) {
        ProviderServiceDirectory providerServiceDirectory = null;
        IService service = null;
        do {
            final String serviceCodePrompt = "Enter the code of the service to record: ";
            System.out.print(serviceCodePrompt);
            final int serviceCode = IOUtils.readUnsignedInt(stdin, () -> {
                System.out.println("Error! Please enter a positive numeric service code...");
                System.out.print(serviceCodePrompt);
            });
            if (providerServiceDirectory == null) {
                providerServiceDirectory = this.providerServiceDirectorySupplier.get();
                if (providerServiceDirectory == null) {
                    break;
                }
            }
            service = providerServiceDirectory.get(serviceCode);
            if (service != null) {
                break;
            }
            System.out.println("Failed to lookup service with code: " + serviceCode);
        } while (true);
        return service;
    }

    private boolean printProviderServiceDirectory(final List<String> args, final Scanner stdin) {
        try {
            final ProviderServiceDirectory providerServiceDirectory = this.providerServiceDirectorySupplier.get();
            if (providerServiceDirectory != null) {
                System.out.print("Printing provider service directory...");
                try (final PrintStream out = new PrintStream("provider_service_directory.txt")) {
                    providerServiceDirectory.print(out);
                    System.out.println(" Done.");
                } catch (final FileNotFoundException e) {
                    System.out.println(" Failed!");
                    e.printStackTrace();
                }
            }
        } finally {
            System.out.println();
        }
        return true;
    }

}
