package chocan.ui.menus;

import chocan.ui.IOUtils;
import chocan.ui.ParseUtils;
import chocan.ui.cli.Command;
import chocan.users.IUser;
import chocan.users.IUserDatabase;
import chocan.users.MemberUser;
import chocan.users.User;
import chocan.users.UserDatabase;
import chocan.ui.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 */
public class UserManagementMenu extends CustomMenu {

    /**
     *
     */
    public UserManagementMenu(final Supplier<UserDatabase<? extends MemberUser>> memberDatabaseSupplier,
                              final Supplier<UserDatabase<? extends User>> providerDatabaseSupplier,
                              final Supplier<UserDatabase<? extends User>> managerDatabaseSupplier) {
        this.setHelpTitle("[User Management Menu] Select an action:");
        this.add(new Command("list-members", "List and filter members in the database", "[member-id | member-name]", (final List<String> args, final Scanner stdin) -> {
            listUsers(args, "member", memberDatabaseSupplier);
            return true;
        }));
        this.add(new Command("list-providers", "List and filter providers in the database", "[provider-id | provider-name]", (final List<String> args, final Scanner stdin) -> {
            listUsers(args, "provider", providerDatabaseSupplier);
            return true;
        }));
        this.add(new Command("list-managers", "List and filter managers in the database", "[manager-id | manager-name]", (final List<String> args, final Scanner stdin) -> {
            listUsers(args, "manager", managerDatabaseSupplier);
            return true;
        }));
        this.add(new Command("add-member", "Add a new member", "", (final List<String> args, final Scanner stdin) -> {
            addUser(stdin, "member", memberDatabaseSupplier);
            return true;
        }));
        this.add(new Command("add-provider", "Add a new provider", "", (final List<String> args, final Scanner stdin) -> {
            addUser(stdin, "provider", providerDatabaseSupplier);
            return true;
        }));
        this.add(new Command("add-manager", "Add a new manager", "", (final List<String> args, final Scanner stdin) -> {
            addUser(stdin, "manager", managerDatabaseSupplier);
            return true;
        }));
        this.add(new Command("update-member", "Update an existing member", "[member-id [field [value]]]", (final List<String> args, final Scanner stdin) -> {
            updateUser(args, stdin, "member", memberDatabaseSupplier);
            return true;
        }));
        this.add(new Command("update-provider", "Update an existing provider", "[provider-id [field [value]]]", (final List<String> args, final Scanner stdin) -> {
            updateUser(args, stdin, "provider", providerDatabaseSupplier);
            return true;
        }));
        this.add(new Command("update-manager", "Update an existing manager", "[manager-id [field [value]]]", (final List<String> args, final Scanner stdin) -> {
            updateUser(args, stdin, "manager", managerDatabaseSupplier);
            return true;
        }));
        this.add(new Command("remove-member", "Remove a member", "[member-id]", (final List<String> args, final Scanner stdin) -> {
            removeUser(args, stdin, "member", memberDatabaseSupplier);
            return true;
        }));
        this.add(new Command("remove-provider", "Remove a provider", "[provider-id]", (final List<String> args, final Scanner stdin) -> {
            removeUser(args, stdin, "provider", providerDatabaseSupplier);
            return true;
        }));
        this.add(new Command("remove-manager", "Remove a manager", "[manager-id]", (final List<String> args, final Scanner stdin) -> {
            removeUser(args, stdin, "manager", managerDatabaseSupplier);
            return true;
        }));
        this.setExitCommand("back", "Go back to manager menu", "");
    }

    /**
     * Displays the information of a user in a simple human-readable format.
     * @param user The user to display.
     * @param header The header to display above the information.
     */
    private static void displayUserWithHeader(final IUser user, final String header) {
        System.out.println(header);
        user.display();
        System.out.println(StringUtils.repeat('-', header.length()));
    }

