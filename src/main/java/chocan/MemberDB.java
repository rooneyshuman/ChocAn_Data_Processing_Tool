package chocan;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.out;

public class MemberDB {

    private Member root;
    private int memberCount;

    MemberDB() {
        root = null;
        load();
    }

    // This checks the members ID.
    boolean checkID(int id) {

        if (checkID(id,root)) return true;
        else {
            out.print("Invalid member.");
            return false;
        }

    }

    // Traverses tree to check ID.
    private boolean checkID(int id, Member root) {

        if (root == null) return false;

        if (root.checkID(id) == 0) return true;

        return checkID(id, root.goLeft()) || checkID(id, root.goRight());
    }

    // Traverses tree to check for active status.
    public boolean checkStatus(int id) {
        return checkStatus(this.root,id);
    }

    private boolean checkStatus(Member root, int id) {
        if (root == null) return false;

        // If ID matches, return status.
        if (root.checkID(id) == 0) return root.checkStatus();

        //Otherwise, traverse tree.
        if (root.checkID(id) < 0) {
            return checkStatus(root.goLeft(), id);
        } else {
            return checkStatus(root.goRight(), id);
        }
    }

    public String getName(int id) {
        return getName(this.root,id);
    }

    private String getName(Member root, int id) {

        if (root == null) return null;

        if (root.compareID(id) == 0) return root.getName();

        String result = getName(root.goLeft(),id);

        return (result != null) ? result : getName(root.goRight(),id);

    }

    // Add members via public prompt. (For managers.)
    public void add(Manager manager) {

        // Check for manager authorization.
        if (manager == null) {
            out.println("Please login as a manager.");
            return;
        }

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

        while (zip.length() != 6) {
            out.print("Please enter 6 digits: ");
            zip = input.nextLine();
        }

        this.root = add(this.root,(600000000 + memberCount),true,name,address,city,state,zip);
        out.println("Member added.");
        save();

    }

