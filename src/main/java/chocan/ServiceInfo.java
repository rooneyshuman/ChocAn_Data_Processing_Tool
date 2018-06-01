package chocan;

//ServiceInfo: holds info for each service code. Contained within Provider Directory

import java.text.NumberFormat;

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

    //Copy constructor
    public ServiceInfo(ServiceInfo to_copy) {
        this.name = to_copy.name;
        this.code = to_copy.code;
        this.fee = to_copy.fee;
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
    public void Display(){
        System.out.printf("--------------------------------------------\n");
        System.out.println("Service name: " + this.name);
        System.out.println("Service code: " + this.code);
        System.out.print("Service fee: ");
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        System.out.println(formatter.format(this.fee));
    }

    //Returns the String of the service name
    public String Service(){

        return this.name;
    }

    //Compares the names and returns true if comparison is greater than 0.
    public boolean Less_Than(String name){
        if(this.name.compareTo(name) >0)
            return true;
        return false;
    }

    //Find by service code
    public boolean Find(int to_find) {
        if (to_find == this.code)
            return true;
        return false;
    }
}