    /**
     * Lists (and filters) the users in the given user database.
     * @param args Arguments to specify the value to filter with.
     * @param userType The user type string (member, provider, manager).
     * @param userDatabaseFunction A function to load the user database to query.
     * @param <U> The generic user type.
     */
    private static <U extends IUser> void listUsers(final List<String> args,
                                                    final String userType,
                                                    final Supplier<? extends IUserDatabase<? extends U>> userDatabaseFunction) {
        try {
            final Predicate<U> filter;
            if (args.size() > 0) {
                final String partialNameOrID = args.get(0);
                final Integer parsedUserID = ParseUtils.parseUnsignedInt(partialNameOrID);
                if (parsedUserID != null) {
                    filter = u -> u.getID() == parsedUserID;
                } else {
                    final String lower = partialNameOrID.toLowerCase();
                    filter = u -> u.getName().toLowerCase().contains(lower);
                }
            } else {
                filter = u -> true;
            }
            final IUserDatabase<? extends U> userDatabase = userDatabaseFunction.get();
            if (userDatabase != null) {
                final List<U> users = userDatabase.items().filter(filter).collect(Collectors.toList());
                if (users.size() > 0) {
                    final String displayHeader = users.get(0).displayHeader();
                    final String horizontalLine = StringUtils.repeat('-', displayHeader.length());
                    System.out.println(horizontalLine);
                    System.out.println(displayHeader);
                    System.out.println(horizontalLine);
                    for (final U user : users) {
                        System.out.println(user.displayRow());
                    }
                } else {
                    System.out.println("No " + userType.toLowerCase() + "s.");
                }
            }
        } finally {
            System.out.println();
        }
    }

    /**
     * Prompts for a new user and adds to the given user database.
     * @param stdin A standard input scanner.
     * @param userType The user type string (member, provider, manager).
     * @param userDatabaseFunction A function to load the user database to add to.
     * @param <U> The generic user type.
     */
    private static <U extends User> void addUser(final Scanner stdin,
                                                 final String userType,
                                                 final Supplier<? extends UserDatabase<? extends U>> userDatabaseFunction) {
        try {
            final UserDatabase<? extends U> userDatabase = userDatabaseFunction.get();
            if (userDatabase != null && addNewUser(userDatabase, stdin, userType)) {
                System.out.print("Saving " + userType.toLowerCase() + " database...");
                try {
                    userDatabase.save();
                    System.out.println(" Done.");
                } catch (final IOException e) {
                    System.out.println(" Failed!");
                    e.printStackTrace();
                }
            }
        } finally {
            System.out.println();
        }
    }

    /**
     * Prompts for updated user information and saves it to the user database.
     * @param args A list of arguments that were passed to this command.
     * @param stdin A standard input scanner.
     * @param userType The user type string (member, provider, manager).
     * @param userDatabaseFunction A function to load the user database to update.
     * @param <U> The generic user type.
     */
    private static <U extends User> void updateUser(final List<String> args, final Scanner stdin,
                                                    final String userType,
                                                    final Supplier<? extends UserDatabase<? extends U>> userDatabaseFunction) {
        final String lowerUserType = userType.toLowerCase();
        final int userID;
        if (args.size() > 0) {
            final Integer parsedUserID = ParseUtils.parseUnsignedInt(args.get(0));
            if (parsedUserID == null) {
                System.out.println("Failed to parse " + lowerUserType + " ID!");
                System.out.println();
                return;
            }
            userID = parsedUserID;
        } else {
            final String prompt = "Enter the ID of the " + lowerUserType + " to update: ";
            System.out.print(prompt);
            userID = IOUtils.readUnsignedInt(stdin, () -> {
                System.out.println("Error! Please enter a positive numeric " + userType + " ID...");
                System.out.print(prompt);
            });
        }
        final UserDatabase<? extends U> userDatabase = userDatabaseFunction.get();
        if (userDatabase != null) {
            final U user = userDatabase.get(userID);
            if (user != null) {
                final EditUserMenu<U> editUserMenu = new EditUserMenu<>(lowerUserType, user, userDatabase);
                if (args.size() > 1) {
                    editUserMenu.process(args.subList(1, args.size()), stdin);
                } else {
                    System.out.println();
                    editUserMenu.run(stdin);
                    return;
                }
            } else {
                System.out.println("No " + lowerUserType + " with ID: " + userID);
            }
        }
        System.out.println();
    }