    // Add members to tree.
    private Member add(Member root, int id, boolean active, String name, String address, String city, String state, String zip) {

        // If no members, create it.
        if (root == null) {

            root = new Member(id,active,name,address,city,state,zip);
            ++memberCount;
            return root;

        }

        int direction = root.compareID(id);
        //When a member is deleted sometimes there can be duplicate ID's this will increment a duplicate ID to
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

    // Activate or deactivate member's account.
    public void changeStatus(Manager manager) {

        // Check for manager authorization.
        if (manager == null) {
            out.println("Please login as a manager.");
            return;
        }

        Scanner input = new Scanner(System.in);

        out.println("\n-----------------------------------------");
        out.println("Changing status of a member...");
        out.println("-----------------------------------------");

        out.print("Please enter a member ID: ");

        while (!input.hasNextInt()) {

            out.print("Please enter a valid number: ");
            input.nextLine();

        }

        //Error if input is out of range
        int id = input.nextInt();
        while (id < 600000000 || id > 699999999) {
            out.print("Please enter 9 digits. Member ID's begin with '6': ");
            id = input.nextInt();
        }
        input.nextLine();

        changeStatus(this.root,id);
        save();

    }

    // Traverse tree and change status of a member.
    private void changeStatus(Member root, int id) {

        // If empty, return.
        if (root == null) return;

        // If ID matches, change status.
        if (root.compareID(id) == 0) {

            out.println("Member status has changed.");
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

    // Loads the list of members.
    private void load() {

        try {
            File file = new File("db/members.txt");
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

    // Delete a member via public prompt. (For managers.)
    public void delete(Manager manager) {

        // Check for manager authorization.
        if (manager == null) {
            out.println("Please login as a manager.");
            return;
        }

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

        //Error if input is out of range
        while (id < 600000000 || id > 699999999) {
            out.print("Please enter 9 digits. Member ID's begin with '6': ");
            id = input.nextInt();
        }

        input.nextLine();

        this.root = delete(this.root,id);
        save();

    }

    // Delete member from tree.
    private Member delete(Member root, int id) {

        // If no members, return.
        if (root == null) return null;

        // If member is found, get ready to delete.
        if (root.compareID(id) == 0) {

            out.println("Member deleted.");
            --memberCount;

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
            Member temp = root.goRight();
            Member previous = null;
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

    // Saves the list of members.
    public void save() {

        File file = new File("db/members.txt");
        file.getParentFile().mkdirs();
        PrintWriter write = null;

        try {
            write = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        save(this.root,write);
        out.println("Member list has been saved.");
        write.close();
    }

    // Traverse tree and save data.
    private void save(Member root, PrintWriter write) {

        // If empty, return.
        if (root == null) return;

        // Inorder traversal.
        save(root.goLeft(),write);
        root.save(write);
        save(root.goRight(),write);
    }

    // Displays the list of members.
    public void showMembers() {

        out.println("\n-----------------------------------------");
        out.println("Showing list of members...");
        out.println("-----------------------------------------");

        if (this.root == null) {
            out.println("(No members listed.)");
            return;
        }

        showMembers(this.root);
        out.println("Total number of members: " + memberCount);

    }

    // Recursive display.
    private void showMembers(Member root) {

        if (root == null) return;

        showMembers(root.goLeft());
        root.display();
        showMembers(root.goRight());

    }

    //find the member object in database based on the id provided
    public Member find(int toCheck) {

        return root == null ? null : find(toCheck, this.root);

    }

    //find the member object in database based on the id provided
    private Member find(int toCheck, Member root) {
        if(root == null)
            return null;

        if(root.compareID(toCheck) == 0)
            return root;

        return (root.compareID(toCheck) > 0) ? find(toCheck, root.goRight()) : find(toCheck, root.goLeft());
    }

    //Add new service records based on the ID provided.
    public void addService(int id, String serviceDate, String providerName, String serviceName) {

        String memberName = getName(id);

        try {
            File file = new File("db/Members/" + memberName + ".txt");
            file.getParentFile().mkdirs();
            boolean fileExists = file.exists();

            if (!fileExists) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter write = new PrintWriter(bw);

            // If new file, create first line with member info.
            if (!fileExists) saveMemberService(this.root, id, write);

            write.close();

            saveServiceRecord(this.root, id, serviceDate, providerName, serviceName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Traverse tree and save member service info.
    private void saveMemberService(Member root, int id, PrintWriter write) {

        // If empty, return.
        if (root == null) return;

        // If match, then write data.
        if (root.compareID(id) == 0) {
            root.saveMemberService(write);
            return;
        }

        // Inorder traversal.
        saveMemberService(root.goLeft(),id,write);
        saveMemberService(root.goRight(),id,write);
    }

    // Traverse tree and save member service info.
    private void saveServiceRecord(Member root, int id, String serviceDate, String providerName, String serviceName) {

        // If empty, return.
        if (root == null) return;

        // If match, then write data.
        if (root.compareID(id) == 0) {
            root.saveServiceRecord(serviceDate,providerName,serviceName);
            return;
        }

        // Inorder traversal.
        saveServiceRecord(root.goLeft(),id,serviceDate,providerName,serviceName);
        saveServiceRecord(root.goRight(),id,serviceDate,providerName,serviceName);
    }

    //write the info of the whole database
    public void write () {
       if(root != null)
           write(root);
    }

    private void write(Member root) {
        if(root != null)
            return;

        root.write();
        write(root.goLeft());
        write(root.goRight());
    }

    //write the info of single object
    public boolean write(int toFind) {
        Member temp = find(toFind);

        if(temp != null) {
            temp.write();
            return true;
        }

        return false;
    }

    public static void main(String[] args) {

        MemberDB memberMenu = new MemberDB();
        ManagerDB managerMenu = new ManagerDB();

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
                    memberMenu.add(managerMenu.authorize());
                    break;

                case 2: // Change Status of Member
                    memberMenu.changeStatus(managerMenu.authorize());
                    break;

                case 3: // Delete Member
                    memberMenu.delete(managerMenu.authorize());
                    break;

                case 4: // Check Member ID
                    out.print("Please enter a member ID: ");

                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int id = input.nextInt();

                    //Error if input is out of range
                    while (id < 600000000 || id > 699999999) {
                        out.print("Please enter 9 digits. Member ID's begin with '6': ");
                        id = input.nextInt();
                    }

                    if (memberMenu.checkID(id))
                        out.println("Member ID is valid.");
                    break;

                case 5: // Show Member List
                    memberMenu.showMembers();
                    break;

                case 6: // Save Member List
                    memberMenu.save();
                    break;

                case 7: // Load Member List
                    memberMenu.load();
                    break;

                case 8: // Add new service record
/*
                    out.print("Please enter a member ID: ");
                    while (!input.hasNextInt()) {
                        out.print("Please enter a valid number: ");
                        input.nextLine();
                    }

                    int toCheck = input.nextInt();
                    if(memberMenu.addService(toCheck, "Test", "Test", "Test"))
                        out.print("Pass\n");
                    else
                        out.print("Fail\n");*/
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

