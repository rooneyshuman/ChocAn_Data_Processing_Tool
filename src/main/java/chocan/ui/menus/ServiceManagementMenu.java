package chocan.ui.menus;

import chocan.service.IService;
import chocan.service.ProviderServiceDirectory;
import chocan.service.Service;
import chocan.ui.IOUtils;
import chocan.ui.ParseUtils;
import chocan.ui.cli.Command;
import chocan.ui.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 */
public class ServiceManagementMenu extends CustomMenu {

    private static final NumberFormat MONEY_FORMAT = NumberFormat.getCurrencyInstance();

    /**
     *
     */
    public ServiceManagementMenu(final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier) {
        this.setHelpTitle("[Service Management Menu] Select an action:");
        this.add(new Command("list", "List services", "[service-code | service-name]", (final List<String> args, final Scanner stdin) -> {
            listServices(args, providerServiceDirectorySupplier);
            return true;
        }));
        this.add(new Command("add", "Add a new service", "", (final List<String> args, final Scanner stdin) -> {
            addService(stdin, providerServiceDirectorySupplier);
            return true;
        }));
        this.add(new Command("update", "Update an existing service", "[service-code | service-name]", (final List<String> args, final Scanner stdin) -> {
            updateService(args, stdin, providerServiceDirectorySupplier);
            return true;
        }));
        this.add(new Command("remove", "Remove a service", "[service-code | service-name]", (final List<String> args, final Scanner stdin) -> {
            removeService(args, stdin, providerServiceDirectorySupplier);
            return true;
        }));
        this.setExitCommand("back", "Go back to manager menu", "");
    }

    /**
     * Displays the information of a service in a simple human-readable format.
     * @param service The service to display.
     * @param header The header to display above the information.
     */
    private static void displayServiceWithHeader(final IService service, final String header) {
        System.out.println(header);
        service.display();
        System.out.println(StringUtils.repeat('-', header.length()));
    }

    private static ServiceSelectorFunction createSelector(final List<String> args,
                                                          final Supplier<ServiceSelectorFunction> onEmpty) {
        if (args.size() > 0) {
            final String partialNameOrCode = args.get(0);
            final Integer parsedServiceCode = ParseUtils.parseUnsignedInt(partialNameOrCode);
            if (parsedServiceCode != null) {
                return new ServiceSelectorFunction("No service with code: " + partialNameOrCode, null, p -> {
                    final Service service = p.get(parsedServiceCode);
                    return service != null ? Collections.singletonList(service) : Collections.emptyList();
                });
            } else {
                final String lower = partialNameOrCode.toLowerCase();
                return new ServiceSelectorFunction("No service with name: " + partialNameOrCode, "Ambiguous service name query: " + partialNameOrCode, p -> p.items()
                        .filter(s -> s.getName().toLowerCase().contains(lower))
                        .collect(Collectors.toList()));
            }
        } else {
            return onEmpty.get();
        }
    }

    /**
     * Lists (and filters) the services in the provider service directory.
     * @param args Arguments to specify the value to filter with.
     * @param providerServiceDirectorySupplier A function to load the provider service directory.
     */
    private static void listServices(final List<String> args,
                                     final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier) {
        try {
            final ServiceSelectorFunction selector = createSelector(args, () -> new ServiceSelectorFunction("No services.", null, p -> p.items().collect(Collectors.toList())));
            final ProviderServiceDirectory<Service> providerServiceDirectory = providerServiceDirectorySupplier.get();
            if (providerServiceDirectory != null) {
                final List<Service> services = selector.select(providerServiceDirectory);
                if (services.size() > 0) {
                    providerServiceDirectory.print(services.stream(), System.out);
                } else {
                    System.out.println(selector.fewError);
                }
            }
        } finally {
            System.out.println();
        }
    }

