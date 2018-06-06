package chocan;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.out;

public class Member {

    protected int id;
    boolean active;
    protected String name;
    String address;
    String city;
    String state;
    String zip;
    Member left;
    Member right;
    private MemberServiceDB serviceDB;

    Member()
    {
        id = 0;
        active = false;
        name = null;
        address = null;
        city = null;
        state = null;
        zip = null;
        left = null;
        right = null;
        serviceDB = null;
    }

    //id,active,name,address,city,state,zip
    Member(int newId, boolean newActive, String newName, String newAddress, String newCity, String newState, String newZip) {
        id = newId;
        active = newActive;
        name = newName;
        address = newAddress;
        city = newCity;
        state = newState;
        zip = newZip;
        left = null;
        right = null;
        serviceDB = new MemberServiceDB();

        try {
            File file = new File("src/main/java/chocan/db/Members/" + id + ".txt");
            file.getParentFile().mkdirs();
            boolean fileExists = file.exists();

            if (!fileExists) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter write = new PrintWriter(bw);

            // If new file, create first line with member info.
            if (!fileExists) saveMemberService(write);

            write.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        serviceDB.load(id);
    }

    public Member goLeft(){
        return left;
    }

    public Member goRight(){
        return right;
    }

    void setLeft(Member NewLeft){
        left = NewLeft;
    }

    void setRight(Member NewRight) {
        right = NewRight;
    }

    public void display() {

        out.println("Member Name: " + name);
        out.println("Member Number: " + id);
        out.println("Member Address: " + address);
        out.println("Member City: " + city);
        out.println("Member State: " + state);
        out.println("Member Zip Code: " + zip);

        if (active) out.println("Member Status: Active");
        else out.println("Member Status: Inactive");

        out.println("-----------------------------------------");
    }

    int checkID(int id) {
        // If id passed in is less than current, return -1.
        // If id passed in is equal, return 0.
        // If id passed in is greater than current, return 1.
        return Integer.compare(id, this.id);
    }

    boolean checkActivation(){
        return active;
    }

    boolean editInfo() {
        Scanner sc = new Scanner(System.in);

        out.print("New ID: ");
        int new_id = sc.nextInt();

        out.print("New name: ");
        String new_name = sc.nextLine();

        out.print("New address: ");
        String new_address = sc.nextLine();

        out.print("New city: ");
        String new_city = sc.nextLine();

        out.print("New state: ");
        String new_state = sc.nextLine();

        out.print("New zip: ");
        String new_zip = sc.nextLine();

        id = new_id;
        name = new_name;
        address = new_address;
        city = new_city;
        state = new_state;
        zip = new_zip;

        return true;
    }

    // Get name of member.
    public String getName() {
        return this.name;
    }

    // Save member data for service record.
    public void saveMemberService(PrintWriter write) {

        write.print(this.name + "|");
        write.print(this.id + "|");
        write.print(this.address + "|");
        write.print(this.city + "|");
        write.print(this.state + "|");
        write.println(this.zip);

    }

    // Save member data for service record.
    public void saveServiceRecord(String serviceDate, String providerName, String serviceName) {

        serviceDB.addServiceRecord(serviceDate,providerName,serviceName);

    }

    int compareID(int id) {

        // If id passed in is less than current, return -1.
        // If id passed in is equal, return 0.
        // If id passed in is greater than current, return 1.
        return Integer.compare(id, this.id);
    }

    public void save(PrintWriter write) {

        write.print(this.id);
        write.print(":");
        write.print(this.active);
        write.print(":" + this.name + ":" + this.address + ":" + this.city);
        write.println(":" + this.state + ":" + this.zip);

    }

    void write() {
        // writes member info to the text files
    }

    public void changeStatus() {

        active = !active;

    }


    private boolean displayStatus() {
        if(active) {
            out.println("The current status is active.");
            return true;
        }
        else {
            out.println("The current status is suspended.");
            return false;
        }
    }

}
