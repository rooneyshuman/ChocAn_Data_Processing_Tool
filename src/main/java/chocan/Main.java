package chocan;

public class Main {

    public static void main(String[] args) {
        // TODO User menu needs to be built.

        //test
       /* Member test = new Member();
        test.EditInfo();*/

        ProviderDir tree = new ProviderDir();

        //tree.Add();//Testing, This prompts user for input to build the provide dir tree.
        //tree.Add();
        //tree.Add();
        tree.Read_txt();//Testing, this reads from the provider dir text file and builds directory tree
        tree.Display();//Test, this displays the provider directory tree alphabetically by name.
        int code = 0;
        tree.Find_code(code);//Test, looks up Service Info record by service code.









    }

}