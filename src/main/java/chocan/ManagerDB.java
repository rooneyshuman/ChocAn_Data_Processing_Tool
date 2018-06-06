package chocan;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.System.out;

public class ManagerDB {

    private Manager root;
    private int managerCount = 0;
    private int managerID = 0;

    ManagerDB() {

        this.root = null;
        load(); // Loads the list of managers

    }

    // This checks the manager's ID and logs them in.
    public boolean login(int id) {

        if (login(id, this.root)) {
            this.managerID = id;
            return true;
        }
        else {
            out.print("Invalid manager number.\n");
            return false;
        }

    }

    // Traverses tree to check ID.
    private boolean login(int id, Manager root) {

        if (root == null) return false;

        if (root.checkID(id) == 0) return true;

        return login(id, root.goLeft()) || login(id, root.goRight());
    }

    // Log out manager.
    public void logout() {

        this.managerID = 0;

    }

    // Add managers via public prompt.
    public void add() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Please enter the manager's information.");
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

        this.root = add(this.root,(800000 + managerCount),true,name,address,city,state,zip);
        out.println("Manager added.");
        save();

    }

    // Add manager to tree.
    private Manager add(Manager root, int id, boolean active, String name, String address, String city, String state, String zip) {

        // If no managers, create it.
        if (root == null) {

            root = new Manager(id,active,name,address,city,state,zip);
            ++managerCount;
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

    // Activate or deactivate manager's account.
    public void changeStatus() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Changing status of a manager...");
        out.println("-----------------------------------------");

        out.print("Please enter a manager ID: ");

        while (!input.hasNextInt()) {

            out.print("Please enter a valid number: ");
            input.nextLine();

        }

        int id = input.nextInt();
        input.nextLine();

        changeStatus(this.root,id);
        save();

    }

    // Traverse tree and change status of a manager.
    private void changeStatus(Manager root, int id) {

        // If empty, return.
        if (root == null) return;

        // If ID matches, change status.
        if (root.compareID(id) == 0) {

            out.println("Manager status has changed.");
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

    // Loads the list of managers.
    private void load() {

        try {
            File file = new File("src/main/java/chocan/db/managers.txt");
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

    // Delete a manager via public prompt.
    public void delete() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Deleting a manager...");
        out.println("-----------------------------------------");

        out.print("Please enter a manager ID: ");

        while (!input.hasNextInt()) {
            out.print("Please enter a valid number: ");
            input.nextLine();
        }

        int id = input.nextInt();
        input.nextLine();

        this.root = delete(this.root,id);
        save();

    }

    // Delete manager from tree.
    private Manager delete(Manager root, int id) {

        // If no managers, return.
        if (root == null) return null;

        // If manager is found, get ready to delete.
        if (root.compareID(id) == 0) {

            out.println("Manager deleted.");
            --managerCount;

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
            Manager temp = root.goRight();
            Manager previous = null;
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

    // Saves the list of managers.
    public void save() {

        File file = new File("src/main/java/chocan/db/managers.txt");
        file.getParentFile().mkdirs();
        PrintWriter write = null;

        try {
            write = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        save(this.root,write);
        out.println("Manager list has been saved.");
        write.close();
    }

    // Traverse tree and save data.
    private void save(Manager root, PrintWriter write) {

        // If empty, return.
        if (root == null) return;

        // Inorder traversal.
        save(root.goLeft(),write);
        root.save(write);
        save(root.goRight(),write);
    }

    // Displays the list of managers.
    public void showManagers() {

        out.println("\n-----------------------------------------");
        out.println("Showing list of managers...");
        out.println("-----------------------------------------");

        if (this.root == null) {
            out.println("(No managers listed.)");
            return;
        }

        showManagers(this.root);
        out.println("Total number of managers: " + managerCount);

    }

    // Recursive display.
    private void showManagers(Manager root) {

        if (root == null) return;

        showManagers(root.goLeft());
        root.display();
        showManagers(root.goRight());

    }

    // Generate Member Reports
    public void generateMemberReports() {

        try(Stream<Path> paths = Files.walk(Paths.get("src/main/java/chocan/db/Members"))) {

            paths.forEach(filePath -> {

                if (Files.isRegularFile(filePath)) {

                    try {
                        // read the file

                        // save the report
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Generate Provider Reports
    public void generateProviderReports() {

        int consultations = 0;
        double fee, totalFee = 0;

        File file = new File("src/main/java/chocan/db/providers/");
        File[] files = file.listFiles();
        Scanner read;
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        for (File f: files) {
            try {
                read = new Scanner(f);
                read.useDelimiter("[|\\n]");

                File report = new File("src/main/java/chocan/report/" + f.getName());
                report.getParentFile().mkdirs();
                PrintWriter write = null;

                try {
                    write = new PrintWriter(report);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                String providerName = read.next();
                int providerID = read.nextInt();
                String providerAddress = read.next();
                String providerCity = read.next();
                String providerState = read.next();
                int providerZip = read.nextInt();

                write.println("Provider Name: " + providerName);
                write.println("Provider ID: " + providerID);
                write.println("Provider Address: " + providerAddress);
                write.println("Provider City: " + providerCity);
                write.println("Provider State: " + providerState);
                write.println("Provider Zip Code: " + providerZip);
                write.println("-----------------------------------------");

                out.println("Provider Name: " + providerName);
                out.println("Provider ID: " + providerID);
                out.println("Provider Address: " + providerAddress);
                out.println("Provider City: " + providerCity);
                out.println("Provider State: " + providerState);
                out.println("Provider Zip Code: " + providerZip);
                out.println("-----------------------------------------");

                String serviceDate = read.next();
                String dateReceived = read.next();
                String memberName = read.next();
                int memberID = read.nextInt();
                int serviceCode = read.nextInt();

                while(read.hasNext()) {
                    write.println("Service Date: " + serviceDate);
                    write.println("Date Received: " + dateReceived);
                    write.println("Member Name: " + memberName);
                    write.println("Member ID: " + memberID);
                    write.println("Service Code: " + serviceCode);

                    out.println("Service Date: " + serviceDate);
                    out.println("Date Received: " + dateReceived);
                    out.println("Member Name: " + memberName);
                    out.println("Member ID: " + memberID);
                    out.println("Service Code: " + serviceCode);

                    fee = read.nextDouble();
                    totalFee += fee;

                    write.println("Fee: " + nf.format(fee));
                    write.println("-----------------------------------------");

                    out.println("Fee: " + nf.format(fee));
                    out.println("-----------------------------------------");
                    ++consultations;
                }

                write.println("Total Consultations: " + consultations);
                write.println("Total Fee: " + nf.format(totalFee));

                out.println("Total Consultations: " + consultations);
                out.println("Total Fee: " + nf.format(totalFee));

                write.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    // Generate Manager Report
    public void generateManagerReport() {

    }

    public static void main(String[] args) {

        ManagerDB managerMenu = new ManagerDB();
        Scanner input = new Scanner(System.in);
        int menuOption;

        do {
            out.println("\n-----------------------------------------");
            out.println("Manager Test Interface");
            out.println("-----------------------------------------");
            out.println("1) Add Manager");
            out.println("2) Change Status of Manager");
            out.println("3) Delete Manager");
            out.println("4) Login");
            out.println("5) Show Manager List");
            out.println("6) Save Manager List");
            out.println("7) Load Manager List");
            out.println("8) Generate Member Reports");
            out.println("9) Generate Provider Reports");
            out.println("10) Generate Manager Report");
            out.println("11) Logout");

            out.print("Please select an option: ");

            while (!input.hasNextInt()) {
                out.print("Please enter a valid number: ");
                input.nextLine();
            }

            menuOption = input.nextInt();

            switch (menuOption) {

                case 1: // Add Manager
                    managerMenu.add();
                    break;

                case 2: // Change Status of Manager
                    managerMenu.changeStatus();
                    break;

                case 3: // Delete Manager
                    managerMenu.delete();
                    break;

                case 4: // Login
                    out.print("Please enter a manager ID: ");

                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int id = input.nextInt();
                    if (managerMenu.login(id))
                        out.println("Manager has been logged in.");

                    break;

                case 5: // Show Manager List
                    managerMenu.showManagers();
                    break;

                case 6: // Save Manager List
                    managerMenu.save();
                    break;

                case 7: // Load Manager List
                    managerMenu.load();
                    break;

                case 8: // Generate Member Reports
                    managerMenu.generateMemberReports();
                    break;

                case 9: // Generate Provider Reports
                    managerMenu.generateProviderReports();
                    break;

                case 10: // Generate Manager Report
                    managerMenu.generateManagerReport();
                    break;

                default:
                    managerMenu.logout();
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
