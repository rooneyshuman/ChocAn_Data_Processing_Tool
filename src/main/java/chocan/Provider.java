package chocan;

import static java.lang.System.out;

public class Provider extends Member {

    public Provider() {

        super.id = 0;
        super.active = false;
        super.name = null;
        super.address = null;
        super.city = null;
        super.state = null;
        super.zip = 0;
        super.left = null;
        super.right = null;

    }

    Provider(int id, boolean active, String name, String address, String city, String state, int zip) {

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

    public Provider GoLeft() {
        return (Provider) super.left;
    }

    public Provider GoRight() {
        return (Provider) super.right;
    }

    void SetLeft(Provider left) {
        super.left = left;
    }

    void SetRight(Provider right) {
        super.right = right;
    }

    int CompareID(int id) {

        // If id passed in is less than current, return -1.
        // If id passed in is equal, return 0.
        // If id passed in is greater than current, return 1.
        return Integer.compare(id, this.id);

    }

    public void Display() {

        out.println("Provider name: " + name);
        out.println("Provider number: " + id);
        out.println("Provider street address: " + address);
        out.println("Provider city: " + city);
        out.println("Provider state: " + state);
        out.println("Provider zip code: " + zip);

        if (active) out.println("Provider status: Active");
        else out.println("Provider status: Inactive");

        out.println("-----------------------------------------");
    }
}