    /**
     * Prompts for user to remove from the user database.
     * @param args A list of arguments that were passed to this command.
     * @param stdin A standard input scanner.
     * @param userType The user type string (member, provider, manager).
     * @param userDatabaseFunction A function to load the user database to add to.
     * @param <U> The generic user type.
     */
    private static <U extends User> void removeUser(final List<String> args, final Scanner stdin,
                                                    final String userType,
                                                    final Supplier<? extends UserDatabase<? extends U>> userDatabaseFunction) {
        try {
            final String lowerUserType = userType.toLowerCase();
            final CharSequence titleUserType = StringUtils.toTitleCase(userType);
            final int userID;
            if (args.size() > 0) {
                final Integer parsedUserID = ParseUtils.parseUnsignedInt(args.get(0));
                if (parsedUserID == null) {
                    System.out.println("Failed to parse " + lowerUserType + " ID!");
                    return;
                }
                userID = parsedUserID;
            } else {
                final String prompt = "Enter the ID of the " + lowerUserType + " to remove: ";
                System.out.print(prompt);
                userID = IOUtils.readUnsignedInt(stdin, () -> {
                    System.out.println("Error! Please enter a positive numeric " + userType + " ID...");
                    System.out.print(prompt);
                });
            }
            final UserDatabase<? extends U> userDatabase = userDatabaseFunction.get();
            if (userDatabase != null) {
                final U user = userDatabase.get(userID);
                if (user != null) {
                    displayUserWithHeader(user, "--- " + titleUserType + " Information ---");
                    if (IOUtils.confirm(stdin, "Are you sure you want to delete this " + lowerUserType + "?", false)) {
                        System.out.println();
                        userDatabase.remove(userID);
                        System.out.println("Removed " + lowerUserType + ": " + user.getName() + " (" + user.getID() + ")");
                        System.out.print("Saving " + lowerUserType + " database...");
                        try {
                            userDatabase.save();
                            System.out.println(" Done.");
                        } catch (final IOException e) {
                            System.out.println(" Failed!");
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("No " + lowerUserType + " with ID: " + userID);
                }
            }
        } finally {
            System.out.println();
        }
    }
    
    /**
     * Prompts, creates, and confirms a new user.
     * @param id The ID of the new user.
     * @param userDatabase The user database to use to create a new user.
     * @param stdin A standard input scanner.
     * @param defaultOption The default confirmation option.
     * @param userType The user type string (member, provider, manager).
     * @param <U> The generic user type.
     * @return A newly created and confirmed user.
     */
    static <U extends User> U confirmNewUser(final int id, final IUserDatabase<U> userDatabase,
                                             final Scanner stdin, final boolean defaultOption,
                                             final String userType) {
        System.out.println("ID: " + id);
        final String name = IOUtils.promptMax(stdin, "Name: ", "Name of " + userType, IUser.MAX_NAME_LENGTH);
        final String address = IOUtils.promptMax(stdin, "Street address: ", "Address of " + userType, IUser.MAX_ADDRESS_LENGTH);
        final String city = IOUtils.promptMax(stdin, "City: ", "City of " + userType, IUser.MAX_CITY_LENGTH);
        final String state = IOUtils.promptMax(stdin, "State: ", "State of " + userType, IUser.MAX_STATE_LENGTH);
        final int zip = IOUtils.promptUnsignedInt(stdin, "Zip: ", "Zip of " + userType, IUser.ZIP_LENGTH, 
            "Unable to parse zip as an integer. Please try again...");
        final U newUser = userDatabase.createUser(id, name, address, city, state, zip);
        System.out.println();
        displayUserWithHeader(newUser, "--- New " + StringUtils.toTitleCase(userType) + " Information ---");
        if (IOUtils.confirm(stdin, "Is this " + userType.toLowerCase() + " information correct?", defaultOption)) {
            System.out.println();
            return newUser;
        }
        return null;
    }

    private static <U extends User> boolean addNewUser(final UserDatabase<U> userDatabase,
                                                       final Scanner stdin,
                                                       final String userType) {
        final U confirmedUser = confirmNewUser(userDatabase.nextID(), userDatabase, stdin, false, userType);
        if (confirmedUser != null) {
            userDatabase.add(confirmedUser);
            System.out.println("Added new " + userType.toLowerCase() + ": " + confirmedUser.getName() + " (" + confirmedUser.getID() + ")");
            return true;
        }
        return false;
    }

    static class EditUserMenu<U extends User> extends EditMenu<User> {

        EditUserMenu(final String lowerUserType, final U user, final UserDatabase<? extends U> userDatabase) {
            super(user, User::clone, User::set, userDatabase, lowerUserType + " database", "user management menu");
            this.setHelpTitle("[Edit User Menu] Choose a field to edit:");
            this.add(new FieldCommand("name", this.copy::getName, (final List<String> args, final Scanner stdin) -> {
                if (args.size() > 0) {
                    final String name = args.get(0);
                    if (name.length() <= IUser.MAX_NAME_LENGTH) {
                        this.copy.setName(name);
                    } else {
                        System.out.println("Error: Name of " + lowerUserType + " is longer than " + IUser.MAX_NAME_LENGTH + "!");
                        System.out.println();
                    }
                } else if (stdin.hasNextLine()) {
                    this.copy.setName(IOUtils.promptMax(stdin, "New name: ", "Name of " + lowerUserType, IUser.MAX_NAME_LENGTH));
                    System.out.println();
                }
                return true;
            }));
            this.add(new FieldCommand("address", this.copy::getAddress, (final List<String> args, final Scanner stdin) -> {
                if (args.size() > 0) {
                    final String address = args.get(0);
                    if (address.length() <= IUser.MAX_ADDRESS_LENGTH) {
                        this.copy.setAddress(address);
                    } else {
                        System.out.println("Error: Address of " + lowerUserType + " is longer than " + IUser.MAX_ADDRESS_LENGTH + "!");
                        System.out.println();
                    }
                } else if (stdin.hasNextLine()) {
                    this.copy.setName(IOUtils.promptMax(stdin, "New address: ", "Address of " + lowerUserType, IUser.MAX_ADDRESS_LENGTH));
                    System.out.println();
                }
                return true;
            }));
            this.add(new FieldCommand("city", this.copy::getCity, (final List<String> args, final Scanner stdin) -> {
                if (args.size() > 0) {
                    final String city = args.get(0);
                    if (city.length() <= IUser.MAX_CITY_LENGTH) {
                        this.copy.setCity(city);
                    } else {
                        System.out.println("Error: City of " + lowerUserType + " is longer than " + IUser.MAX_CITY_LENGTH + "!");
                        System.out.println();
                    }
                } else if (stdin.hasNextLine()) {
                    this.copy.setCity(IOUtils.promptMax(stdin, "New city: ", "City of " + lowerUserType, IUser.MAX_CITY_LENGTH));
                    System.out.println();
                }
                return true;
            }));
            this.add(new FieldCommand("state", this.copy::getState, (final List<String> args, final Scanner stdin) -> {
                if (args.size() > 0) {
                    final String state = args.get(0);
                    if (state.length() <= IUser.MAX_STATE_LENGTH) {
                        this.copy.setState(state);
                    } else {
                        System.out.println("Error: State of " + lowerUserType + " is longer than " + IUser.MAX_STATE_LENGTH + "!");
                        System.out.println();
                    }
                } else if (stdin.hasNextLine()) {
                    this.copy.setState(IOUtils.promptMax(stdin, "New state: ", "State of " + lowerUserType, IUser.MAX_STATE_LENGTH));
                    System.out.println();
                }
                return true;
            }));
            this.add(new FieldCommand("zip", this.copy::getZip, (final List<String> args, final Scanner stdin) -> {
                if (args.size() > 0) {
                    final String zip = args.get(0);
                    if (zip.length() != IUser.ZIP_LENGTH) {
                        System.out.println("Error: Zip of " + lowerUserType + " is not " + IUser.ZIP_LENGTH + " digits!");
                        System.out.println();
                        return true;
                    }
                    final Integer parsedZip = ParseUtils.parseUnsignedInt(zip);
                    if (parsedZip == null) {
                        System.out.println("Failed to parse zip as integer!");
                        System.out.println();
                        return true;
                    }
                    this.copy.setZip(parsedZip);
                } else {
                    this.copy.setZip(IOUtils.promptUnsignedInt(stdin, "New zip: ", "Zip of " + lowerUserType, IUser.ZIP_LENGTH, 
                        "Unable to parse zip as an integer. Please try again..."));
                    System.out.println();
                }
                return true;
            }));
            this.init();
        }

    }


}