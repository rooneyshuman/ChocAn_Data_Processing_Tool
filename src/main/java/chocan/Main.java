package chocan;

import java.util.Scanner;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        ProviderDB provider = new ProviderDB();
        ManagerDB manager = new ManagerDB();
        Scanner input = new Scanner(System.in);
        int menuOption;
        boolean again = true;
        String enter;

        out.println("\n\n\nWelcome to Chocoholics Anonymous!\n\n");

        do {
            out.println("\n-----------------------------------------");
            out.println("Main Menu");
            out.println("-----------------------------------------");
            out.println("1) Login to Provider Terminal");
            out.println("2) Login to Manager Terminal");
            out.println("3) Exit Program");

            out.print("Please select an option: ");

            while (!input.hasNextInt()) {
                out.print("Please enter a valid number: ");
                input.nextLine();
            }

            menuOption = input.nextInt();

            // Provider Menu
            if (menuOption == 1) {

                // Authenticate user.
                out.print("Please enter a provider ID: ");

                while (!input.hasNextInt()) {
                    out.print("Please enter a valid number: ");
                    input.nextLine();
                }

                int id = input.nextInt(); input.nextLine();

                //Error message if input is out of bounds
                while (id < 700000000 || id > 799999999) {
                    out.print("Please enter 9 digit. Provider ID's begin with '7': ");
                    id = input.nextInt();
                }

                if (provider.login(id)) {
                    out.println("\n" + provider.getName(id) + " has been logged in.");

                    do {
                        out.println("\n-----------------------------------------");
                        out.println("Provider Menu");
                        out.println("-----------------------------------------");
                        out.println("1) Create Service Record");
                        out.println("2) View Provider Directory");
                        out.println("3) Logout (Return to Main Menu)");

                        out.print("Please select an option: ");

                        while (!input.hasNextInt()) {
                            out.print("Please enter a valid number: ");
                            input.nextLine();
                        }

                        menuOption = input.nextInt();
                        input.nextLine();

                        switch (menuOption) {

                            case 1: // Create Service Record
                                provider.bill();

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 2: // View Provider Directory
                                provider.showDirectory();

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            default:
                                provider.logout();
                                again = false;
                                break;

                        }

                    } while (again);

                    again = true;
                    menuOption = 0;

                }
            }

            // Manager Menu
            if (menuOption == 2) {

                // Authenticate user.
                out.print("Please enter a manager ID: ");

                while (!input.hasNextInt()) {
                    out.print("Please enter a valid number: ");
                    input.nextLine();
                }

                int id = input.nextInt(); input.nextLine();

                //Error message if input is out of bounds
                while (id < 800000000 || id > 899999999) {
                    out.print("Please enter 9 digit. Manager ID's begin with '8': ");
                    id = input.nextInt();
                }

                if (manager.login(id)) {
                    out.println("\n" + manager.getName() + " has been logged in.");

                    do {
                        out.println("\n-----------------------------------------");
                        out.println("Manager Menu");
                        out.println("-----------------------------------------");
                        out.println("01) Add Member");
                        out.println("02) Add Provider");
                        out.println("03) Add Manager");
                        out.println("04) Activate/Suspend Member");
                        out.println("05) Activate/Suspend Provider");
                        out.println("06) Activate/Suspend Manager");
                        out.println("07) Delete Member");
                        out.println("08) Delete Provider");
                        out.println("09) Delete Manager");
                        out.println("10) Generate Member Reports");
                        out.println("11) Generate Provider Reports and EFT Records");
                        out.println("12) Generate Manager Report");
                        out.println("13) Logout (Return to Main Menu)");

                        out.print("Please select an option: ");

                        while (!input.hasNextInt()) {
                            out.print("Please enter a valid number: ");
                            input.nextLine();
                        }

                        menuOption = input.nextInt();
                        input.nextLine();

                        switch (menuOption) {

                            case 1: // Add Member
                                provider.addMember(manager.authorize());

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 2: // Add Provider
                                provider.addProvider(manager.authorize());

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 3: // Add Manager
                                manager.add();

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 4: // Activate/Suspend Member
                                provider.changeStatusMember(manager.authorize());

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 5: // Activate/Suspend Provider
                                provider.changeStatusProvider(manager.authorize());

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 6: // Activate/Suspend Manager
                                manager.changeStatus();

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 7: // Delete Member
                                provider.deleteMember(manager.authorize());

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 8: // Delete Provider
                                provider.deleteProvider(manager.authorize());

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 9: // Delete Manager
                                manager.delete();

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 10: // Generate Member Reports
                                manager.generateMemberReports();

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 11: // Generate Provider Reports
                                manager.generateProviderReports();

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            case 12: // Generate Manager Report
                                manager.generateManagerReport();

                                out.println("(Press enter to continue.)");
                                enter = input.nextLine();

                                break;

                            default:
                                manager.logout();
                                again = false;
                                break;

                        }
                    } while (again);

                    again = true;
                    menuOption = 0;
                }
            }

            if (menuOption == 3) again = false;

        } while (again && again());
    }

    private static boolean again() {

        Scanner input = new Scanner(System.in);
        String reply;
        out.print("Go back to main menu? (Yes/No) ");
        reply = input.next(); input.nextLine();

        return reply.startsWith("Y") || reply.startsWith("y");

    }

}