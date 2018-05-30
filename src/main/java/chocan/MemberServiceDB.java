package chocan;

public class MemberServiceDB {
    private MemberService root;

   MemberServiceDB(){
        root = null;
        Load();
    }

    //will allow a service to be added to a members service record
    public boolean addServiceRecord(){
        if(root == null){
        }
        return false;
    }

    //will load a members service record to create a BST pf services provided to that member
    //will load this information from a text file
    private void Load(){
    }
}
