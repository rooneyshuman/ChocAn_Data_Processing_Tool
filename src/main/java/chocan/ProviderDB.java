package chocan;

import static java.lang.System.out;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProviderDB {

    private Provider root;
    private int providerCount = 0;

    ProviderDB() {

        this.root = null;
        Load(); // Loads the list of providers.

    }

    // This checks the providers ID.
    boolean CheckID(int id) {

        if (CheckID(id,this.root)) return true;
        else {
            out.print("Invalid member.");
            return false;
        }

    }

    // Traverses tree to check ID.
    private boolean CheckID(int id, Provider root) {

        if (root == null) return false;

        if (root.CheckID(id)) return true;

        if (CheckID(id,root.GoLeft())) return true;
        else return CheckID(id, root.GoRight());

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

    public boolean Update() {
        return false;
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
    public boolean Save() {
        return false;
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

    public static void main(String[] args) {

        ProviderDB providerMenu = new ProviderDB();
        Scanner input = new Scanner(System.in);
        int menuOption;

        do {
            out.println("\n-----------------------------------------");
            out.println("Provider Test Interface");
            out.println("-----------------------------------------");
            out.println("1) Add Provider");
            out.println("2) Edit Provider");
            out.println("3) Delete Provider");
            out.println("4) Check Provider ID");
            out.println("5) Show Provider List");
            out.println("6) Save Provider List");
            out.println("7) Load Provider List");

            out.print("Please select an option: ");

            while (!input.hasNextInt()) {
                out.print("Please enter a valid number: ");
                input.nextLine();
            }

            menuOption = input.nextInt();

            switch (menuOption) {

                case 1:
                    providerMenu.Add();
                    break;
                case 2:
                    break;
                case 3:
                    providerMenu.Delete();
                    break;
                case 4:
                    out.print("Please enter a provider ID: ");

                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int id = input.nextInt();
                    if (providerMenu.CheckID(id))
                        out.println("Member ID is valid.");

                    break;
                case 5:
                    providerMenu.ShowProviders();
                    break;
                case 6:
                    break;
                case 7:
                    providerMenu.Load();
                    break;
            }
        } while (again());


    }

    public static boolean again() {

        Scanner input = new Scanner(System.in);
        String reply;
        out.print("Go back to menu? (Yes/No) ");
        reply = input.next(); input.nextLine();

        return reply.equalsIgnoreCase("yes");

    }

}
