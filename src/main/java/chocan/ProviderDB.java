package chocan;

import static java.lang.System.out;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProviderDB {

    private Provider root;

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

    // Add providers to tree.
    private Provider Add(Provider root, int id, boolean active, String name, String address, String city, String state, int zip) {

        // If no providers, create it.
        if (root == null) {

            root = new Provider(id,active,name,address,city,state,zip);
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

            int id, zip;
            String name, address, city, state;
            boolean active;

            while (read.hasNext()) {

                id = read.nextInt();
                active = read.nextBoolean();
                name = read.next();
                address = read.next();
                city = read.next();
                state = read.next();
                zip = read.nextInt();

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

    }
}
