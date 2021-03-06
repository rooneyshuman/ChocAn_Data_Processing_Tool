package chocan;

//This class creates a BST from the ServiceInfo class.

import java.io.File;
import java.util.Scanner;
import static java.lang.System.out;

public class ProviderDir {
    
    protected ServiceInfo root;
    protected Scanner input;
    protected Scanner read;

    protected ServiceInfo insert(ServiceInfo root, String name, int code, double fee) {
        if (root == null) {
            return new ServiceInfo(name, code, fee);

            //return root;

        }
         if (root.lessThan(name)) {
            root.setLeft(insert(root.getLeft(), name, code, fee));
            return root;
        }

        else {
            root.setRight(insert(root.getRight(), name, code, fee));
            return root;
        }
    }

    //Displays the entire tree by in order traversal
    protected void display(ServiceInfo root){
        if (root == null){
            return;}

        display(root.getLeft());
        root.display();
        display(root.getRight());
    }

    public ProviderDir() {
        this.root = null;
        input = new Scanner(System.in);
    }

    //Wrapper for function for the insert function
    public void insert(String name, int code, double fee){
        root = insert(root, name, code, fee);
    }

    //Displays the entire tree in order, wrapper function
    public void display(){
        display(root);
    }

    //Opens the text file and checks for exceptions.
    public void openFile() {
        try {
            read = new Scanner(new File("db/provider directory.txt"));
        } catch (Exception e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        }
    }

    //Reads in from text file and calls the insert function to build the tree.
    public void readFile() {
        this.openFile();
        read.useDelimiter("#|\\n");
        String t_name;
        int t_code;
        double t_fee;
        while (read.hasNext()) {
            t_code = read.nextInt();
            t_name = read.next();
            t_fee = read.nextDouble();

            insert(t_name, t_code, t_fee);
        }
        read.close();
    }

    //Adds a user entry into the trees.
    protected void add(String t_name, int t_code, double t_fee){
        insert(t_name, t_code, t_fee);
    }

    //Public wrapper function for looking up by service code.
    public String findCode(int code){
         return findCode(root, code);
    }

    //Look up service record by service code, returns false if there's no match.
    protected String findCode(ServiceInfo root, int code){
        if(root == null)
            return null;

        if(root.find(code)) {
            return root.Service();
        }
        String factor = findCode(root.getLeft(), code);

        return (factor != null) ? factor : findCode(root.getRight(), code);
    }

    //Look up service code and get fee.
    public double getFee(int code) {

        return getFee(this.root,code);

    }

    //Traverse tree and get fee.
    private double getFee(ServiceInfo root, int code) {

        if (root == null) return 0;

        if (root.find(code)) return root.fee;

        double leftRoot = getFee(root.getLeft(),code);

        if (leftRoot > 0) return leftRoot;

        return getFee(root.getRight(), code);
    }

    //Test class methods
    public static void main(String[] args) {
        ProviderDir tree = new ProviderDir();

        tree.readFile();//Testing, this reads from the provider dir text file and builds directory tree
        tree.display();//Test, this displays the provider directory tree alphabetically by name.
        int code = 598470;
        String factor = tree.findCode(code);//Test, looks up Service Info record by service code.
        out.println("\nThis is the service matching the code: " + factor);

    }
}

