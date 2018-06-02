package chocan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.lang.System.out;

public class MemberDB {
    private Member root;
    private int memberCount;

    MemberDB() {
        root = null;
        Load();
    }
    // This checks the members ID.
    boolean CheckID(int id) {

        if (CheckID(id,root)) return true;
        else {
            out.print("Invalid member.");
            return false;
        }

    }

    // Traverses tree to check ID.
    private boolean CheckID(int id, Member root) {

        if (root == null) return false;

        if (root.CheckID(id)) return true;

        return CheckID(id,root.GoLeft()) ? true : CheckID(id,root.GoRight());
    }

    // Add members via public prompt. (For managers.)
    public void Add() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Please enter the member's information.");
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

        this.root = Add(this.root,(700000 + memberCount),true,name,address,city,state,zip);
        out.println("Member added.");
        Save();

    }

    // Add members to tree.
    private Member Add(Member root, int id, boolean active, String name, String address, String city, String state, String zip) {

        // If no members, create it.
        if (root == null) {

            root = new Member(id,active,name,address,city,state,zip);
            ++memberCount;
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

    // Activate or deactivate member's account.
    public void ChangeStatus() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Changing status of a member...");
        out.println("-----------------------------------------");

        out.print("Please enter a member ID: ");

        while (!input.hasNextInt()) {

            out.print("Please enter a valid number: ");
            input.nextLine();

        }

        int id = input.nextInt();
        input.nextLine();

        ChangeStatus(this.root,id);
        Save();

    }

    // Traverse tree and change status of a member.
    private void ChangeStatus(Member root, int id) {

        // If empty, return.
        if (root == null) return;

        // If ID matches, change status.
        if (root.CompareID(id) == 0) {

            out.println("Member status has changed.");
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

    

    // Loads the list of members.
    private void Load() {

        try {
            File file = new File("src/main/java/chocan/db/members.txt");
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

    // Delete a member via public prompt. (For managers.)
    public void Delete() {

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Deleting a member...");
        out.println("-----------------------------------------");

        out.print("Please enter a member ID: ");

        while (!input.hasNextInt()) {
            out.print("Please enter a valid number: ");
            input.nextLine();
        }

        int id = input.nextInt();
        input.nextLine();

        this.root = Delete(this.root,id);
        Save();

    }

    // Delete member from tree.
    private Member Delete(Member root, int id) {

        // If no members, return.
        if (root == null) return null;

        // If member is found, get ready to delete.
        if (root.CompareID(id) == 0) {

            out.println("Member deleted.");
            --memberCount;

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
            Member temp = root.GoRight();
            Member previous = null;
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

    // Saves the list of members.
    public void Save() {

        File file = new File("src/main/java/chocan/db/members.txt");
        file.getParentFile().mkdirs();
        PrintWriter write = null;

        try {
            write = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Save(this.root,write);
        out.println("Member list has been saved.");
        write.close();
    }

    // Traverse tree and save data.
    private void Save(Member root, PrintWriter write) {

        // If empty, return.
        if (root == null) return;

        // Inorder traversal.
        Save(root.GoLeft(),write);
        root.Save(write);
        Save(root.GoRight(),write);
    }

    // Displays the list of members.
    public void ShowMembers() {

        out.println("\n-----------------------------------------");
        out.println("Showing list of members...");
        out.println("-----------------------------------------");

        if (this.root == null) {
            out.println("(No members listed.)");
            return;
        }

        ShowMembers(this.root);
        out.println("Total number of members: " + (memberCount - 1));

    }

    // Recursive display.
    private void ShowMembers(Member root) {

        if (root == null) return;

        ShowMembers(root.GoLeft());
        root.Display();
        ShowMembers(root.GoRight());

    }

    public Member find (int toCheck){
        //empty database -> null
        if(root == null)
            return null;
        else
        return find(toCheck, this.root);
    }

    private Member find(int toCheck, Member root){
        if(root == null)
            return null;
        if(root.CompareID(toCheck) == 0)
            return root;
        else if (root.CompareID(toCheck) > 0)
            return find(toCheck,root.GoRight());
        else
            return find(toCheck,root.GoLeft());
    }

    public boolean addService(int toCheck, String serviceDate, String providerName, String serviceName)
    {
        Member toFind = null;

        toFind = find(toCheck);

        if(toFind == null)
            return false;
        else{
            toFind.addService(toFind.name,serviceDate,providerName,serviceName);
            return true;
        }
    }



    public static void main(String[] args) {

        MemberDB memberMenu = new MemberDB();
        Scanner input = new Scanner(System.in);
        int menuOption;

        do {
            out.println("\n-----------------------------------------");
            out.println("Member Test Interface");
            out.println("-----------------------------------------");
            out.println("1) Add Member");
            out.println("2) Change Status of Member");
            out.println("3) Delete Member");
            out.println("4) Check Member ID");
            out.println("5) Show Member List");
            out.println("6) Save Member List");
            out.println("7) Load Member List");
            out.println("8) Add a new service record");

            out.print("Please select an option: ");

            while (!input.hasNextInt()) {
                out.print("Please enter a valid number: ");
                input.nextLine();
            }

            menuOption = input.nextInt();

            switch (menuOption) {

                case 1: // Add Member
                    memberMenu.Add();
                    break;

                case 2: // Change Status of Member
                    memberMenu.ChangeStatus();
                    break;

                case 3: // Delete Member
                    memberMenu.Delete();
                    break;

                case 4: // Check Member ID
                    out.print("Please enter a member ID: ");

                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int id = input.nextInt();
                    if (memberMenu.CheckID(id))
                        out.println("Member ID is valid.");
                    break;

                case 5: // Show Member List
                    memberMenu.ShowMembers();
                    break;

                case 6: // Save Member List
                    memberMenu.Save();
                    break;

                case 7: // Load Member List
                    memberMenu.Load();
                    break;

                case 8: // Add new service record

                    out.print("Please enter a member ID: ");
                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int toCheck = input.nextInt();
                    if(memberMenu.addService(toCheck,"Test", "Test", "Test") == true)
                        out.print("Pass\n");
                    else
                        out.print("Fail\n");
                    break;
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

