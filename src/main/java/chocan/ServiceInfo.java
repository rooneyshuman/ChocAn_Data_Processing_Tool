package chocan;

//ServiceInfo: holds info for each service code. Contained within Provider Directory

import java.text.NumberFormat;
import static java.lang.System.out;

public class ServiceInfo {
    //Data fields
    protected String name;
    protected int code;
    protected double fee;
    protected ServiceInfo left;
    protected ServiceInfo right;

    //Default constructor
    public ServiceInfo() {
        this.name = null;
        this.code = 0;
        this.fee = 0;
        this.left = null;
        this.right = null;
    }

    //Copy constructor w/ args
    public ServiceInfo(String t_name, int t_code, double t_fee) {
        this.name = t_name;
        this.code = t_code;
        this.fee = t_fee;
        this.left = null;
        this.right = null;
    }

    //Returns left pointer
    public ServiceInfo getLeft() {
        return left;
    }

    //Returns right pointer
    public ServiceInfo getRight() {
        return right;
    }

    //Set left pointer
    public void setLeft(ServiceInfo new_left) {
        this.left = new_left;
    }

    public void setRight(ServiceInfo new_right) {
        this.right = new_right;
    }

    //Display function
    public void display(){
        out.printf("--------------------------------------------\n");
        out.println("Service name: " + this.name);
        out.println("Service code: " + this.code);
        out.print("Service fee: ");
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        out.println(formatter.format(this.fee));
    }

    //Returns the String of the service name
    public String Service(){
        return this.name;
    }

    //Compares the names and returns true if comparison is greater than 0.
    public boolean lessThan(String name){
        return (this.name.compareTo(name) > 0);
    }

    //Find by service code - returns true if match is found
    public boolean find(int toFind) {
        return (toFind == this.code);
    }
}