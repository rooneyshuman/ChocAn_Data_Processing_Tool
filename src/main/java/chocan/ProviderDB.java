package chocan;

import static java.lang.System.out;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ProviderDB {

    private Provider root;
    private ProviderDir directory;
    private MemberDB memberlist;
    private int providerCount = 0;
    private int providerID = 0;

    ProviderDB() {

        this.root = null;
        Load(); // Loads the list of providers.
        this.memberlist = new MemberDB();
        this.directory = new ProviderDir();
        this.directory.Read_txt();

    }

    // This checks the providers ID and logs them in.
    boolean Login(int id) {

        if (Login(id,this.root)) {
            this.providerID = id;
            return true;
        }
        else {
            out.print("Invalid member.");
            return false;
        }

    }

    // Traverses tree to check ID.
    private boolean Login(int id, Provider root) {

        if (root == null) return false;

        if (root.CheckID(id)) return true;

        return Login(id,root.GoLeft()) ? true : Login(id,root.GoRight());
    }

    // Log out provider.
    public void Logout() {

        this.providerID = 0;
        return;

    }

    // Add providers via public prompt. (For managers.)
    public void Add() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Please enter the provider's information.");
        out.println("-----------------------------------------");

        out.print("Name: ");
        String name = input.nextLine();

        while (name.length() < 1 || name.length() > 25) {
            out.print("Please enter 1-25 characters: ");
            name = input.nextLine();
        }

        out.print("Address: ");
        String address = input.nextLine();

        while (address.length() < 1 || address.length() > 25) {
            out.print("Please enter 1-25 characters: ");
            address = input.nextLine();
        }

        out.print("City: ");
        String city = input.nextLine();

        while (city.length() < 1 || city.length() > 14) {
            out.print("Please enter 1-14 characters: ");
            city = input.nextLine();
        }

        out.print("State: ");
        String state = input.nextLine();

        while (state.length() != 2) {
            out.print("Please enter 2 characters: ");
            state = input.nextLine();
        }

        out.print("Zip Code: ");
        String zip = input.nextLine();

        while (zip.length() < 1 || zip.length() > 6) {
            out.print("Please enter 1-6 characters: ");
            zip = input.nextLine();
        }

        this.root = Add(this.root,(700000 + providerCount),true,name,address,city,state,zip);
        out.println("Provider added.");
        Save();

    }

    // Add providers to tree.
    private Provider Add(Provider root, int id, boolean active, String name, String address, String city, String state, String zip) {

        // If no providers, create it.
        if (root == null) {

            root = new Provider(id,active,name,address,city,state,zip);
            ++providerCount;
            return root;

        }

        int direction = root.CompareID(id);

        // Traverse tree to find where to add.
        if (direction < 0)
            root.SetLeft(Add(root.GoLeft(),id,active,name,address,city,state,zip));
        else if (direction > 0)
            root.SetRight(Add(root.GoRight(),id,active,name,address,city,state,zip));

        return root;

    }

    // Activate or deactivate provider's account.
    public void ChangeStatus() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Changing status of a provider...");
        out.println("-----------------------------------------");

        out.print("Please enter a provider ID: ");

        while (!input.hasNextInt()) {

            out.print("Please enter a valid number: ");
            input.nextLine();

        }

        int id = input.nextInt();
        input.nextLine();

        ChangeStatus(this.root,id);
        Save();

    }

    // Traverse tree and change status of a provider.
    private void ChangeStatus(Provider root, int id) {

        // If empty, return.
        if (root == null) return;

        // If ID matches, change status.
        if (root.CompareID(id) == 0) {

            out.println("Provider status has changed.");
            root.ChangeStatus();
            return;

        }

        //Otherwise, traverse tree.
        if (root.CompareID(id) < 0) {
            ChangeStatus(root.GoLeft(), id);
        } else {
            ChangeStatus(root.GoRight(), id);
        }

    }

    // Loads the list of providers.
    private void Load() {

        try {
            File file = new File("src/main/java/chocan/db/providers.txt");
            Scanner read = new Scanner(file);
            read.useDelimiter("[:\\n]"); // Will ignore colons and new line character.

            int id;
            String name, address, city, state, zip;
            boolean active;

            while (read.hasNext()) {

                id = read.nextInt();
                active = read.nextBoolean();
                name = read.next();
                address = read.next();
                city = read.next();
                state = read.next();
                zip = read.next();

                this.root = Add(this.root,id,active,name,address,city,state,zip);
            }

            read.close();
        }

        catch (FileNotFoundException error) {
            error.printStackTrace();
        }

    }

    // Delete a provider via public prompt. (For managers.)
    public void Delete() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Deleting a provider...");
        out.println("-----------------------------------------");

        out.print("Please enter a provider ID: ");

        while (!input.hasNextInt()) {
            out.print("Please enter a valid number: ");
            input.nextLine();
        }

        int id = input.nextInt();
        input.nextLine();

        this.root = Delete(this.root,id);
        Save();

    }

    // Delete provider from tree.
    private Provider Delete(Provider root, int id) {

        // If no providers, return.
        if (root == null) return null;

        // If provider is found, get ready to delete.
        if (root.CompareID(id) == 0) {

            out.println("Provider deleted.");
            --providerCount;

            // If this is the only node, make it null and return.
            if (root.GoLeft() == null && root.GoRight() == null)
                return null;

            // If there is only a left child, make it the new root.
            if (root.GoLeft() != null && root.GoRight() == null)
                return root.GoLeft();

            // If there is only a right child, make it the new root.
            if (root.GoLeft() == null && root.GoRight() != null)
                return root.GoRight();

            // If both children exist, find inorder successor on right subtree.
            Provider temp = root.GoRight();
            Provider previous = null;
            while (temp.GoLeft() != null) {
                previous = temp;
                temp = temp.GoLeft();
            }

            // If inorder successor has a right child, that becomes the inorder successor.
            if (temp.GoRight() != null) {

                previous = temp;
                temp = temp.GoRight();
                if (temp != root.GoLeft())
                    temp.SetLeft(root.GoLeft()); // Make sure it doesn't link to itself.
                if (temp != root.GoRight())
                    temp.SetRight(root.GoRight()); // Make sure it doesn't link to itself.
                root = temp;
                previous.SetRight(null);
                return root;

            }

            // Otherwise, this one is the inorder successor.
            if (temp != root.GoLeft())
                temp.SetLeft(root.GoLeft()); // Make sure it doesn't link to itself.
            if (temp != root.GoRight())
                temp.SetRight(root.GoRight()); // Make sure it doesn't link to itself.
            root = temp;
            if (previous != null)
                previous.SetLeft(null);
            return root;

        }

        // If less than, go left.
        if (root.CompareID(id) < 0)
            root.SetLeft(Delete(root.GoLeft(), id));
        else
            root.SetRight(Delete(root.GoRight(), id));

        return root;

    }

    // Saves the list of providers.
    public void Save() {

        File file = new File("src/main/java/chocan/db/providers.txt");
        file.getParentFile().mkdirs();
        PrintWriter write = null;

        try {
            write = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Save(this.root,write);
        out.println("Provider list has been saved.");
        write.close();
    }

    // Traverse tree and save data.
    private void Save(Provider root, PrintWriter write) {

        // If empty, return.
        if (root == null) return;

        // Inorder traversal.
        Save(root.GoLeft(),write);
        root.Save(write);
        Save(root.GoRight(),write);
    }

    // Displays the list of providers.
    public void ShowProviders() {

        out.println("\n-----------------------------------------");
        out.println("Showing list of providers...");
        out.println("-----------------------------------------");

        if (this.root == null) {
            out.println("(No providers listed.)");
            return;
        }

        ShowProviders(this.root);
        out.println("Total number of providers: " + (providerCount - 1));

    }

    // Recursive display.
    private void ShowProviders(Provider root) {

        if (root == null) return;

        ShowProviders(root.GoLeft());
        root.Display();
        ShowProviders(root.GoRight());

    }

    public void Bill() {

        // Check for authorization.
        if (providerID == 0) {
            out.println("Must be logged in with a Provider ID.");
            return;
        }

        out.println("\n-----------------------------------------");
        out.println("Create Service Record");
        out.println("-----------------------------------------");

        Scanner ask = new Scanner(System.in);

        // Check if member ID is valid.
        out.print("Please enter member ID: ");
        while (!ask.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            ask.nextLine();
        }

        int memberID = ask.nextInt();
        // Call MemberDB function to check ID.
        // If invalid, return. Otherwise, continue.

        // Grab current date and time.
        DateFormat dateFormat1 = new SimpleDateFormat("MM-dd-YYYY HH:MM:ss", Locale.US);
        Date providerDate = new Date();
        String currentDate = dateFormat1.format(providerDate);

        // Prompt provider to enter service date.
        DateFormat dateFormat2 = new SimpleDateFormat("MM-dd-YYYY", Locale.US);
        out.print("Please enter the service date (MM-DD-YYYY)");
        Date memberDate = null;
        String serviceDate = null;

        while (memberDate == null) {

            serviceDate = ask.next();

            try {
                memberDate = dateFormat2.parse(serviceDate);
            } catch (ParseException error) {
                out.print("Invalid date.\nPlease enter a date (MM-DD-YYYY): ");
            }
        }

        // Display provider directory.
        directory.Display();

        int serviceCode;
        String serviceName = null;

        // Input service code.
        do {
            out.print("Please enter the service code: ");

            while (!ask.hasNextInt()) {
                out.print("Please enter a valid number: ");
                ask.nextLine(); ask.nextLine();
            }

            serviceCode = ask.nextInt();
            ask.nextLine();

            serviceName = directory.Find_code(serviceCode);

            if (serviceName == null)
                out.println("Invalid code. Please try again.");
            else {
                out.print("Is this the correct service? (Y/N): ");
                String answer = ask.nextLine();
                if (answer.startsWith("N") || answer.startsWith("n"))
                    serviceName = null;
            }

        } while (serviceName == null);

        // Prompt user to enter any comments about the service.
        out.print("Please enter any comments (optional): ");
        String serviceComments = ask.nextLine();

        /* Write record to disk.
            Current date and time (MM-DD-YYYY HH:MM:SS).
            Date service was provided (MM-DD-YYYY)
            Provider number (9 digits).
            Member number (9 digits).
            Service code (6 digits).
            Comments (100 characters). (optional)
        */
        out.print("Current date and time: " + currentDate + "\n");
        out.print("Date service was provided: " + serviceDate + "\n");
        out.print("Provider number: ");
        out.print("Member number: " + memberID + "\n");
        out.print("Service code: " + serviceCode + "\n");
        out.print("Comments: " + serviceComments + "\n");

        // Display fee to provider.

        /* Save member service record.
            Date of service (MM-DD-YYYY).
            Provider name (25 characters).
            Service name (20 characters).
         */

        /* Save provider service record.
            Date of service (MM-DD-YYYY).
            Date and time data were received by the computer (MM-DD-YYYY HH:MM:SS).
            Member name (25 characters).
            Member number (9 digits).
            Service code (6 digits).
            Fee to be paid (up to $999.99).
         */

    }

    public static void main(String[] args) {

        ProviderDB providerMenu = new ProviderDB();
        Scanner input = new Scanner(System.in);
        int menuOption;

        do {
            out.println("\n-----------------------------------------");
            out.println("Provider Test Interface");
            out.println("-----------------------------------------");
            out.println("1) Add Provider");
            out.println("2) Change Status of Provider");
            out.println("3) Delete Provider");
            out.println("4) Login");
            out.println("5) Show Provider List");
            out.println("6) Save Provider List");
            out.println("7) Load Provider List");
            out.println("8) Create Service Record");
            out.println("9) Logout");

            out.print("Please select an option: ");

            while (!input.hasNextInt()) {
                out.print("Please enter a valid number: ");
                input.nextLine();
            }

            menuOption = input.nextInt();

            switch (menuOption) {

                case 1: // Add Provider
                    providerMenu.Add();
                    break;

                case 2: // Change Status of Provider
                    providerMenu.ChangeStatus();
                    break;

                case 3: // Delete Provider
                    providerMenu.Delete();
                    break;

                case 4: // Login
                    out.print("Please enter a provider ID: ");

                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int id = input.nextInt();
                    if (providerMenu.Login(id))
                        out.println("Member ID is valid.");

                    break;

                case 5: // Show Provider List
                    providerMenu.ShowProviders();
                    break;

                case 6: // Save Provider List
                    providerMenu.Save();
                    break;

                case 7: // Load Provider List
                    providerMenu.Load();
                    break;

                case 8: // Create Service Record
                    providerMenu.Bill();
                    break;

                default:
                    providerMenu.Logout();
                    return;

            }
        } while (again());


    }

    private static boolean again() {

        Scanner input = new Scanner(System.in);
        String reply;
        out.print("Go back to menu? (Yes/No) ");
        reply = input.next(); input.nextLine();

        return reply.equalsIgnoreCase("yes");

    }

}
