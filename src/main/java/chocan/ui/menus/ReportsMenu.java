package chocan.ui.menus;

import chocan.report.ReportGenerator;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 *
 */
public class ReportsMenu extends CustomMenu {

    private final Supplier<UserDatabase<? extends MemberUser>> memberDatabaseSupplier;
    private final Supplier<UserDatabase<? extends User>> providerDatabaseSupplier;
    private final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier;
    private final Supplier<ServiceRecordDatabase<ServiceRecord>> serviceRecordDatabaseSupplier;
    private final ReportGenerator reportGenerator;

    /**
     *
     */
    public ReportsMenu(final Supplier<UserDatabase<? extends MemberUser>> memberDatabaseSupplier,
                       final Supplier<UserDatabase<? extends User>> providerDatabaseSupplier,
                       final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier,
                       final Supplier<ServiceRecordDatabase<ServiceRecord>> serviceRecordDatabaseSupplier,
                       final ReportGenerator reportGenerator) {
        this.memberDatabaseSupplier = memberDatabaseSupplier;
        this.providerDatabaseSupplier = providerDatabaseSupplier;
        this.providerServiceDirectorySupplier = providerServiceDirectorySupplier;
        this.serviceRecordDatabaseSupplier = serviceRecordDatabaseSupplier;
        this.reportGenerator = reportGenerator;
        // Configure report menu
        this.setHelpTitle("[Report Menu] Select a report to generate:");
        this.add(new Command("member", "Generate a member report", "[member-id]", this::memberReport));
        this.add(new Command("provider", "Generate a provider report", "[provider-id]", this::providerReport));
        this.add(new Command("manager", "Generate a manager summary report", "", this::managerReport));
        this.add(new Command("eft", "Generate an EFT record for all providers", "[provider-id]", this::eftRecord));
        this.setExitCommand("back", "Go back to manager menu", "");
    }

