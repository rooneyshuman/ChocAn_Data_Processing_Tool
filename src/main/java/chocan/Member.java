package chocan;

import java.io.PrintWriter;
import java.util.Scanner;

public class Member {

    protected int id;
    protected boolean active;
    protected String name;
    protected String address;
    protected String city;
    protected String state;
    protected String zip;
    protected Member left;
    protected Member right;

    public Member()
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
    }
    //id,active,name,address,city,state,zip
    public Member(int newId,boolean newActive,String newName, String newAddress, String newCity, String newState, String newZip ){
        id = newId;
        active = newActive;
        name = new String(newName);
        address = new String(newAddress);
        city =  new String(newCity);
        state = new String(newState);
        zip = newZip;
        left = null;
        right = null;
    }

    public Member GoLeft(){
        return left;
    }
    public Member GoRight(){
        return right;
    }
    public void SetLeft(Member NewLeft){
        left = NewLeft;
    }
    public void SetRight(Member NewRight) {
        right = NewRight;
    }
    public void Display(){
        System.out.println("Member name: " + name);
        System.out.println("Member number: " + id);
        System.out.println("Member street address: " + address);
        System.out.println("Member city: " + city);
        System.out.println("Member state: " + state);
        System.out.println("Member zip code: " + zip);
    }
    public boolean CheckID(int ID){
        if(ID == id)            //check if the ID exists
            return true;
        else
            return false;
    }
    public boolean checkActivation(){
        return active;
    }
    public boolean EditInfo(){
        Scanner sc = new Scanner(System.in);
        System.out.print("New ID: ");
        int new_id = sc.nextInt();
        System.out.print("New name: ");
        String new_name = sc.nextLine();
        System.out.print("New address: ");
        String new_address = sc.nextLine();
        System.out.print("New city: ");
        String new_city = sc.nextLine();
        System.out.print("New state: ");
        String new_state = sc.nextLine();
        System.out.print("New zip: ");
        String new_zip = sc.nextLine();
        id = new_id;
        name = new String(new_name);
        address = new String(new_address);
        city = new String(new_city);
        state = new String(new_state);
        zip = new_zip;
        return true;
    }
    public boolean UpdateActivation() {
        Scanner sc = new Scanner(System.in);
        String reply = new String();
        if (display_status() == true) {
            System.out.print("Change the status to be suspended(Y/N): ");
            reply = sc.nextLine();
            if(reply.equalsIgnoreCase("Y"))
                active = false;
        }
        else {
            System.out.print("Change the status to be active(Y/N): ");
            reply = sc.nextLine();
            if(reply.equalsIgnoreCase("Y"))
                active = true;
        }
       return display_status();
    }

    int CompareID(int id) {

        // If id passed in is less than current, return -1.
        // If id passed in is equal, return 0.
        // If id passed in is greater than current, return 1.
        return Integer.compare(id, this.id);
    }

    public void Save(PrintWriter write) {

        write.print(this.id);
        write.print(":");
        write.print(this.active);
        write.print(":" + this.name + ":" + this.address + ":" + this.city);
        write.println(":" + this.state + ":" + this.zip);

    }

    public void ChangeStatus() {

        active = active ? false : true;

    }


    public boolean display_status(){
        if(active == true) {
            System.out.println("The current status is active.");
            return true;
        }
        else {
            System.out.println("The current status is suspended.");
            return false;
        }
    }


}
