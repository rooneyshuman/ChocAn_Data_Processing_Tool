package chocan;

import java.io.PrintWriter;
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
        out.println("Member name: " + name);
        out.println("Member number: " + id);
        out.println("Member street address: " + address);
        out.println("Member city: " + city);
        out.println("Member state: " + state);
        out.println("Member zip code: " + zip);
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

    public boolean updateActivation() {
        Scanner sc = new Scanner(System.in);
        String reply;

        if (displayStatus()) {
            out.print("Change the status to be suspended(Y/N): ");
            reply = sc.nextLine();
            if(reply.equalsIgnoreCase("Y"))
                active = false;
        }
        else {
            out.print("Change the status to be active(Y/N): ");
            reply = sc.nextLine();
            if(reply.equalsIgnoreCase("Y"))
                active = true;
        }

       return displayStatus();
    }

    // Get name of member.
    public String getName() {
        return this.name;
    }

    // Save member data for service record.
    public void saveServiceRecord(PrintWriter write) {

        write.print(this.name + "|");
        write.print(this.id + "|");
        write.print(this.address + "|");
        write.print(this.city + "|");
        write.print(this.state + "|");
        write.println(this.zip);

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

    void addService(String memberName, String serviceDate, String providerName, String serviceName) {
        //serviceDB.add(memberName,serviceDate,providerName,serviceName);
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
