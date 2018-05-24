package chocan;

//ServiceInfo: holds info for each service code. Contained within Provider Directory
//ServiceInfo: holds info for each service code. Contained within Provider Directory

public class ServiceInfo {
    //Data fields
    protected String name;
    protected int code;
    protected double fee;

    //Default constructor
    public ServiceInfo() {
        this.name = null;
        this.code = 0;
        this.fee = 0;
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
    }


    //Display function
    public void Display(){
        System.out.printf("--------------------------------------------\n");
        System.out.println("SERVICE INFO: ");
        System.out.println("Service name: " + this.name);
        System.out.println("Service code: " + this.code);
        System.out.println("Service fee: " + this.fee);
    }


    //Compares names to see if there is a match.
    public boolean Equals(String match){
        return this.name.equals(match);
    }

    //Compares the names and returns true if comparison is greater than 0.
    public boolean Less_Than(String name){
        if(this.name.compareTo(name) >0)
            return true;
        return false;
    }

    //Input function used for building a directory tree, populates the fields with the arguments in the parameters.
    public void input(String t_name, int t_code, double t_fee){
        this.name = t_name;
        this.code = t_code;
        this.fee = t_fee;
    }

    //Find by service code
    public boolean Find(int to_find) {
        if (to_find == this.code)
            return true;
        return false;
    }
}

//Node class inherits ServiceInfo, is used to build directory tree
class Node extends ServiceInfo {
    //Data fields
    protected Node left;
    protected Node right;
    protected ServiceInfo service;

    //Constructor
    public Node(String name, int code, double fee) {
        super(name, code, fee);
        this.left = null;
        this.right = null;
    }

    //Kickstarts parent's (ServiceInfo) contstructor
    public Node(ServiceInfo copy){
        super(copy);
        this.left = null;
        this.right = null;
    }


    //Methods
    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

   /* public void display(){
        service.Display();
    }

    public boolean find(int to_find) {
        return service.Find(to_find);
    }
    */
}