    /**
     * Prompts for a new service and adds to the provider service directory.
     * @param stdin A standard input scanner.
     * @param providerServiceDirectorySupplier A function to load the provider service directory.
     */
    private static void addService(final Scanner stdin,
                                   final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier) {
        try {
            final ProviderServiceDirectory<Service> providerServiceDirectory = providerServiceDirectorySupplier.get();
            if (providerServiceDirectory != null) {
                final int nextCode = providerServiceDirectory.nextCode();
                System.out.println("Code: " + nextCode);
                // Prompt for service information
                System.out.print("Name: ");
                final String name = stdin.nextLine();
                System.out.print("Fee: ");
                final BigDecimal fee = IOUtils.readMoney(stdin, () -> {
                    System.out.println("Unable to parse fee as decimal number. Please try again...");
                    System.out.print("Fee: ");
                });
                final Service newService = providerServiceDirectory.createService(nextCode, name, fee);
                System.out.println();
                // Confirm service information
                displayServiceWithHeader(newService, "--- New Service Information ---");
                if (IOUtils.confirm(stdin, "Is this service information correct?", false)) {
                    System.out.println();
                    // Add service to directory and save
                    providerServiceDirectory.add(newService);
                    System.out.println("Added new service: " + newService.getName() + " (" + newService.getCode() + ")");
                    System.out.print("Saving provider service directory...");
                    try {
                        providerServiceDirectory.save();
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
    }

    /**
     * Prompts for updated service information and saves it to the provider service directory.
     * @param args A list of arguments that were passed to this command.
     * @param stdin A standard input scanner.
     * @param providerServiceDirectorySupplier A function to load the provider service directory.
     */
    private static void updateService(final List<String> args, final Scanner stdin,
                                      final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier) {
        final ServiceSelectorFunction selector = createSelector(args, () -> {
            final String prompt = "Enter the code of the service to update: ";
            System.out.print(prompt);
            final int serviceCode = IOUtils.readUnsignedInt(stdin, () -> {
                System.out.println("Error! Please enter a positive numeric service code...");
                System.out.print(prompt);
            });
            return new ServiceSelectorFunction("No service with code: " + serviceCode, null, p -> {
                final Service service = p.get(serviceCode);
                return service != null ? Collections.singletonList(service) : Collections.emptyList();
            });
        });
        final ProviderServiceDirectory<Service> providerServiceDirectory = providerServiceDirectorySupplier.get();
        if (providerServiceDirectory != null) {
            final List<Service> services = selector.select(providerServiceDirectory);
            switch (services.size()) {
                case 0:
                    System.out.println(selector.fewError);
                    break;
                case 1:
                    final Service service = services.get(0);
                    final EditServiceMenu editServiceMenu = new EditServiceMenu(service, providerServiceDirectory);
                    if (args.size() > 1) {
                        editServiceMenu.process(args.subList(1, args.size()), stdin);
                    } else {
                        System.out.println();
                        editServiceMenu.run(stdin);
                        return;
                    }
                    break;
                default:
                    System.out.println(selector.manyError);
                    break;
            }
        }
        System.out.println();
    }

    /**
     * Prompts for service to remove from the provider service directory.
     * @param args A list of arguments that were passed to this command.
     * @param stdin A standard input scanner.
     * @param providerServiceDirectorySupplier A function to load the provider service directory.
     */
    private static void removeService(final List<String> args, final Scanner stdin,
                                      final Supplier<ProviderServiceDirectory<Service>> providerServiceDirectorySupplier) {
        try {
            final ServiceSelectorFunction selector = createSelector(args, () -> {
                final String prompt = "Enter the code of the service to remove: ";
                System.out.print(prompt);
                final int serviceCode = IOUtils.readUnsignedInt(stdin, () -> {
                    System.out.println("Error! Please enter a positive numeric service code...");
                    System.out.print(prompt);
                });
                return new ServiceSelectorFunction("No service with code: " + serviceCode, null, p -> {
                    final Service service = p.get(serviceCode);
                    return service != null ? Collections.singletonList(service) : Collections.emptyList();
                });
            });
            final ProviderServiceDirectory<Service> providerServiceDirectory = providerServiceDirectorySupplier.get();
            if (providerServiceDirectory != null) {
                final List<Service> services = selector.select(providerServiceDirectory);
                switch (services.size()) {
                    case 0:
                        System.out.println(selector.fewError);
                        break;
                    case 1:
                        final Service service = services.get(0);
                        displayServiceWithHeader(service, "--- Service Information ---");
                        System.out.println("Warning: Deleting a service may cause reports to contain incorrect information.");
                        if (IOUtils.confirm(stdin, "Are you sure you want to delete this service?", false)) {
                            System.out.println();
                            providerServiceDirectory.remove(service.getCode());
                            System.out.println("Removed service: " + service.getName() + " (" + service.getCode() + ")");
                            System.out.print("Saving provider service directory...");
                            try {
                                providerServiceDirectory.save();
                                System.out.println(" Done.");
                            } catch (final IOException e) {
                                System.out.println(" Failed!");
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        System.out.println(selector.manyError);
                        break;
                }
            }
        } finally {
            System.out.println();
        }
    }

    static class EditServiceMenu extends EditMenu<Service> {

        EditServiceMenu(final Service service, final ProviderServiceDirectory<Service> providerServiceDirectory) {
            super(service, Service::clone, Service::set, providerServiceDirectory, "provider service directory", "service management menu");
            this.setHelpTitle("[Edit Service Menu] Choose a field to edit:");
            this.add(new FieldCommand("fee", () -> MONEY_FORMAT.format(copy.getFee()), (final List<String> args, final Scanner stdin) -> {
                if (args.size() > 0) {
                    final BigDecimal parsedFee = ParseUtils.parseMoney(args.get(0));
                    if (parsedFee == null) {
                        System.out.println("Failed to parse fee as decimal number!");
                        System.out.println();
                        return true;
                    }
                    copy.setFee(parsedFee);
                } else {
                    System.out.print("New fee: ");
                    copy.setFee(IOUtils.readMoney(stdin, () -> {
                        System.out.println("Unable to parse fee as decimal number. Please try again...");
                        System.out.print("New fee: ");
                    }));
                    System.out.println();
                }
                return true;
            }));
            this.init();
        }

    }

    static class ServiceSelectorFunction {

        final String fewError;
        final String manyError;

        private final Function<ProviderServiceDirectory<Service>, List<Service>> function;

        ServiceSelectorFunction(final String fewError, final String manyError,
                                final Function<ProviderServiceDirectory<Service>, List<Service>> function) {
            this.fewError = fewError;
            this.manyError = manyError;
            this.function = function;
        }

        List<Service> select(final ProviderServiceDirectory<Service> providerServiceDirectory) {
            return this.function.apply(providerServiceDirectory);
        }

    }

}

