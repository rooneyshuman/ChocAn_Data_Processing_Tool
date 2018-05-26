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

    // Add providers via public prompt.
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
        providerMenu.ShowProviders();

        providerMenu.Add();

        providerMenu.ShowProviders();

    }
}