    private boolean memberReport(final List<String> args, final Scanner stdin) {
        final int memberID;
        if (args.size() > 0) {
            final Integer parsedMemberID = ParseUtils.parseUnsignedInt(args.get(0));
            if (parsedMemberID == null) {
                System.out.println("Failed to parse member ID!");
                return true;
            }
            memberID = parsedMemberID;
        } else {
            System.out.print("Enter the member ID: ");
            memberID = IOUtils.readUnsignedInt(stdin, () -> {
                System.out.println("Error! Please enter a positive numeric member ID...");
                System.out.print("Enter the member ID: ");
            });
        }
        final UserDatabase<? extends MemberUser> memberDatabase = this.memberDatabaseSupplier.get();
        if (memberDatabase == null) {
            return true;
        }
        final MemberUser member = memberDatabase.get(memberID);
        if (member == null) {
            System.out.println("No member with ID: " + memberID);
            return true;
        }
        final UserDatabase<? extends User> providerDatabase = this.providerDatabaseSupplier.get();
        if (providerDatabase == null) {
            return true;
        }
        final ProviderServiceDirectory providerServiceDirectory = this.providerServiceDirectorySupplier.get();
        if (providerServiceDirectory == null) {
            return true;
        }
        final ServiceRecordDatabase<ServiceRecord> serviceRecordDatabase = this.serviceRecordDatabaseSupplier.get();
        if (serviceRecordDatabase == null) {
            return true;
        }
        System.out.print("Generating member report...");
        final String reportOutput = this.reportGenerator.member(member, providerDatabase, providerServiceDirectory, serviceRecordDatabase);
        System.out.println(" Done.");
        try {
            System.out.print("Writing member report to file...");
            Files.write(Paths.get("member-" + memberID + "-report.txt"), reportOutput.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            System.out.println(" Done.");
        } catch (final IOException e) {
            System.out.println(" Failed!");
            e.printStackTrace();
        }
        return true;
    }

    private boolean providerReport(final List<String> args, final Scanner stdin) {
        final int providerID;
        if (args.size() > 0) {
            final Integer parsedProviderID = ParseUtils.parseUnsignedInt(args.get(0));
            if (parsedProviderID == null) {
                System.out.println("Failed to parse provider ID!");
                return true;
            }
            providerID = parsedProviderID;
        } else {
            System.out.print("Enter the provider ID: ");
            providerID = IOUtils.readUnsignedInt(stdin, () -> {
                System.out.println("Error! Please enter a positive numeric provider ID...");
                System.out.print("Enter the provider ID: ");
            });
        }
        final UserDatabase<? extends User> providerDatabase = this.providerDatabaseSupplier.get();
        if (providerDatabase == null) {
            return true;
        }
        final User provider = providerDatabase.get(providerID);
        if (provider == null) {
            System.out.println("No provider with ID: " + providerID);
            return true;
        }
        final UserDatabase<? extends MemberUser> memberDatabase = this.memberDatabaseSupplier.get();
        if (memberDatabase == null) {
            return true;
        }
        final ProviderServiceDirectory providerServiceDirectory = this.providerServiceDirectorySupplier.get();
        if (providerServiceDirectory == null) {
            return true;
        }
        final ServiceRecordDatabase<ServiceRecord> serviceRecordDatabase = this.serviceRecordDatabaseSupplier.get();
        if (serviceRecordDatabase == null) {
            return true;
        }
        System.out.print("Generating provider report...");
        final String reportOutput = this.reportGenerator.provider(provider, memberDatabase, providerServiceDirectory, serviceRecordDatabase);
        System.out.println(" Done.");
        try {
            System.out.print("Writing provider report to file...");
            Files.write(Paths.get("provider-" + providerID + "-report.txt"), reportOutput.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            System.out.println(" Done.");
        } catch (final IOException e) {
            System.out.println(" Failed!");
            e.printStackTrace();
        }
        return true;
    }

    private boolean managerReport(final List<String> args, final Scanner stdin) {
        final UserDatabase<? extends User> providerDatabase = this.providerDatabaseSupplier.get();
        if (providerDatabase == null) {
            return true;
        }
        final ProviderServiceDirectory providerServiceDirectory = this.providerServiceDirectorySupplier.get();
        if (providerServiceDirectory == null) {
            return true;
        }
        final ServiceRecordDatabase<ServiceRecord> serviceRecordDatabase = this.serviceRecordDatabaseSupplier.get();
        if (serviceRecordDatabase == null) {
            return true;
        }
        System.out.print("Generating manager report...");
        final String reportOutput = this.reportGenerator.manager(providerDatabase, providerServiceDirectory, serviceRecordDatabase);
        System.out.println(" Done.");
        try {
            System.out.print("Writing manager report to file...");
            Files.write(Paths.get("manager-report.txt"), reportOutput.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            System.out.println(" Done.");
        } catch (final IOException e) {
            System.out.println(" Failed!");
            e.printStackTrace();
        }
        return true;
    }

    private boolean eftRecord(final List<String> args, final Scanner stdin) {
        final UserDatabase<? extends User> providerDatabase = this.providerDatabaseSupplier.get();
        if (providerDatabase == null) {
            return true;
        }
        final ProviderServiceDirectory providerServiceDirectory = this.providerServiceDirectorySupplier.get();
        if (providerServiceDirectory == null) {
            return true;
        }
        final ServiceRecordDatabase<ServiceRecord> serviceRecordDatabase = this.serviceRecordDatabaseSupplier.get();
        if (serviceRecordDatabase == null) {
            return true;
        }
        final Supplier<String> recordOutputSupplier;
        final String recordsText;
        final String recordFile;
        if (args.size() > 0) {
            final Integer parsedProviderID = ParseUtils.parseUnsignedInt(args.get(0));
            if (parsedProviderID == null) {
                System.out.println("Failed to parse provider ID!");
                return true;
            }
            final User provider = providerDatabase.get(parsedProviderID);
            if (provider == null) {
                System.out.println("No provider with ID: " + parsedProviderID);
                return true;
            }
            recordOutputSupplier = () -> this.reportGenerator.eftRecord(provider, providerDatabase, providerServiceDirectory, serviceRecordDatabase);
            recordsText = "record";
            recordFile = "provider-" + parsedProviderID + "-eft-record.txt";
        } else {
            recordOutputSupplier = () -> this.reportGenerator.eftRecords(providerDatabase, providerServiceDirectory, serviceRecordDatabase);
            recordsText = "records";
            recordFile = "eft-records.txt";
        }
        System.out.print("Generating EFT " + recordsText + "...");
        final String recordOutput = recordOutputSupplier.get();
        System.out.println(" Done.");
        try {
            System.out.print("Writing EFT " + recordsText + " to file...");
            Files.write(Paths.get(recordFile), recordOutput.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            System.out.println(" Done.");
        } catch (final IOException e) {
            System.out.println(" Failed!");
            e.printStackTrace();
        }
        return true;
    }

}
