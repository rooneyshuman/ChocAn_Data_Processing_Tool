package chocan;


//This class creates a BST from the Serviceinfo class.



import java.io.File;
import java.util.Scanner;

public class ProviderDir {
    protected ServiceInfo root;
    protected Scanner input;

    protected Scanner read;

    private void delete_root() {
        root = null;
    }

    protected ServiceInfo Insert(ServiceInfo root, String name, int code, double fee) {
        if (root == null) {
            return new ServiceInfo(name, code, fee);

            //return root;

        }
         if (root.Less_Than(name)) {
            root.setLeft(Insert(root.getLeft(), name, code, fee));
            return root;
        }

        else {
            root.setRight(Insert(root.getRight(), name, code, fee));
            return root;
        }

    }

    //Displays the entire tree by in order traversal
    protected void Display(ServiceInfo root){
        if (root == null){
            return;}

        Display(root.getLeft());
        root.Display();
        Display(root.getRight());

    }

    public ProviderDir() {
        this.root = null;
        input = new Scanner(System.in);
    }

    //Wrapper for function for the insert function
    public void Insert(String name, int code, double fee){
        root = Insert(root, name, code, fee);
    }

    //Displays the entire tree in order, wrapper function
    public void Display(){
        Display(root);
    }

    //Opens the text file and checks for exceptions.
    public void Open_txt() {
        try {
            read = new Scanner(new File("Provider Directory"));
        } catch (Exception e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        }
    }

    //Reads in from text file and calls the insert function to bulld the tree.
    public void Read_txt() {
        this.Open_txt();
        read.useDelimiter("#|\\n");
        String t_name;
        int t_code;
        double t_fee;
        while (read.hasNext()) {
            t_code = read.nextInt();
            t_name = read.next();
            t_fee = read.nextDouble();

            Insert(t_name, t_code, t_fee);
        }
        read.close();
    }

    //Adds a user entry into the trees.
    protected void Add(String t_name, int t_code, double t_fee){
        Insert(t_name, t_code, t_fee);
    }

    //Public wrapper function for looking up by service code.
    public String Find_code(int code){
         return Find_code(root, code);
    }

    //Look up service record by service code, returns false if there's no match.
    protected String Find_code(ServiceInfo root, int code){
        if(root == null)
            return null;


        if(root.Find(code)) {
            return root.Service();
        }
        String factor = Find_code(root.getLeft(), code);

        return (factor != null) ? factor : Find_code(root.getRight(), code);


    }
    //Wrapper function for manually adding an entry into the tree.
    public void Add(){

        String t_name;
        int t_code;
        double t_fee;

        System.out.println("Enter the service name.");
        t_name = input.nextLine();
        System.out.println("Enter the code.");
        t_code = input.nextInt();
        System.out.println("Enter the fee.");
        t_fee = input.nextDouble();
        input.nextLine();

        Add(t_name, t_code, t_fee);
    }
}

