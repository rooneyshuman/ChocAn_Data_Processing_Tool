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

    // This function will be used by ProviderDB.
    public Manager authorize() {

        return authorize(this.root,managerID);
    }

    // Traverse tree to find manager.
    private Manager authorize(Manager root, int id) {

        if (root == null || id == 0) return null;

        if (root.checkID(id) == 0) return root;

        Manager temp = authorize(root.goLeft(),id);

        if (temp != null)
            return temp;
        else
            return authorize(root.goRight(),id);
    }

    // Add managers via public prompt.
    public void add() {

        // Check for authorization.
        if (managerID == 0) {
            out.println("Must be logged in with a Manager ID.");
            return;
        }

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

        while (zip.length() != 6) {
            out.print("Please enter 6 digits: ");
            zip = input.nextLine();
        }

        this.root = add(this.root,(800000000 + managerCount),true,name,address,city,state,zip);
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
        //When a manager is deleted sometimes there can be duplicate ID's this will increment a duplicate ID to
        //prevent them from being the same.
        if(direction == 0){
            ++id;
            direction = root.compareID(id);
        }

        // Traverse tree to find where to add.
        if (direction < 0)
            root.setLeft(add(root.goLeft(),id,active,name,address,city,state,zip));
        else if (direction > 0)
            root.setRight(add(root.goRight(),id,active,name,address,city,state,zip));

        return root;

    }

    // Activate or deactivate manager's account.
    public void changeStatus() {

        // Check for authorization.
        if (managerID == 0) {
            out.println("Must be logged in with a Manager ID.");
            return;
        }

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

        //Error message if input is out of bounds
        while (id < 800000000 || id > 899999999) {
            out.print("Please enter 9 characters. Manager ID's begin with '8': ");
            id = input.nextInt();
        }

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

        // Check for authorization.
        if (managerID == 0) {
            out.println("Must be logged in with a Manager ID.");
            return;
        }

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

        //Error message if input is out of bounds
        while (id < 800000000 || id > 899999999) {
            out.print("Please enter 9 digits. Manager ID's begin with '8': ");
            id = input.nextInt();
        }

        // Check if deleting self.
        if (id == managerID) {
            out.println("Cannot delete yourself. Please login with another manager account.");
            return;
        }

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

    // Get name of manager logged in.
    private String getName() {
        return getName(this.root,this.managerID);
    }

    // Traverse tree to find manager.
    private String getName(Manager root, int id) {

        if (root == null) return null;

        if (root.compareID(id) == 0) return root.getName();

        String result = getName(root.goLeft(),id);

        return (result != null) ? result : getName(root.goRight(),id);

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

        // Check for authorization.
        if (managerID == 0) {
            out.println("Must be logged in with a Manager ID.");
            return;
        }

        File file = new File("src/main/java/chocan/db/Members/");
        File[] files = file.listFiles();
        Scanner read;

        //used to get the current date to append to the end of the file name
        String date = getDate();

        for (File f: files) {
            try {
                read = new Scanner(f);
                read.useDelimiter("[|\\n]");

                File report = new File("src/main/java/chocan/report/" + f.getName() + "_" + date);
                report.getParentFile().mkdirs();
                PrintWriter write = null;

                try {
                    write = new PrintWriter(report);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                write.println("-----------------------------------------");
                write.println("Member Report");
                write.println("-----------------------------------------");

                String memberName = read.next();
                int memberID = read.nextInt();
                String memberAddress = read.next();
                String memberCity = read.next();
                String memberState = read.next();
                int memberZip = read.nextInt();

                write.println("Member Name: " + memberName);
                write.println("Member ID: " + memberID);
                write.println("Member Address: " + memberAddress);
                write.println("Member City: " + memberCity);
                write.println("Member State: " + memberState);
                write.println("Member Zip Code: " + memberZip);
                write.println("-----------------------------------------");

                out.println("Member Name: " + memberName);
                out.println("Member ID: " + memberID);
                out.println("Member Address: " + memberAddress);
                out.println("Member City: " + memberCity);
                out.println("Member State: " + memberState);
                out.println("Member Zip Code: " + memberZip);
                out.println("-----------------------------------------");

                while(read.hasNext()) {

                    String serviceDate = read.next();
                    String providerName = read.next();
                    String serviceName = read.next();

                    write.println("Service Date: " + serviceDate);
                    write.println("Provider Name: " + providerName);
                    write.println("Service Name: " + serviceName);

                    out.println("Service Date: " + serviceDate);
                    out.println("Provider Name: " + providerName);
                    out.println("Service Name: " + serviceName);

                }

                write.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    // Generate Provider Reports
    public void generateProviderReports() {

        // Check for authorization.
        if (managerID == 0) {
            out.println("Must be logged in with a Manager ID.");
            return;
        }

        int consultations = 0;
        double fee, totalFee = 0;

        //used to get the current date to append to the end of the file name
        String date = getDate();

        // Delete EFT records first.
        File eft = new File("src/main/java/chocan/report/EFT.txt");
        eft.delete();

        File file = new File("src/main/java/chocan/db/providers/");
        File[] files = file.listFiles();
        Scanner read;
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        for (File f: files) {
            try {
                read = new Scanner(f);
                read.useDelimiter("[|\\n]");

                File report = new File("src/main/java/chocan/report/" + f.getName() + "_" + date);
                report.getParentFile().mkdirs();
                PrintWriter write = null;

                try {
                    write = new PrintWriter(report);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                write.println("-----------------------------------------");
                write.println("Provider Report");
                write.println("-----------------------------------------");

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

                out.println("-----------------------------------------");
                out.println("Provider Name: " + providerName);
                out.println("Provider ID: " + providerID);
                out.println("Provider Address: " + providerAddress);
                out.println("Provider City: " + providerCity);
                out.println("Provider State: " + providerState);
                out.println("Provider Zip Code: " + providerZip);
                out.println("-----------------------------------------");

                while(read.hasNext()) {
                    String serviceDate = read.next();
                    String dateReceived = read.next();
                    String memberName = read.next();
                    int memberID = read.nextInt();
                    int serviceCode = read.nextInt();

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

                generateEFT(providerName,providerID,totalFee);

                // Reset for next providers.
                consultations = 0;
                totalFee = 0;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    // Generate Manager Report
    public void generateManagerReport() {

        // Check for authorization.
        if (managerID == 0) {
            out.println("Must be logged in with a Manager ID");
            return;
        }

        String managerName = getName();

        int consultations = 0, totalConsultations = 0, providers = 0;
        double fee = 0, totalFee = 0;

        //used to get the current date to append to the end of the file name
        String date = getDate();

        File file = new File("src/main/java/chocan/db/providers/");
        File[] files = file.listFiles();
        Scanner read;
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        File report = new File("src/main/java/chocan/report/" + managerName + ".txt" + "_" + date);
        report.getParentFile().mkdirs();
        PrintWriter write = null;

        try {
            write = new PrintWriter(report);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        write.println("-----------------------------------------");
        write.println("Manager Report");
        write.println("-----------------------------------------");

        for (File f: files) {
            try {
                read = new Scanner(f);
                read.useDelimiter("[|\\n]");

                String providerName = read.next();
                int providerID = read.nextInt();
                String ignore = read.next();
                ignore = read.next();
                ignore = read.next();
                ignore = String.valueOf(read.nextInt());

                write.println("Provider Name: " + providerName);
                write.println("Provider ID: " + providerID);

                out.println("-----------------------------------------");
                out.println("Provider Name: " + providerName);
                out.println("Provider ID: " + providerID);

                while(read.hasNext()) {
                    ignore = read.next();
                    ignore = read.next();
                    ignore = read.next();
                    ignore = String.valueOf(read.nextInt());
                    ignore = String.valueOf(read.nextInt());

                    fee += read.nextDouble();
                    ++consultations;
                }

                write.println("Total Consultations: " + consultations);
                write.println("Total Fee: " + nf.format(fee));
                write.println("-----------------------------------------");

                out.println("Total Consultations: " + consultations);
                out.println("Total Fee: " + nf.format(fee));
                out.println("-----------------------------------------");

                totalFee += fee;
                totalConsultations += consultations;
                ++providers;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Append last line in report.
        //try {
            //File report = new File("src/main/java/chocan/report/" + managerName + ".txt");
            //report.getParentFile().mkdirs();
            //if (!report.exists()) report.createNewFile();

            //FileWriter fw = new FileWriter(report,true);
            //BufferedWriter bw = new BufferedWriter(fw);
            //PrintWriter pw = new PrintWriter(bw);

            write.println("Total Providers: " + providers);
            write.println("Total Consultations: " + totalConsultations);
            write.println("Total Fee: " + nf.format(totalFee));
            write.println("-----------------------------------------");

            out.println("Total Providers: " + providers);
            out.println("Total Consultations: " + totalConsultations);
            out.println("Total Fee: " + nf.format(totalFee));
            out.println("-----------------------------------------");

            write.close();

        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

    }


    // Generate EFT Records
    public void generateEFT(String providerName, int providerID, double totalFee) {

        // Check for authorization.
        if (managerID == 0) {
            out.println("Must be logged in with a Manager ID.");
            return;
        }

        String date = getDate();

        NumberFormat nf = NumberFormat.getCurrencyInstance();

        try {
            File report = new File("src/main/java/chocan/report/EFT.txt" + "_" + date);
            report.getParentFile().mkdirs();
            if (!report.exists()) report.createNewFile();

            FileWriter fw = new FileWriter(report,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter write = new PrintWriter(bw);

            write.println("Provider Name: " + providerName);
            write.println("Provider ID: " + providerID);
            write.println("Transfer Amount: " + nf.format(totalFee));
            write.println("-----------------------------------------");

            write.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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

                    //Error message if input is not an int
                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int id = input.nextInt();

                    //Error message if input is out of bounds
                    while (id < 800000000 || id > 899999999) {
                        out.print("Please enter 9 digits. Manager ID's begin with '8': ");
                        id = input.nextInt();
                    }

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

        return reply.startsWith("Y") || reply.startsWith("y");
    }

    private String getDate(){
        String date = null;
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        Date currentDate = new Date();
        return date = formatDate.format(currentDate);
    }
}
