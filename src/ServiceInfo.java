//ServiceInfo: holds info for each service code. Contained within Provider Directory

public class ServiceInfo {
    //Data fields
    private String name;
    private int code;

    //Default constructor
    public ServiceInfo() {
        this.name = null;
        this.code = 0;
    }

    //Copy constructor
    public ServiceInfo(ServiceInfo to_copy) {
        this.name = to_copy.name;
        this.code = to_copy.code;
    }

    //Copy constructor w/ args
    public ServiceInfo(String name, int code) {
        this.name = name;
        this.code = code;
    }

    //Display function
    public void Display(){
        System.out.println("SERVICE INFO: ");
        System.out.println("Service name: " + name);
        System.out.println("Service code: " + code);
    }

    //Find by service code
    public boolean Find(int to_find) {
        if (to_find == code)
            return true;
        return false;
    }
}

//Node class contains an obj of ServiceInfo, is used to build directory tree
class Node {
    //Data fields
    protected Node left;
    protected Node right;
    protected ServiceInfo service;

    //Constructor
    public Node() {
        this.service = new ServiceInfo();
        this.left = null;
        this.right = null;
    }

    //Methods
    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void display(){
        service.Display();
    }

    public boolean find(int to_find) {
        return service.Find(to_find);
    }
}
