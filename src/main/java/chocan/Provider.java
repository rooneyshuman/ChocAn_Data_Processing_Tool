package chocan;

import java.io.PrintWriter;

import static java.lang.System.out;

public class Provider extends Member {

    public Provider() {

        super.id = 0;
        super.active = false;
        super.name = null;
        super.address = null;
        super.city = null;
        super.state = null;
        super.zip = null;
        super.left = null;
        super.right = null;

    }

    Provider(int id, boolean active, String name, String address, String city, String state, String zip) {

        super.id = id;
        super.active = active;
        super.name = name;
        super.address = address;
        super.city = city;
        super.state = state;
        super.zip = zip;
        super.left = null;
        super.right = null;

    }

    public Provider goLeft() {
        return (Provider) super.left;
    }

    public Provider goRight() {
        return (Provider) super.right;
    }

    void setLeft(Provider left) {
        super.left = left;
    }

    void setRight(Provider right) {
        super.right = right;
    }

    int compareID(int id) {

        // If id passed in is less than current, return -1.
        // If id passed in is equal, return 0.
        // If id passed in is greater than current, return 1.
        return Integer.compare(id, this.id);

    }

    // Change active status.
    public void changeStatus() {

        super.active = !super.active;

    }

    public void display() {

        out.println("Provider Name: " + name);
        out.println("Provider Number: " + id);
        out.println("Provider Address: " + address);
        out.println("Provider City: " + city);
        out.println("Provider State: " + state);
        out.println("Provider Zip Code: " + zip);

        if (active) out.println("Provider Status: Active");
        else out.println("Provider Status: Inactive");

        out.println("-----------------------------------------");
    }

    // Save provider data.
    public void save(PrintWriter write) {

        write.print(this.id);
        write.print(":");
        write.print(this.active);
        write.print(":" + this.name + ":" + this.address + ":" + this.city);
        write.println(":" + this.state + ":" + this.zip);

    }

    // Save provider data for service record.
    public void saveServiceRecord(PrintWriter write) {

        write.print(this.name + "|");
        write.print(this.id + "|");
        write.print(this.address + "|");
        write.print(this.city + "|");
        write.print(this.state + "|");
        write.println(this.zip);

    }

    // Get name of provider.
    public String getName() {
        return this.name;
    }
}
