package chocan;

import static java.lang.System.out;

import java.io.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ProviderDB {

    private Provider root;
    private ProviderDir directory;
    private MemberDB memberList;
    private int providerCount = 0;
    private int providerID = 0;

    ProviderDB() {

        this.root = null;
        load(); // Loads the list of providers.
        this.memberList = new MemberDB();
        this.directory = new ProviderDir();
        this.directory.Read_txt();

    }

    // This checks the providers ID and logs them in.
    public boolean login(int id) {

        if (login(id,this.root)) {
            this.providerID = id;
            return true;
        }
        else {
            out.print("Invalid member.\n");
            return false;
        }

    }

    // Traverses tree to check ID.
    private boolean login(int id, Provider root) {

        if (root == null) return false;

        if (root.checkID(id) == 0) return true;

        return login(id, root.goLeft()) || login(id, root.goRight());
    }

    // Log out provider.
    public void logout() {

        this.providerID = 0;

    }

    // Add providers via public prompt. (For managers.)
    public void add() {

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

        this.root = add(this.root,(700000 + providerCount),true,name,address,city,state,zip);
        out.println("Provider added.");
        save();

    }

    // Add providers to tree.
    private Provider add(Provider root, int id, boolean active, String name, String address, String city, String state, String zip) {

        // If no providers, create it.
        if (root == null) {

            root = new Provider(id,active,name,address,city,state,zip);
            ++providerCount;
            return root;

        }

        int direction = root.compareID(id);

        // Traverse tree to find where to add.
        if (direction < 0)
            root.setLeft(add(root.goLeft(),id,active,name,address,city,state,zip));
        else if (direction > 0)
            root.setRight(add(root.goRight(),id,active,name,address,city,state,zip));

        return root;

    }

    // Activate or deactivate provider's account.
    public void changeStatus() {

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

        changeStatus(this.root,id);
        save();

    }

    // Traverse tree and change status of a provider.
    private void changeStatus(Provider root, int id) {

        // If empty, return.
        if (root == null) return;

        // If ID matches, change status.
        if (root.compareID(id) == 0) {

            out.println("Provider status has changed.");
            root.changeStatus();
            return;

        }

        //Otherwise, traverse tree.
        if (root.compareID(id) < 0) {
            changeStatus(root.goLeft(), id);
        } else {
            changeStatus(root.goRight(), id);
        }

    }

    // Loads the list of providers.
    private void load() {

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

                this.root = add(this.root,id,active,name,address,city,state,zip);
            }

            read.close();
        }

        catch (FileNotFoundException error) {
            error.printStackTrace();
        }

    }

    // Delete a provider via public prompt. (For managers.)
    public void delete() {

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

        this.root = delete(this.root,id);
        save();

    }

    // Delete provider from tree.
    private Provider delete(Provider root, int id) {

        // If no providers, return.
        if (root == null) return null;

        // If provider is found, get ready to delete.
        if (root.compareID(id) == 0) {

            out.println("Provider deleted.");
            --providerCount;

            // If this is the only node, make it null and return.
            if (root.goLeft() == null && root.goRight() == null)
                return null;

            // If there is only a left child, make it the new root.
            if (root.goLeft() != null && root.goRight() == null)
                return root.goLeft();

            // If there is only a right child, make it the new root.
            if (root.goLeft() == null && root.goRight() != null)
                return root.goRight();

            // If both children exist, find inorder successor on right subtree.
            Provider temp = root.goRight();
            Provider previous = null;
            while (temp.goLeft() != null) {
                previous = temp;
                temp = temp.goLeft();
            }

            // If inorder successor has a right child, that becomes the inorder successor.
            if (temp.goRight() != null) {

                previous = temp;
                temp = temp.goRight();
                if (temp != root.goLeft())
                    temp.setLeft(root.goLeft()); // Make sure it doesn't link to itself.
                if (temp != root.goRight())
                    temp.setRight(root.goRight()); // Make sure it doesn't link to itself.
                root = temp;
                previous.setRight(null);
                return root;

            }

            // Otherwise, this one is the inorder successor.
            if (temp != root.goLeft())
                temp.setLeft(root.goLeft()); // Make sure it doesn't link to itself.
            if (temp != root.goRight())
                temp.setRight(root.goRight()); // Make sure it doesn't link to itself.
            root = temp;
            if (previous != null)
                previous.setLeft(null);
            return root;

        }

        // If less than, go left.
        if (root.compareID(id) < 0)
            root.setLeft(delete(root.goLeft(), id));
        else
            root.setRight(delete(root.goRight(), id));

        return root;

    }

    // Saves the list of providers.
    public void save() {

        File file = new File("src/main/java/chocan/db/providers.txt");
        file.getParentFile().mkdirs();
        PrintWriter write = null;

        try {
            write = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        save(this.root,write);
        out.println("Provider list has been saved.");
        write.close();
    }

    // Traverse tree and save data.
    private void save(Provider root, PrintWriter write) {

        // If empty, return.
        if (root == null) return;

        // Inorder traversal.
        save(root.goLeft(),write);
        root.save(write);
        save(root.goRight(),write);
    }

    // Traverse tree and save provider service info.
    private void saveProviderService(Provider root, int id, PrintWriter write) {

        // If empty, return.
        if (root == null) return;

        // If match, then write data.
        if (root.compareID(id) == 0) {
            root.saveServiceRecord(write);
            return;
        }

        // Inorder traversal.
        saveProviderService(root.goLeft(),id,write);
        saveProviderService(root.goRight(),id,write);
    }

    // Displays the list of providers.
    public void showProviders() {

        out.println("\n-----------------------------------------");
        out.println("Showing list of providers...");
        out.println("-----------------------------------------");

        if (this.root == null) {
            out.println("(No providers listed.)");
            return;
        }

        showProviders(this.root);
        out.println("Total number of providers: " + providerCount);

    }

    private String getName(int id) {
        return getName(this.root,id);
    }

    private String getName(Provider root, int id) {

        if (root == null) return null;

        if (root.compareID(id) == 0) return root.getName();

        String result = getName(root.goLeft(),id);

        return (result != null) ? result : getName(root.goRight(),id);

    }

    // Recursive display.
    private void showProviders(Provider root) {

        if (root == null) return;

        showProviders(root.goLeft());
        root.display();
        showProviders(root.goRight());

    }

    public void bill() {

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
            out.print("Please enter a valid number: ");
            ask.nextLine();
        }

        int memberID = ask.nextInt();
        if (!memberList.checkID(memberID)) {
            out.println("Invalid number.");
            return;
        }

        // Check member status.
        if (!memberList.checkStatus(memberID)) {
            out.println("Member suspended.");
            return;
        }

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
        String serviceName;

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
                out.println("Service Name: " + serviceName);
                out.print("Is this the correct service? (Y/N): ");
                String answer = ask.nextLine();
                if (answer.startsWith("N") || answer.startsWith("n"))
                    serviceName = null;
            }

        } while (serviceName == null);

        // Prompt user to enter any comments about the service.
        out.print("Please enter any comments (optional): ");
        String serviceComments = ask.nextLine();

        // Write service record to disk.
        try {
            File file = new File("src/main/java/chocan/db/services.txt");
            file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter write = new PrintWriter(bw);

            write.print(currentDate + "|");
            write.print(serviceDate + "|");
            write.print(providerID + "|");
            write.print(memberID + "|");
            write.print(serviceCode + "|");
            write.println(serviceComments);
            write.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        out.print("Current date and time: " + currentDate + "\n");
        out.print("Date service was provided: " + serviceDate + "\n");
        out.print("Provider number: " + providerID + "\n");
        out.print("Member number: " + memberID + "\n");
        out.print("Service code: " + serviceCode + "\n");
        out.print("Comments: " + serviceComments + "\n");

        // Display fee to provider.
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        out.println("Service Fee: " + nf.format(directory.getFee(serviceCode)));

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

        String providerName = getName(providerID);

        // Write provider service record to disk.
        try {
            File file = new File("src/main/java/chocan/db/providers/" + providerName + ".txt");
            file.getParentFile().mkdirs();
            boolean fileExists = file.exists();

            if (!fileExists) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter write = new PrintWriter(bw);

            // If new file, create first line with provider info.
            if (!fileExists) saveProviderService(this.root, providerID, write);

            write.print(currentDate + "|");
            write.print(serviceDate + "|");
            write.print(providerID + "|");
            write.print(memberID + "|");
            write.print(serviceCode + "|");
            write.println(serviceComments);
            write.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    providerMenu.add();
                    break;

                case 2: // Change Status of Provider
                    providerMenu.changeStatus();
                    break;

                case 3: // Delete Provider
                    providerMenu.delete();
                    break;

                case 4: // Login
                    out.print("Please enter a provider ID: ");

                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int id = input.nextInt();
                    if (providerMenu.login(id))
                        out.println("Provider has been logged in.");

                    break;

                case 5: // Show Provider List
                    providerMenu.showProviders();
                    break;

                case 6: // Save Provider List
                    providerMenu.save();
                    break;

                case 7: // Load Provider List
                    providerMenu.load();
                    break;

                case 8: // Create Service Record
                    providerMenu.bill();
                    break;

                default:
                    providerMenu.logout();
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
